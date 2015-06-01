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
 * ������������Ƿ���ܵ�,���������ر�ź�ip�洢��һ���ļ�,���ļ���������Ϊ����,�����ʱ��û��
 * �ܵ�����,��洢��־����������������,���ڷ��������������������,�����ⲿ������
 * �Ѿ�����,������Ȼ���Ե���ʱʹ��
 */
public class GatewayIDIPTimer extends Thread {
	  public GatewayIDIPTimer(String threadName) {
	        super(threadName);
	    }
	  public static void fileParser(String pathname) throws FileNotFoundException{
	    	  File filename = new File(pathname); // Ҫ��ȡ����·����input��txt�ļ�  
	          InputStreamReader reader = new InputStreamReader(  
	                  new FileInputStream(filename)); // ����һ������������reader  
	          BufferedReader br = new BufferedReader(reader); // ����һ�����������ļ�����ת�ɼ�����ܶ���������  
	          String line = "";  
	          HashMap<String,String> idip =new HashMap<String,String>();
	          try {
				line = br.readLine();
				
				while (line != null) {  
					//System.out.println("line:"+line);
					  String[] content=line.split(",");
					 // content[0]
					  idip.put(content[0], content[1]);
		              line = br.readLine(); // һ�ζ���һ������  
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
