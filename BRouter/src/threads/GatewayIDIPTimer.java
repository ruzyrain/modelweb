package threads;

import internalStorageStructure.GatewayIDIPMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import HttpServer.LogOper;
/*
 * all id-ip store in a file
 * 检测网关心跳是否接受到,将所有网关编号和ip存储到一个文件,以文件中内容作为依据,如果到时间没有
 * 受到心跳,则存储日志并给服务器发警报,由于服务器后来加了这个功能,所以这部分现在
 * 已经废弃,但是依然可以调试时使用
 */
public class GatewayIDIPTimer extends Thread {
	  public GatewayIDIPTimer(String threadName) {
	        super(threadName);
	    }
	  public static void fileParser(String pathname) throws FileNotFoundException{
	    	  File filename = new File(pathname); // 要读取以上路径的input。txt文件  
	          InputStreamReader reader = new InputStreamReader(  
	                  new FileInputStream(filename)); // 建立一个输入流对象reader  
	          BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言  
	          String line = "";  
	          HashMap<String,String> idip =new HashMap<String,String>();
	          try {
				line = br.readLine();
				
				while (line != null) {  
					//System.out.println("line:"+line);
					  String[] content=line.split(",");
					 // content[0]
					  idip.put(content[0], content[1]);
		              line = br.readLine(); // 一次读入一行数据  
		          } 
				
				GatewayIDIPMap.getInstance().judgeMap(idip);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	           
	  }
	  @SuppressWarnings("static-access")
	public void run() {
		  while(true){
	        	try {
	        		Thread.currentThread().sleep(600000);
	        		String pathname="D:/Router_old/src/internalStorageStructure/GIDIPMap";
	        		fileParser(pathname);
	        		GatewayIDIPMap.getInstance().clearMap();
	        		//System.out.println("1200 timer");
	        		
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		  }
	  }
	  public static void main(String[] args) {
		String pathname="D:/Router_old/src/internalStorageStructure/GIDIPMap";
  		try {
			fileParser(pathname);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
}
