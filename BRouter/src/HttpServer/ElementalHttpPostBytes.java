package HttpServer;


/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */




/*
 * 
 */

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.DefaultBHttpClientConnection;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpProcessorBuilder;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.protocol.RequestConnControl;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestExpectContinue;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;
import org.apache.http.util.EntityUtils;

import internalStorageStructure.CmdBuffer;

/**
 * Elemental example for executing multiple POST requests sequentially.
 * 向httpserver  post数据
 */
public class ElementalHttpPostBytes implements Runnable {
	
	private byte[] content;
	private String ip;
	
	public ElementalHttpPostBytes(){
		
	}
	public ElementalHttpPostBytes(byte[] content,String ip){
		this.content = content;
		this.ip = ip;
	}
	
	public void post2Server(byte[] content,String ip) throws Exception{
        HttpProcessor httpproc = HttpProcessorBuilder.create()
        .add(new RequestContent())
        .add(new RequestTargetHost())
        .add(new RequestConnControl())
        .add(new RequestUserAgent("Test/1.1"))
        .add(new RequestExpectContinue(true)).build();
          
    HttpRequestExecutor httpexecutor = new HttpRequestExecutor();

    HttpCoreContext coreContext = HttpCoreContext.create();
    
    HttpHost host = new HttpHost(ip, 8090);
    //HttpHost host = new HttpHost("192.168.111.191", 8090);
    coreContext.setTargetHost(host);

    DefaultBHttpClientConnection conn = new DefaultBHttpClientConnection(8 * 1024);
    ConnectionReuseStrategy connStrategy = DefaultConnectionReuseStrategy.INSTANCE;
    try {
    	
    	HttpEntity[] requestBodies = {
                new ByteArrayEntity(content,ContentType.create("text/plain", Consts.UTF_8))
        };
    	
        for (int i = 0; i < requestBodies.length; i++) {
            if (!conn.isOpen()) {
            	try{
            		Socket socket = new Socket(host.getHostName(), host.getPort());
                    conn.bind(socket);
            	}
            	catch(Exception e)
            	{
            		//System.out.println("connect timeout exception.server is not open");
            		//e.printStackTrace();
            	}
            	   
            }
            BasicHttpEntityEnclosingRequest request = new BasicHttpEntityEnclosingRequest("POST",
                    "/test.txt");
            request.setEntity(requestBodies[i]);
            //System.out.println(">> Request URI: " + request.getRequestLine().getUri());

            //System.out.println("***********************"+content.getBytes().length);
            httpexecutor.preProcess(request, httpproc, coreContext);
            String cmdString="";
            HttpResponse response=null;
            try {
            	 response = httpexecutor.execute(request, conn, coreContext);
            	 httpexecutor.postProcess(response, httpproc, coreContext);
                
                 HttpEntity he=response.getEntity();
                 
                 //cmdString = EntityUtils.toString(he);
                 
                 byte[]cmdByte = EntityUtils.toByteArray(he);
                 cmdString=new String(cmdByte);
                // System.out.println("<< Response: " + response.getStatusLine());
                 
                 if(response.getStatusLine().getStatusCode()==200){
                	// oldCmdHandler(cmdString);
                	 new DownCmdHandler().handleCmd(cmdByte,cmdString);
                 }
                 else{
                	 System.out.println(ip);
                 }
                 //System.out.println("=============="+cmdString);
                 if (!connStrategy.keepAlive(response, coreContext)) {
                     conn.close();
                 } else {
                     //System.out.println("Connection kept alive...");
                 }
			} catch (Exception e) {
				// TODO: handle exception
				//e.printStackTrace();
			}
            
           
           
            
        }
    } finally {
        conn.close();
    }
	}

	
    public static void main(String[] args){
//    	ElementalHttpPostBytes elementalHttpPost = new ElementalHttpPostBytes();
//    	//char[]panid={0x2,0x3};
//		//String string=new String(panid);
//    	String cmd="1122222222333333eeee";
//    	//elementalHttpPost.cmdHandler(cmd);
//    	try {
//			//new ElementalHttpPost().post2Server("test content");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    	
    	System.out.println("开始时间是：" + new Date());
    	String originString = "192.168.10.117;a000000000000117,2015-01-30 15:45:29,9,1;1122222222333333,17,G1,-74,G2,-83,2015-01-30 15:45:28";
    	try{
    		new ElementalHttpPostBytes().post2Server(originString.getBytes(), ConstantInterface.SERVICEID);
    	}
    	catch(Exception e)
    	{
    		//System.out.println("connect timeout exception.server is not open");
    	
    		//e.printStackTrace();
    	}
    	
    	System.out.println("结束时间是：" + new Date());  
    
    }


