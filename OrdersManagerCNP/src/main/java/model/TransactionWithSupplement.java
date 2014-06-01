package model;

/**
 * This class extends the concept of Transaction by adding a supplement to be taken in account.
 * So it will be used to manage all food transaction with particular needs.
 * 
 * @author luca
 */
public class TransactionWithSupplement extends Transaction {

	private Supplement supplement;
	
	/**
	 * This constructor returns an object which models a transaction with
	 * supplement. Please note that involvedFood must belong to the Food class
	 * and not to the Drink one.
	 * 
	 * @param involvedFood
	 * @param quantity
	 * @param supplement
	 */
	public TransactionWithSupplement(Food involvedFood, int quantity, Supplement supplement) {
		super(involvedFood, quantity);
		this.supplement = supplement;
	}

	/**
	 * @return the supplement
	 */
	public Supplement getSupplement() {
		return supplement;
	}

	@Override
	public float getTransactionCost() {
		return quantity * (involvedElement.getCost() + supplement.getExtraCharge());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((supplement == null) ? 0 : supplement.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransactionWithSupplement other = (TransactionWithSupplement) obj;
		if (supplement == null) {
			if (other.supplement != null)
				return false;
		} else if (!supplement.equals(other.supplement))
			return false;
		return true;
	}
	
}
