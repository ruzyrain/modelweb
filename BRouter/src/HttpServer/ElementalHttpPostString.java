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



import java.net.Socket;

import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
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
/**
 * Elemental example for executing multiple POST requests sequentially.
 */
public class ElementalHttpPostString {
	DefaultBHttpClientConnection conn=null;
	
	
	public ElementalHttpPostString(){
	    conn = new DefaultBHttpClientConnection(8 * 1024);

	}
	public void post2Server(String ip,String content) throws Exception{
		HttpHost host=null;
		//HttpRequestExecutor httpexecutor=null;
		HttpProcessor httpproc=null;
		HttpCoreContext coreContext=null;
		ConnectionReuseStrategy connStrategy=null;
		BasicHttpEntityEnclosingRequest request = new BasicHttpEntityEnclosingRequest("POST",
	            "/test.txt");
		httpproc = HttpProcessorBuilder.create()
		        .add(new RequestContent())
		        .add(new RequestTargetHost())
		        .add(new RequestConnControl())
		        .add(new RequestUserAgent("Test/1.1"))
		        .add(new RequestExpectContinue(true)).build();

		    HttpRequestExecutor httpexecutor = new HttpRequestExecutor();

		    coreContext = HttpCoreContext.create();
		    host = new HttpHost(ip, 8090);
		    connStrategy = DefaultConnectionReuseStrategy.INSTANCE;
		    coreContext.setTargetHost(host);
		try {
	    	HttpEntity[] requestBodies = {
	                new StringEntity(content,ContentType.create("text/plain", Consts.UTF_8))
	        };
	        for (int i = 0; i < requestBodies.length; i++) {
	            if (!conn.isOpen()) {
	            	try{
	            		Socket socket = new Socket(host.getHostName(), host.getPort());
	                    conn.bind(socket);
	            	}
	            	catch(Exception e)
	            	{
	            		//e.printStackTrace();
	            		conn.close();
	            	}	                
	            }
	            request.setEntity(requestBodies[i]);
	            //System.out.println(">> Request URI: " + request.getRequestLine().getUri());
	            //System.out.println("***********************"+content.getBytes().length);
	            httpexecutor.preProcess(request, httpproc, coreContext);
	            String cmdString="";
	            HttpResponse response=null;
	            try {
	            	 response = httpexecutor.execute(request, conn, coreContext);
	            	 httpexecutor.postProcess(response, httpproc, coreContext);
	
	                 //System.out.println(ip+"<< Response: " + response.getStatusLine());
	            	 //System.out.println("=============="+cmdString);
	                 HttpEntity he=response.getEntity();
	                 byte[]cmdByte = EntityUtils.toByteArray(he);
	                 cmdString=new String(cmdByte);
	                 if(response.getStatusLine().getStatusCode()==200){
	                 	// oldCmdHandler(cmdString)
	                 	 new DownCmdHandler().handleCmd(cmdByte,cmdString);
	                  }
	                  else{
	                 	 System.out.println(ip);
	                  }
	                
	                 if (!connStrategy.keepAlive(response, coreContext)) {
	                     conn.close();
	                 } else {
	                     //System.out.println("Connection kept alive...");
	                 }
				} catch (Exception e) {
					
					conn.close();
					// TODO: handle exception
				} 
	        }
	    } finally {
	       // conn.close();
	    }
	}

	
    public static void main(String[] args){
        ElementalHttpPostString elementalHttpPost = new ElementalHttpPostString();
    	String cmd="661111111112111111111211111111121111111112111111111211111111121111111112111d123456789012345";
    	//elementalHttpPost.cmdHandler(cmd);
    	try {
			//new ElementalHttpPost().post2Server(ConstantInterface.SERVICEID,cmd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
