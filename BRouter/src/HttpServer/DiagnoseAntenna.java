package HttpServer;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
/*
 * 诊断同一网关的两个2531设备，一个接受的信号比较强，另外一个却一直收不到数据，此时收不到数据的节点可能出现问题；
 * 如果一个信号强度值在-90左右，另外一个可能由于距离，位置，墙阻挡等原因会一直收不到，这种情况是正常情况，给予了
 * 排除
 */
public class DiagnoseAntenna {
	HashMap<String,int[]> map=new HashMap<String,int[]>(); 
	private static DiagnoseAntenna instance = null;
	private DiagnoseAntenna() {
	
	}
		
	public static DiagnoseAntenna getInstance() {
		if (instance == null) { 
		      instance = new DiagnoseAntenna(); 
	    }
		return instance;
	}
	public void updataMap(String gid,int[]count){
		if(!map.containsKey(gid)){
			map.put(gid, count);
		}
		else{
			int value[]=map.get(gid);
			if(count[0]!=0){
				value[0]+=1;
			}
			else{
				value[0]=0;
			}
			if(count[1]!=0){
				value[1]+=1;
			}
			else{
				value[1]=0;
			}
			map.put(gid, value);
			if(value[0]==50){
				//System.out.println(gid+" num 1 antenna may break down ");
				LogOper.GetLogOper().addLog(gid+" num 1 antenna may break down ");
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	    		String theTime=df.format(new Date());
				String pac=gid+","+theTime+"62G1";
				try {
					new ElementalHttpPostString().post2Server(pac, ConstantInterface.SERVERID);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(value[1]==50){
				LogOper.GetLogOper().addLog(gid+" num 2 antenna may break down ");
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	    		String theTime=df.format(new Date());
				String pac=gid+","+theTime+"62G2";
				try {
					new ElementalHttpPostString().post2Server(pac, ConstantInterface.SERVERID);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//System.out.println(gid+" num 2 antenna may break down ");
			}
		}
	}
	public void antennaRecover(String gid){
		if(map.containsKey(gid)){
			int value[]=map.get(gid);
			if(value[0]==50 || value[1]==50){
				LogOper.GetLogOper().addLog(gid+" map size:"+map.size()+" 2 antenna receive pacs.");
				map.remove(gid);
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	    		String theTime=df.format(new Date());
				String pac=gid+","+theTime+"63";
				try {
					new ElementalHttpPostString().post2Server(pac, ConstantInterface.SERVERID);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
	public void displayMap(){
		Iterator iter = map.entrySet().iterator();
		while(iter.hasNext()){
			Entry<String, int[]> next = (Entry<String, int[]>) iter.next();
			String key=next.getKey();
			int[]value=next.getValue();
			System.out.println(key+","+value[0]+","+value[1]);
		}
	}
	public static void main(String[] args) {
		DiagnoseAntenna da=DiagnoseAntenna.getInstance();
		String gid="001";
		int value[]={0,1};
		for(int i=0;i<51;i++)
			da.updataMap(gid+i, value);
		da.displayMap();
	}
}
