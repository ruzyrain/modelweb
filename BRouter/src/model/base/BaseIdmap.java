package model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the idmap table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="idmap"
 */

public abstract class BaseIdmap  implements Serializable {

	public static String REF = "Idmap";
	public static String PROP_PANID = "Panid";
	public static String PROP_ID = "Id";


	// constructors
	public BaseIdmap () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseIdmap (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String panid;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="GID"
     */
	public java.lang.String getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.String id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: panid
	 */
	public java.lang.String getPanid () {
		return panid;
	}

	/**
	 * Set the value related to the column: panid
	 * @param panid the panid value
	 */
	public void setPanid (java.lang.String panid) {
		this.panid = panid;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof model.Idmap)) return false;
		else {
			model.Idmap idmap = (model.Idmap) obj;
			if (null == this.getId() || null == idmap.getId()) return false;
			else return (this.getId().equals(idmap.getId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}