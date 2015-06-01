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
 * 下发命令到MD寻址路径
 * 数据结构   mdid, gatewayid, 定位毛节点较大rssi, 该rssi对应的时间
 */
public class Router<E> {
	List<RouterItem> router = new ArrayList<RouterItem>();
	private static Router instance = null;
	private Router() {
	
	}
		
	public static Router getInstance() {
		if (instance == null) { 
		      instance = new Router(); 
	    }
		return instance;
	}
	public int findRouterItem(String mdid){
		
		for(int i=0;i<router.size();i++){
			if(router.get(i).getMdid().equals(mdid)){
				return i;
			}
		}
		/*for(RouterItem item:router){
			if(item.getMdid().equals(mdid)){
				return item;
			}
		}*/
		return -1;
	}
	public String queryGateway(String mdid){
		for(RouterItem item:router){
			if(item.getMdid().equals(mdid)){
				//System.out.println(item.getGatewayid());
				return item.getGatewayid();
			}
		}
		return "";
	}
	public synchronized boolean updateRouter(String mdid, String gatewayid, int rssi, String latesttime){
		//System.out.println("in upate router"+rssi+"---"+gatewayid);
		int routerItemIndex=findRouterItem(mdid);
		if(routerItemIndex==-1){
			//System.out.println("add router:"+gatewayid);
			
			RouterItem rt=new RouterItem(mdid, gatewayid,rssi, latesttime);
			router.add(rt);
			//System.out.println("first:"+gatewayid+","+rssi);
			//return true;
		}
		else
		{
			//相同gateway新换旧
			RouterItem theRouterItem=router.get(routerItemIndex);
    		if(gatewayid.equals(theRouterItem.getGatewayid())){
    			theRouterItem.setLatesttime(latesttime);
    			theRouterItem.setRssi(rssi);
    			router.set(routerItemIndex, theRouterItem);
    			//System.out.println("same gateway in upate router"+rssi+"---"+gatewayid);
    			
    		}
    		else{
    			//值大时间新替换
    			int max=theRouterItem.getRssi();
    			//System.out.println("different gateway");
    			//System.out.println(theRouterItem.getLatesttime()+","+latesttime);
        		if(theRouterItem.getLatesttime().compareTo(latesttime)<=0){
        			//System.out.println("max:"+max+";rssi:"+rssi);
        			if(max < rssi){
        				//System.out.println(max+"<"+rssi);
        				theRouterItem.setLatesttime(latesttime);
            			theRouterItem.setRssi(rssi);
            			theRouterItem.setGatewayid(gatewayid);
            			router.set(routerItemIndex, theRouterItem);
            			//System.out.println("diffff in upate router"+rssi+"---"+gatewayid);
        				
        			}
        			//超过一定的时间及时其他网关值小也可以替换，防止网关异常
        			else{
        			   	String s1=theRouterItem.getLatesttime();
        		    	Date oDate = null;   //定义时间类型    
        		    	Date nDate = null;
        		    	SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd hh:MM:ss");
        		    	try {
        		    		oDate = (Date) inputFormat.parse(s1);
        		    		nDate = (Date) inputFormat.parse(latesttime);
        				} catch (ParseException e1) {
        					// TODO Auto-generated catch block
        					e1.printStackTrace();
        					return false;
        				} 
   
        				if(nDate.getTime()-oDate.getTime()>15000){
        					//dBOperator.updateGatewaySelector(gatewayID, MDID, time, rssi);
        					theRouterItem.setLatesttime(latesttime);
                			theRouterItem.setRssi(rssi);
                			theRouterItem.setGatewayid(gatewayid);
                			router.set(routerItemIndex, theRouterItem);
                			//System.out.println("timeout in upate router"+rssi+"---"+gatewayid);
            				
        				}
        				//String string=time-queryResult.get(2);
        			}
        		}
    		}
		}
		return true;
	}
	
	public void deleteInvalid(){
		Date nDate=new Date();
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		for(Iterator<RouterItem> it=router.iterator();it.hasNext();){
			RouterItem item=it.next();
			Date oDate;
			try {
				oDate = (Date) inputFormat.parse(item.getLatesttime());
				if(nDate.getTime()-oDate.getTime()>3600000){
					it.remove();
					//router.remove(item);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	}
	public void printRouter(){
		System.out.println("--------------------------------------------------");
		for(RouterItem item:router){
			System.out.println(item.getMdid()+","+item.getGatewayid()+","+item.getRssi()+","+item.getLatesttime());
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
	}
}
