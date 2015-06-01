package JDO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.protocol.HttpRequestExecutor;

import HttpServer.ConstantInterface;
import JDO.IDMapOperator;
import JDO.UploadBuffer;
/**
 * ÎÞÓÃ´úÂë
 * @author Administrator
 *
 */
public class MergeMap {
	Map<String, String> map = new HashMap<String, String>();
	private static MergeMap instance = null;
	private MergeMap() {
		//new MergeUploadBuffer("merge upload thread").start();
	}
		
	public static MergeMap getInstance() {
		if (instance == null) { 
		      instance = new MergeMap(); 
	    }
		return instance;
	}
	public Map<String, String> getMap(){
		return map;
	}
	public void mergeFun(String contentString,int seq){
		//System.out.println(contentString);
		String key=contentString.substring(37, 39);
		
		String value=map.get(key);
		if(value!=null){
			
			if(!value.contains(contentString.substring(37))){
				System.out.println("add:"+seq);
				value +="$"+contentString;
				
				map.put(key, value);
				merge(key);	
			}
		}
		else{
			System.out.println("add:"+seq);
			value=contentString;
			
			map.put(key, value);
		}
	}
	public boolean merge(String key){
		//System.out.println("merge fun");
		
		String value=map.get(key);
		String[] itemStrings=value.split("\\$");
		int itemNum=itemStrings.length;
		
		if(itemNum>0){
			//System.out.println("item num:"+itemNum);

			byte[]bs=itemStrings[0].substring(45, 52).getBytes();
			//System.out.println(bs[1]);
		
			int num=bs[0];
			//System.out.println("num in merge:"+num+",item num:"+itemNum);
			if(num<itemNum){
				System.out.println("split num<merge num");
				map.remove(key);
				return false;
			}
			else if(num>itemNum){
				//System.out.println("receive not complete");
				return false;
			}
			int[]seq=new int[itemNum];
			for(int index=0;index<itemNum;index++){
				seq[index]=index;
			}
			//seq[0][1]=Integer.parseInt(itemStrings[0].substring(46, 47));
			bs=itemStrings[0].substring(45, 52).getBytes();
			seq[0]=bs[4];
			System.out.println("seq"+bs[0]+bs[4]);
			for (int i = 1; i < itemStrings.length; i++) {	
				
				bs=itemStrings[i].substring(45, 52).getBytes();
				seq[i]=bs[4];
				//System.out.println("num:"+num+"the num:"+bs[0]+"seq:"+seq[i][1]);
			
				if(num!=bs[0]){
					System.out.println("pac split num not constent");
					map.remove(key);
					return false;
				}
					
			}	
			
			map.remove(key);
			
			bubbleSort(seq,itemStrings);
			String dataString=itemStrings[0].substring(0, 45);
			for(int i=0;i<seq.length;i++){
				System.out.println(seq[i]);
				dataString += itemStrings[i].substring(53);
			}
			//System.out.println("dataString:"+dataString);
			String gid=new IDMapOperator().getGID(key);
			dataString = itemStrings[0].substring(0, 47)+dataString+gid;
            try {
            	//ElementalHttpPost.getInstance().post2Server(ConstantInterface.SERVERID,dataString);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
			                             
			
		}
		
		return false;
	}
	public static void bubbleSort(int[] seq,String[] itemStrings){
		int temp;
		String temp1;
        for (int i = 0; i < seq.length - 1; i++) {
      	  for (int j = 0; j < seq.length - i - 1; j++) {
      		  if (seq[j] > seq[j + 1]) {
      			  temp1 = itemStrings[j + 1];
      			  itemStrings[j + 1] = itemStrings[j];
      			  itemStrings[j] = temp1;
      			  temp = seq[j + 1];
      			  seq[j + 1] = seq[j];
      			  seq[j] = temp;
      		  }
      	  }
        }
	}
}
