package JDO;


import java.nio.charset.Charset;
import java.util.ArrayList;

import javassist.expr.NewArray;

/**
 * 目前可能无用，主要作用是一开始启动的 clearbuffer线程
 * @author Administrator
 *
 */
public class UploadBuffer {
	private static UploadBuffer instance = null;
	private ArrayList<String[]>uploadList=new ArrayList<String[]>();
	private UploadBuffer() {
		//new ClearUploadBuffer("clear thread").start();
	}
		
	public static UploadBuffer getInstance() {
		if (instance == null) { 
		      instance = new UploadBuffer(); 
	    }
		return instance;
	}
	public boolean queryItem(String item){
	    //System.out.println("item len:"+item.length());
		for (int i = 0; i < uploadList.size(); i++) {
			String cString=uploadList.get(i)[0];
			//if(item.substring(37,42).equals(uploadList.get(i)[0].substring(37,42))){
			if(item.substring(0,16).equals(cString.substring(0,16)) && item.substring(37).equals(cString.substring(37))){
				return true;
			}
		}
		return false;
	}
	public void addItem(String item){		
		if(!queryItem(item)){
			String[] contentString={item,String.valueOf(System.currentTimeMillis())};
			uploadList.add(contentString);
		}
	}
	public boolean deleteItem(){
		//System.out.println("delete");
		for (int i = 0; i <uploadList.size(); i++) {
			Long theTimeLong=Long.parseLong(uploadList.get(i)[1]);
			if(System.currentTimeMillis()-theTimeLong>60000)
			{
				uploadList.remove(i);				
			}
		}
		return true;
	}
	public static void main(String[] args) throws InterruptedException {
		/*String a=String.valueOf(System.currentTimeMillis());
		System.out.println((a));
		Thread.currentThread().sleep(1000);
		String b=String.valueOf(System.currentTimeMillis());
		System.out.println(b);
		System.out.println(Long.parseLong(b)-Long.parseLong(a));*/
		//char[]temp1={0,0,0,1};
		//String aString=temp1.toString();
		//aString.getChars(srcBegin, srcEnd, dst, dstBegin)
		//int num=0;
		//System.arraycopy(temp1,0,num,0,4);
		byte[]s=new byte[3];
		s[0]=1;
		s[1]=3;
		s[2]=4;
		String ss=new String(s, Charset.forName("UTF-8"));
		byte[]b=ss.getBytes();
		
		System.out.println(b[1]);
		String string="a$b$c";
		b=string.getBytes();
		System.out.println(new String(b,Charset.forName("UTF-8")));
		
	}
}

