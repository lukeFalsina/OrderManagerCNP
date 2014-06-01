package model;

/**
 * Natural extension of the abstract class Element in order to model
 * elements managed by the kitchen.
 * @author luca
 */
public class Food extends Element {

	/**
	 * Basic constructor which simply returns a new object with the incoming parameters
	 * as its attribute.
	 * 
	 * @param name
	 * @param cost
	 * @param description
	 */
	public Food(String name, float cost, String description, long ID) {
		this.name = name;
		this.cost = cost;
		this.description = description;
		this.ID = ID;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Food [getName()=" + getName() + ", getCost()=" + getCost()
				+ ", getClass()=" + getClass() + "]";
	}
	
	
}
