package model;

/**
 * This class models extra charges that could be applied to food elements.
 * 
 * @author luca
 */
public class Supplement {

	private String name;
	private float extraCharge;
	private long ID;
	
	public Supplement(String name, float extraCharge, long ID) {
		this.name = name;
		this.extraCharge = extraCharge;
		this.ID = ID;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the extraCharge
	 */
	public float getExtraCharge() {
		return extraCharge;
	}

	public long getID() {
		return ID;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(extraCharge);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Supplement other = (Supplement) obj;
		if (Float.floatToIntBits(extraCharge) != Float
				.floatToIntBits(other.extraCharge))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Supplement [name=" + name + ", extraCharge=" + extraCharge
				+ "]";
	}
	
}
