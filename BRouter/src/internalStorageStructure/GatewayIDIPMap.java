package internalStorageStructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import HttpServer.LogOper;

import model.Gatewayidipmap;

/*
 * 定位毛节点idip映射
 * 数据结构list
 * id， ip
 */
public class GatewayIDIPMap {
	List<Gatewayidipmap> map = new ArrayList<Gatewayidipmap>() ;
	private static GatewayIDIPMap instance = null;
	private GatewayIDIPMap() {
	}
		
	public static GatewayIDIPMap getInstance() {
		if (instance == null) { 
		      instance = new GatewayIDIPMap(); 
	    }
		return instance;
	}
	public synchronized boolean  addIDIPMapItem(String id, String ip){
		//System.out.println(id+"------------"+ip);
		for(Gatewayidipmap mapitem:map){
			if(mapitem.getGatewayID().equals(id)){
				if(mapitem.getIp().equals(ip)){
					return false;
				}
				else
				{
					mapitem.setIp(ip);
					return true;
				}
			}
			
		}
		//System.out.println("id ip map add"+id+";"+ip);
		Gatewayidipmap gm=new Gatewayidipmap();
		gm.setGatewayID(id);
		gm.setIp(ip);
		map.add(gm);
		return true;
	}
	public void clearMap(){
			map.clear();
	}
	public void printGatewayIDIPMap(){
		System.out.println(map.size()+"------------------------------");
		for(Gatewayidipmap mapitem:map){
			System.out.println(mapitem.getGatewayID()+","+mapitem.getIp());
		}
	}
	public boolean isInMap(String gid){
		for(Gatewayidipmap mapitem:map){
			if(mapitem.getGatewayID().equals(gid)){
				return true;
			}
		}
		return false;
	}
	public boolean judgeMap(HashMap<String,String> idip){
		//System.out.println("judge map");
		//printGatewayIDIPMap();
		if(idip.size()==map.size()){
			return true;
		}	
		Iterator iter = ((HashMap<String, String>) idip).entrySet().iterator();
		while(iter.hasNext()){
			Entry<String,String> next = (Entry<String, String>) iter.next();
			String key=next.getKey();
			//System.out.println("in file "+key);
			if(!isInMap(key)){
				//System.out.println(key+","+idip.get(key)+"not receive heartbeat");
				LogOper.GetLogOper().addLog(key+","+idip.get(key)+"not receive heartbeat");
			}
			
		}
		return false;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GatewayIDIPMap gm=GatewayIDIPMap.getInstance();
		gm.addIDIPMapItem("1", "111");
		gm.printGatewayIDIPMap();
		gm.addIDIPMapItem("1", "112");
		gm.printGatewayIDIPMap();
	}
}