	@Override
	public void run() {
		// TODO Auto-generated method stub
		 HttpProcessor httpproc = HttpProcessorBuilder.create()
			        .add(new RequestContent())
			        .add(new RequestTargetHost())
			        .add(new RequestConnControl())
			        .add(new RequestUserAgent("Test/1.1"))
			        .add(new RequestExpectContinue(true)).build();
			          
			    HttpRequestExecutor httpexecutor = new HttpRequestExecutor();

			    HttpCoreContext coreContext = HttpCoreContext.create();
			    
			    HttpHost host = new HttpHost(ip, 8090);
			    //HttpHost host = new HttpHost("192.168.111.191", 8090);
			    coreContext.setTargetHost(host);

			    DefaultBHttpClientConnection conn = new DefaultBHttpClientConnection(8 * 1024);
			    ConnectionReuseStrategy connStrategy = DefaultConnectionReuseStrategy.INSTANCE;
			    try {
			    	
			    	HttpEntity[] requestBodies = {
			                new ByteArrayEntity(content,ContentType.create("text/plain", Consts.UTF_8))
			        };
			    	
			        for (int i = 0; i < requestBodies.length; i++) {
			            if (!conn.isOpen()) {
			            	try{
			            		Socket socket = new Socket(host.getHostName(), host.getPort());
			                    conn.bind(socket);
			            	}
			            	catch(Exception e)
			            	{
			            		//System.out.println("connect timeout exception.server is not open");
			            		//e.printStackTrace();
			            	}
			            	   
			            }
			            BasicHttpEntityEnclosingRequest request = new BasicHttpEntityEnclosingRequest("POST",
			                    "/test.txt");
			            request.setEntity(requestBodies[i]);
			            //System.out.println(">> Request URI: " + request.getRequestLine().getUri());

			            //System.out.println("***********************"+content.getBytes().length);
			            try {
							httpexecutor.preProcess(request, httpproc, coreContext);
						} catch (HttpException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			            String cmdString="";
			            HttpResponse response=null;
			            try {
			            	 response = httpexecutor.execute(request, conn, coreContext);
			            	 httpexecutor.postProcess(response, httpproc, coreContext);
			                
			                 HttpEntity he=response.getEntity();
			                 
			                 //cmdString = EntityUtils.toString(he);
			                 
			                 byte[]cmdByte = EntityUtils.toByteArray(he);
			                 cmdString=new String(cmdByte);
			                // System.out.println("<< Response: " + response.getStatusLine());
			                 
			                 if(response.getStatusLine().getStatusCode()==200){
			                	// oldCmdHandler(cmdString);
			                	 new DownCmdHandler().handleCmd(cmdByte,cmdString);
			                 }
			                 else{
			                	 System.out.println(ip);
			                 }
			                 //System.out.println("=============="+cmdString);
			                 if (!connStrategy.keepAlive(response, coreContext)) {
			                     conn.close();
			                 } else {
			                     //System.out.println("Connection kept alive...");
			                 }
						} catch (Exception e) {
							// TODO: handle exception
							//e.printStackTrace();
						}
			            
			           
			           
			            
			        }
			    } finally {
			        try {
						conn.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    }
	}

}
