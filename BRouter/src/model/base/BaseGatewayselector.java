package model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the gatewayselector table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="gatewayselector"
 */

public abstract class BaseGatewayselector  implements Serializable {

	public static String REF = "Gatewayselector";
	public static String PROP_GATEWAY_I_D = "GatewayID";
	public static String PROP_LATEST_TIME = "LatestTime";
	public static String PROP_RSSI = "Rssi";
	public static String PROP_ID = "Id";


	// constructors
	public BaseGatewayselector () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseGatewayselector (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String gatewayID;
	private java.lang.Integer rssi;
	private java.lang.String latestTime;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="MDID"
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
	 * Return the value associated with the column: GatewayID
	 */
	public java.lang.String getGatewayID () {
		return gatewayID;
	}

	/**
	 * Set the value related to the column: GatewayID
	 * @param gatewayID the GatewayID value
	 */
	public void setGatewayID (java.lang.String gatewayID) {
		this.gatewayID = gatewayID;
	}



	/**
	 * Return the value associated with the column: rssi
	 */
	public java.lang.Integer getRssi () {
		return rssi;
	}

	/**
	 * Set the value related to the column: rssi
	 * @param rssi the rssi value
	 */
	public void setRssi (java.lang.Integer rssi) {
		this.rssi = rssi;
	}



	/**
	 * Return the value associated with the column: latestTime
	 */
	public java.lang.String getLatestTime () {
		return latestTime;
	}

	/**
	 * Set the value related to the column: latestTime
	 * @param latestTime the latestTime value
	 */
	public void setLatestTime (java.lang.String latestTime) {
		this.latestTime = latestTime;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof model.Gatewayselector)) return false;
		else {
			model.Gatewayselector gatewayselector = (model.Gatewayselector) obj;
			if (null == this.getId() || null == gatewayselector.getId()) return false;
			else return (this.getId().equals(gatewayselector.getId()));
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