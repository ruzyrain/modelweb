package threads;

import internalStorageStructure.GatewayIDIPMap;
import internalStorageStructure.Router;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/*
 * ͨ������̨������������ѯ�����Ϣ
 * ����p��ѯ����ID IPӳ��
 * ����r��ѯ�ڵ㵽��λê�ڵ�·��
 */
public class UIThead extends Thread {
    public UIThead(String threadName) {
        super(threadName);
    }

    public void run() {
      
        while(true){
        	Scanner sc = new Scanner( System.in );
           
            if(sc.next().equals("p")){
            	GatewayIDIPMap.getInstance().printGatewayIDIPMap();
            }
            else if(sc.next().equals("r")){
            	Router.getInstance().printRouter();
            }
            else{
            	System.out.println( "tap 'p' to see gateway id ip map ");
            	System.out.println( "tap 'r' to see gateway id --md ip map ");
            }
            
        }
        
    }
}