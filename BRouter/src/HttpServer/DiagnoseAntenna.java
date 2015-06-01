package HttpServer;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
/*
 * ���ͬһ���ص�����2531�豸��һ�����ܵ��źűȽ�ǿ������һ��ȴһֱ�ղ������ݣ���ʱ�ղ������ݵĽڵ���ܳ������⣻
 * ���һ���ź�ǿ��ֵ��-90���ң�����һ���������ھ��룬λ�ã�ǽ�赲��ԭ���һֱ�ղ�����������������������������
 * �ų�
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
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
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
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
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
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
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
