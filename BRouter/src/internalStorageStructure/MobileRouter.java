package internalStorageStructure;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/*
 * �·����MDѰַ·��
 * ���ݽṹ   mdid, gatewayid, ��λë�ڵ�ϴ�rssi, ��rssi��Ӧ��ʱ��
 */
public class MobileRouter<E> {
	Map<String, String[]> router = new HashMap<String, String[]>();
	
	private static MobileRouter instance = null;
	private MobileRouter() {
	
	}
		
	public static MobileRouter getInstance() {
		if (instance == null) { 
		      instance = new MobileRouter(); 
	    }
		return instance;
	}
	
	public String queryGateway(String mdid){
		
		return router.get(mdid) == null?"":router.get(mdid)[0];
	}
	
	public synchronized boolean updateRouter(String mdid, String gatewayid, String type, String latesttime){
		String[] value = {gatewayid,type,latesttime};
		router.put(mdid, value);
		return true;

	
	}
	public void deleteInvalid(String mdid){
		router.remove(mdid);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
	}
}
