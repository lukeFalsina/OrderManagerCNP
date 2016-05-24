package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.CommittedOrder;
import main.DataBaseHandler;
import model.Drink;
import model.Element;
import model.Food;
import model.Order;
import model.Supplement;
import model.Transaction;
import model.TransactionWithSupplement;

public class DataBaseHandlerImpl implements DataBaseHandler {

	private Connection con;
	private Statement st;
	private ResultSet rs, rs0, rs1;
	private PreparedStatement pst;
	private String url;
	private String user;
	private String password;
	private String FQDN;
	
	
	public DataBaseHandlerImpl(String iPServerDB) {
		FQDN = iPServerDB;
	}

	private void startTransaction() throws SQLException {
		con = null;
        st = null;
        rs = null;
        url = "jdbc:postgresql://" + FQDN.toString() + "/testdb";
        //url = "jdbc:postgresql://"+"127.0.0.1"+"/testdb";
        user = "postgres";
        password = "user";
        con = DriverManager.getConnection(url, user, password);
        st = con.createStatement();
        con.setAutoCommit(false);
	}

	private void endTransaction() throws SQLException {
    	con.commit();
    }

	@Override
	public CommittedOrder saveOrder(Order incomingOrder) {
		int id_ordine;
		int id_variazione;
		String time;
		CommittedOrder myOrder=null;
		//Variabili d'appoggio
		int randomMarker;
		Random randomGenerator;

		try {
			startTransaction();
			
			//Aggiungi Ordine al DB
			//Genero un record con un marker e lo inserisco
			randomGenerator = new Random();
			randomMarker = randomGenerator.nextInt(2147483647);
			pst = con.prepareStatement("INSERT INTO Ordine("+
					"NumeroTavolo, DataRicezione, DataEvasione, "+
					"Marker) VALUES (0, now(), NULL, ?);");
			pst.setInt(1, randomMarker);
			
			pst.executeUpdate();
			//estraggo i campi del record compilati dal DB partendo dal marker
			pst = con.prepareStatement("SELECT ID_ordine, DataRicezione FROM Ordine WHERE Marker = ?;");
			pst.setInt(1, randomMarker);
			rs = pst.executeQuery();
			if (rs.next()) {
				id_ordine=rs.getInt(1);
				time=rs.getTimestamp(2).toString();
		    } else return null;
			//Azzero il marker
			pst = con.prepareStatement("UPDATE Ordine"+
			   " SET Marker = 0 WHERE ID_ordine = ? ;");
			pst.setInt(1, id_ordine);
			
			pst.executeUpdate();
			
			Map<TransactionWithSupplement, Long> tToBeUpdated = new HashMap<TransactionWithSupplement, Long>();
			
			//per ogni transazione
			Iterator<Transaction> iterator = incomingOrder.getTransactions().iterator();
			while (iterator.hasNext()) {
				Transaction t= iterator.next();
				
				if (t.getClass().equals(TransactionWithSupplement.class)) {
					// Transactions with Supplement
					// Recast of the transaction..
					TransactionWithSupplement tSupp = (TransactionWithSupplement) t;
					//Se la variazione non � presente nel DB la aggiungo
					if (tSupp.getSupplement().getID()==0){
						
						//Genero un record con un marker e lo inserisco
						randomGenerator = new Random();
						randomMarker = randomGenerator.nextInt(2147483647);
						pst = con.prepareStatement("INSERT INTO Variazione( "+
					            "Descrizione, Supplemento, Marker) "+
					            "VALUES (?, ?, ?);");
						pst.setString(1, tSupp.getSupplement().getName());
						pst.setFloat(2, tSupp.getSupplement().getExtraCharge());
						pst.setInt(3, randomMarker);
						pst.executeUpdate();
						//estraggo i campi del record compilati dal DB partendo dal marker
						pst = con.prepareStatement("SELECT ID_variazione FROM Variazione"+
								" WHERE Marker = ?;");
						pst.setInt(1, randomMarker);
						rs = pst.executeQuery();
						if (rs.next()) {
							id_variazione=rs.getInt(1);
							
							tToBeUpdated.put(tSupp, new Long(id_variazione));
/*							incomingOrder.removeSpecificTransaction(tSupp);
							newSupplement = new Supplement(tSupp.getSupplement().getName(), tSupp.getSupplement().getExtraCharge(), id_variazione);
							newTransaction = new TransactionWithSupplement((Food) tSupp.getInvolvedElement(), tSupp.getQuantity(), newSupplement );
							incomingOrder.addFurtherTransaction(newTransaction); */
					    } else return null;
						
						//Azzero il marker
						pst = con.prepareStatement("UPDATE Variazione "+
						   " SET Marker = 0 WHERE ID_variazione = ? ;");
						pst.setInt(1, id_ordine);
						pst.executeUpdate();
					}else 
						//Se � presente estraggo l'ID
						id_variazione=(int)tSupp.getSupplement().getID();
					
				}
				else id_variazione=0;
				
				//Ho ID_ordine, ID_pietanza e ID_variazione(se presente)
				//Quindi posso inserire la transazione nel DB
				pst = con.prepareStatement("INSERT INTO Transazione( "+
			            "Quantita, ordine_ID, pietanza_ID, variazione_ID) "+
			            "VALUES (?, ?, ?, "+(id_variazione!=0 ? "?" : "NULL")+");");
				pst.setInt(1, t.getQuantity());
				pst.setInt(2, id_ordine);
				pst.setInt(3, (int)t.getInvolvedElement().getID());
				if (id_variazione!=0)
					pst.setInt(4, (int)id_variazione);
				pst.executeUpdate();
			}
			
			endTransaction();
			
			// Update of transactions with new supplement objects
			for(TransactionWithSupplement checkedTrans : tToBeUpdated.keySet()) {
				Long id_newSuppl = tToBeUpdated.get(checkedTrans);
				incomingOrder = incomingOrder.removeSpecificTransaction(checkedTrans);
				
				Supplement newSupplement = new Supplement(checkedTrans.getSupplement().getName(), checkedTrans.getSupplement().getExtraCharge(), id_newSuppl);
				TransactionWithSupplement newTransaction = new TransactionWithSupplement((Food) checkedTrans.getInvolvedElement(), checkedTrans.getQuantity(), newSupplement );
				
				if(incomingOrder != null) {
					incomingOrder.addFurtherTransaction(newTransaction);
				} else {
					incomingOrder = new Order(new TransactionWithSupplement((Food) checkedTrans.getInvolvedElement(), checkedTrans.getQuantity(), newSupplement ));
				}
				
			}
			myOrder= new CommittedOrder(incomingOrder, id_ordine, time);
		} 
			catch (SQLException ex) {
    	if (con != null) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger lgr = Logger.getLogger(DataBaseHandlerImpl.class.getName());
                lgr.log(Level.WARNING, ex1.getMessage(), ex1);
            }
        }
        Logger lgr = Logger.getLogger(DataBaseHandlerImpl.class.getName());
        lgr.log(Level.SEVERE, ex.getMessage(), ex);
        return null;
    } finally {
    	try {
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
        	Logger lgr = Logger.getLogger(DataBaseHandlerImpl.class.getName());
            lgr.log(Level.WARNING, ex.getMessage(), ex);
        }
    }
		return myOrder;
	}


	@Override
	public CommittedOrder searchOrderByID(long ID) {
		CommittedOrder myOrder=null;
		
		//AssociatedOrder
		int transazione_Quantita;
		//int transazione_ID_transazione;
		//String transazione_DataCommit;
		int transazione_variazione_ID;
		int transazione_pietanza_ID;
		
		//ORDINE
		String ordine_dataRic;
		//ID
		
		//PIETANZA
		String pietanza_Nome;
		float pietanza_Costo;
		String pietanza_Descrizione;
		int pietanza_Tipo;
		
		//VARIAZIONE
		String variazione_Nome;
		float variazione_Costo;
		
		
		try {
			startTransaction();
			
			//
			//ORDINE
			pst = con.prepareStatement("SELECT DataRicezione FROM Ordine WHERE ID_ordine = ?;");
			pst.setInt(1, (int)ID);
			rs0 = pst.executeQuery();
			if (rs0.next()) {
				ordine_dataRic=rs0.getTimestamp(1).toString();
		    } else return null;
			
			//
			//TRANSAZIONE
			pst = con.prepareStatement(
					"SELECT Quantita, ID_transazione, DataCommit, variazione_ID, pietanza_ID "+
				       "FROM Transazione WHERE ordine_ID = ?;");
			pst.setInt(1, (int)ID);
			rs1 = pst.executeQuery();
			
			boolean first=true;
			Order myOrder_plain=null;
			//per ogni transazione costruisco la struttura
			while (rs1.next()) {
				transazione_Quantita=rs1.getInt(1);
				//transazione_ID_transazione=rs1.getInt(2);
				//transazione_DataCommit=rs1.getDate(3).toString();
				transazione_variazione_ID=rs1.getInt(4);
				transazione_pietanza_ID=rs1.getInt(5);
		    
				//
				//PIETANZA
				pst = con.prepareStatement("SELECT Nome, Costo, Descrizione, tipo_ID "+
						  "FROM Pietanza WHERE ID_pietanza = ?;");
				pst.setInt(1, transazione_pietanza_ID);
				rs = pst.executeQuery();
				if (rs.next()) {
					pietanza_Nome=rs.getString(1);
					pietanza_Costo=rs.getFloat(2);
					pietanza_Descrizione=rs.getString(3);
					pietanza_Tipo=rs.getInt(4);
			    } else return null;
				Element myElement;
				if (pietanza_Tipo==1){
					myElement= new Food(pietanza_Nome, pietanza_Costo, pietanza_Descrizione, transazione_pietanza_ID);
				}else
					myElement= new Drink(pietanza_Nome, pietanza_Costo, pietanza_Descrizione, transazione_pietanza_ID);
			
				Transaction myTrans;
				if (transazione_variazione_ID!=0)
					myTrans = new Transaction(myElement, transazione_Quantita);
				else {
					//
					//VARIAZIONE
					pst = con.prepareStatement("SELECT Descrizione, Supplemento "+
							  "FROM Variazione WHERE ID_variazione = ?;");
					pst.setInt(1, transazione_variazione_ID);
					rs = pst.executeQuery();
					if (rs.next()) {
						variazione_Nome=rs.getString(1);
						variazione_Costo=rs.getFloat(2);
				    } else return null;
					
					Supplement mySup =new Supplement(variazione_Nome, variazione_Costo, transazione_variazione_ID);
					
					myTrans = new TransactionWithSupplement((Food)myElement, transazione_Quantita, mySup);
				}
				
				if (first){
					myOrder_plain= new Order(myTrans);
				}else myOrder_plain.addFurtherTransaction(myTrans);
			}
			
			myOrder= new CommittedOrder(myOrder_plain, ID, ordine_dataRic);
			
			endTransaction();
		} catch (SQLException ex) {
    	if (con != null) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger lgr = Logger.getLogger(DataBaseHandlerImpl.class.getName());
                lgr.log(Level.WARNING, ex1.getMessage(), ex1);
            }
        }
        Logger lgr = Logger.getLogger(DataBaseHandlerImpl.class.getName());
        lgr.log(Level.SEVERE, ex.getMessage(), ex);
        return null;

    } finally {
    	try {
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
        	Logger lgr = Logger.getLogger(DataBaseHandlerImpl.class.getName());
            lgr.log(Level.WARNING, ex.getMessage(), ex);
        }
    }
		return myOrder;
	}

	
	
	
	@Override
	public List<Element> getCurrentElementList(String elementChoice) {
		List<Element> appoggio = new ArrayList<Element>();
		try {
			startTransaction();
			
			//pst = con.prepareStatement("SELECT \"Nome\",\"Costo\",\"Descrizione\",\"ID_pietanza\" FROM public.\"Pietanza\" WHERE \"tipo_ID\" = ?;");
			pst = con.prepareStatement("SELECT Nome, Costo, Descrizione, ID_pietanza FROM pietanza WHERE tipo_ID = ? AND active='t';");
			if (elementChoice.equals("FOOD")){
				pst.setInt(1, 1);
				rs = pst.executeQuery();
		        while (rs.next()) {
		        	appoggio.add(new Food(rs.getString(1),rs.getFloat(2),rs.getString(3),rs.getLong(4)));
		        }
			} else if (elementChoice.equals("DRINK")){
				pst.setInt(1, 2);
				rs = pst.executeQuery();
		        while (rs.next()) {
		        	appoggio.add(new Drink(rs.getString(1),rs.getFloat(2),rs.getString(3),rs.getLong(4)));
		        }
			}
	        
	        
			endTransaction();
		} catch (SQLException ex) {
    	if (con != null) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger lgr = Logger.getLogger(DataBaseHandlerImpl.class.getName());
                lgr.log(Level.WARNING, ex1.getMessage(), ex1);
            }
        }
        Logger lgr = Logger.getLogger(DataBaseHandlerImpl.class.getName());
        lgr.log(Level.SEVERE, ex.getMessage(), ex);

    } finally {
    	try {
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
        	Logger lgr = Logger.getLogger(DataBaseHandlerImpl.class.getName());
            lgr.log(Level.WARNING, ex.getMessage(), ex);
        }
    }
		return appoggio;
	}


	@Override
	public List<Supplement> getCurrentSupplementList() {
		List<Supplement> appoggio = new ArrayList<Supplement>();
		try {
			startTransaction();
			
			pst = con.prepareStatement("SELECT Descrizione, Supplemento, ID_variazione FROM variazione;");
	        rs = pst.executeQuery();
	        while (rs.next()) {
	        	appoggio.add(new Supplement(rs.getString(1),rs.getFloat(2),rs.getLong(3)));
	        }
	        
			endTransaction();
		} catch (SQLException ex) {
    	if (con != null) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger lgr = Logger.getLogger(DataBaseHandlerImpl.class.getName());
                lgr.log(Level.WARNING, ex1.getMessage(), ex1);
            }
        }
        Logger lgr = Logger.getLogger(DataBaseHandlerImpl.class.getName());
        lgr.log(Level.SEVERE, ex.getMessage(), ex);

    } finally {
    	try {
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
        	Logger lgr = Logger.getLogger(DataBaseHandlerImpl.class.getName());
            lgr.log(Level.WARNING, ex.getMessage(), ex);
        }
    }
		return appoggio;
	}

}
