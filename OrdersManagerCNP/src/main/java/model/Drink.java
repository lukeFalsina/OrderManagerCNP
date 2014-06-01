package model;

/**
 * Natural extension of the abstract class Element in order to model
 * elements served at the bar.
 * @author luca
 */
public class Drink extends Element {

	/**
	 * Basic class constructor that returns an object whose attributes
	 * are equal to the input parameters.
	 * @param name
	 * @param cost
	 * @param description
	 */
	public Drink(String name, float cost, String description, long ID) {
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
		return "Drink [getName()=" + getName() + ", getCost()=" + getCost()
				+ ", getClass()=" + getClass() + "]";
	}
	
}
