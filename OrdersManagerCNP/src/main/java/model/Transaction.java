package model;

/**
 * Class Transaction represents a single entry into a complete order. In particular this class
 * will manage all the transactions without a Supplement and so all beverage transactions
 * and the ones of food without supplement should be managed with this class.
 * 
 * @author luca
 */
public class Transaction {
	
	protected Element involvedElement;
	protected int quantity;

	/**
	 * This constructor creates an entry in an order by associating a certain
	 * product and its bought quantity.
	 * @param involvedElement
	 * @param quantity
	 */
	public Transaction(Element involvedElement, int quantity) {
		this.involvedElement = involvedElement;
		this.quantity = quantity;
	}

	/**
	 * @return the involvedElement
	 */
	public Element getInvolvedElement() {
		return involvedElement;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}
	
	/**
	 * @return the total cost of the transaction
	 */
	public float getTransactionCost() {
		return quantity * involvedElement.getCost();
	}
	
	/**
	 * This method adds the new incoming quantity to the previous one
	 * of the transaction.
	 * It must be used to consolidate two equals transactions.
	 * 
	 * @param addedQuantity
	 */
	public void increaseQuantity(int addedQuantity) {
		this.quantity = this.quantity + addedQuantity;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((involvedElement == null) ? 0 : involvedElement.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (involvedElement == null) {
			if (other.involvedElement != null)
				return false;
		} else if (!involvedElement.equals(other.involvedElement))
			return false;
		return true;
	}
	
}
