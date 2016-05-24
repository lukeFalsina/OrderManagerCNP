-- First some cleanup cause we are good citizens..
DROP TABLE Transazione;
DROP TABLE Ordine;
DROP TABLE Variazione;
DROP TABLE Pietanza;

-- Table creation
CREATE TABLE Pietanza (
    ID_pietanza		serial PRIMARY KEY not null,
    Nome		varchar(80) not null,
    Costo       	float not null,
    Descrizione         varchar(80),
    tipo_ID		int not null,
    active		boolean not null
);

CREATE TABLE Variazione (
    ID_variazione	serial PRIMARY KEY not null,
    Descrizione         varchar(80) not null,
    Supplemento       	float not null,
    Marker		int
);

CREATE TABLE Ordine (
    ID_ordine		serial PRIMARY KEY not null,
    DataRicezione	date ,
    DataEvasione	date,
    NumeroTavolo	int,
    Marker		int
);

CREATE TABLE Transazione (
    ID_transazione	serial PRIMARY KEY,
    ordine_ID		int not null,
    pietanza_ID		int not null,
    variazione_ID	int,
    DataCommit          date not null default CURRENT_DATE,
    Quantita		int not null
);

-- Only insert Food and Beverages. The rest will be populated by the program
-- Only elements with the active flag marked as 't' (True) will be shown in the UI
-- Elements with tipo_ID = 1 => Cibo, Ristorante
-- Elements with tipo_ID = 2 => Bevanda, Bar
INSERT INTO Pietanza (Nome, Costo, Descrizione, tipo_ID, active) VALUES 
	('Casoncelli',4.5,'Unused',1,'t'),
	('Codenghí N del Pá',2.5,'Unused',1,'t'),
	('Spiedino N.1',2.5,'Unused',1,'t'),
	('Costine',3,'Unused',1,'t'),
	('Fritto Misto',6,'Unused',1,'t'),
	('Tomino all Piastra N.1',2.5,'Unused',1,'t'),
	('Polenta',1,'Unused',1,'t'),
	('Patatine Fritte',1.5,'Unused',1,'t'),
	('Fetta di Torta',2.5,'Unused',1,'t'),
	('Sorbetto al Limone',1,'Unused',1,'t'),
-- For Polenta Taragna you can update active to 't' for Sabato e Domenica
	('Polenta Taragna',3.5,'Unused',1,'f'),
	('Margherita',5,'Unused',1,'t'),
	('Prosciutto',6,'Unused',1,'t'),
	('Salamino',6,'Unused',1,'t'),
	('Wurstel',6,'Unused',1,'t'),
	('Napoli',6,'Unused',1,'t'),
	('Caffé Normale',1.0,'Unused',2,'t'),
	('Caffé Decaffeinato',1.0,'Unused',2,'t'),
	('Caffé Corretto',1.2,'Unused',2,'t'),
	('Grappa, Amari, Liquori',2.5,'Unused',2,'t'),
	('Vino Bianco/Rosso (1 Litro)',4,'Unused',2,'t'),
	('Vino Bianco/Rosso (1/2 Litro)',2,'Unused',2,'t'),
	('Vino Bianco/Rosso (Bicchiere)',1,'Unused',2,'t'),
	('Birra Bionda (Media)',3.5,'Unused',2,'t'),
	('Birra Bionda (Piccola)',2.5,'Unused',2,'t'),
	('Birra Rossa (Media)',4,'Unused',2,'t'),
	('Birra Rossa (Piccola)',3,'Unused',2,'t'),
	('Coca Cola (1 Litro)',3,'Unused',2,'t'),
	('Bottiglia di Aranciata, Gassosa',2,'Unused',2,'t'),
	('Lattina',1.2,'Unused',2,'t'),
	('Acqua (1 Litro)',1,'Unused',2,'t');

-- Also variations should be added here. An example follows:
INSERT INTO Variazione (Descrizione, Supplemento) VALUES
	('Piú formaggio', 0.5);
