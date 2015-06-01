package internalStorageStructure;

import java.util.HashMap;
import java.util.Map;
/*
 * Md ����λê�ڵ��·��Ѱַ��
 * ����MDID ��λê�ڵ��ţ��ź�ǿ��ֵ��ʱ��� 
 */
public class RouterItem {
	private String mdid;
	private String gatewayid;
	private int rssi;
	private String latesttime;
	public String getMdid() {
		return mdid;
	}
	public void setMdid(String mdid) {
		this.mdid = mdid;
	}
	public String getGatewayid() {
		return gatewayid;
	}
	public void setGatewayid(String gatewayid) {
		this.gatewayid = gatewayid;
	}
	public int getRssi() {
		return rssi;
	}
	public void setRssi(int rssi) {
		this.rssi = rssi;
	}
	public String getLatesttime() {
		return latesttime;
	}
	public void setLatesttime(String latesttime) {
		this.latesttime = latesttime;
	}
	public RouterItem(String mdid, String gatewayid, int rssi, String latesttime){
		this.mdid=mdid;
		this.gatewayid=gatewayid;
		this.rssi=rssi;
		this.latesttime=latesttime;
	}


}
