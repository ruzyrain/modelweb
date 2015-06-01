package model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the gatewayidipmap table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="gatewayidipmap"
 */

public abstract class BaseGatewayidipmap  implements Serializable {

	public static String REF = "Gatewayidipmap";
	public static String PROP_IP = "Ip";
	public static String PROP_GATEWAY_I_D = "GatewayID";
	public static String PROP_ID = "Id";


	// constructors
	public BaseGatewayidipmap () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseGatewayidipmap (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String gatewayID;
	private java.lang.String ip;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="increment"
     *  column="idgatewayidipmap"
     */
	public java.lang.Integer getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.Integer id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: gatewayID
	 */
	public java.lang.String getGatewayID () {
		return gatewayID;
	}

	/**
	 * Set the value related to the column: gatewayID
	 * @param gatewayID the gatewayID value
	 */
	public void setGatewayID (java.lang.String gatewayID) {
		this.gatewayID = gatewayID;
	}



	/**
	 * Return the value associated with the column: ip
	 */
	public java.lang.String getIp () {
		return ip;
	}

	/**
	 * Set the value related to the column: ip
	 * @param ip the ip value
	 */
	public void setIp (java.lang.String ip) {
		this.ip = ip;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof model.Gatewayidipmap)) return false;
		else {
			model.Gatewayidipmap gatewayidipmap = (model.Gatewayidipmap) obj;
			if (null == this.getId() || null == gatewayidipmap.getId()) return false;
			else return (this.getId().equals(gatewayidipmap.getId()));
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