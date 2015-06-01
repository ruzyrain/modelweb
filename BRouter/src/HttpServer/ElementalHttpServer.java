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


import internalStorageStructure.MDIDIPMap;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.Printable;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Observable;

import net.sf.json.JSONObject;

import org.apache.http.ConnectionClosedException;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.Header;
import org.apache.http.HttpConnectionFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpServerConnection;
import org.apache.http.HttpStatus;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.BHttpConnectionBase;
import org.apache.http.impl.DefaultBHttpServerConnection;
import org.apache.http.impl.DefaultBHttpServerConnectionFactory;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpProcessorBuilder;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.http.protocol.HttpService;
import org.apache.http.protocol.ResponseConnControl;
import org.apache.http.protocol.ResponseContent;
import org.apache.http.protocol.ResponseDate;
import org.apache.http.protocol.ResponseServer;
import org.apache.http.protocol.UriHttpRequestHandlerMapper;
import org.apache.http.util.EntityUtils;

import Decoder.BASE64Decoder;
import threads.AskForCmd;
import threads.ClearUploadBuffer;
import threads.DeleteInvalidRouter;
import threads.GatewayIDIPTimer;
import threads.UIThead;

//import ElementalHttpServer.HttpFileHandler;


import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;
import javax.swing.JFrame;

/**
 * Basic, yet fully functional and spec compliant, HTTP/1.1 file server.
 * http server专门接受定位毛节点发送的数据
 * 这里是程序的入口
 */
public class ElementalHttpServer {
	public static String ipString=null;
    public static void main(String[] args) throws Exception {
    	new UIThead("console in").start();
    	new DeleteInvalidRouter("DeleteInvalidRouter").start();
    	new ClearUploadBuffer("clear upload buffer").start();
    	new AskForCmd("ask for cmd").start();
    	//new GatewayIDIPTimer("idip timeout").start();
    	MDIDIPMap.getInstance();
        if (args.length < 1) {
            System.err.println("Please specify document root directory");
            //System.exit(1);
        }
        // Document root directory
        //String docRoot = args[0];
        String docRoot ="D:/test";
        int port = 8071;//外网8071
        
        if (args.length >= 2) {
            port = Integer.parseInt(args[1]);
        }

        // Set up the HTTP protocol processor
        HttpProcessor httpproc = HttpProcessorBuilder.create()
                .add(new ResponseDate())
                .add(new ResponseServer("Test/1.1"))
                .add(new ResponseContent())
                .add(new ResponseConnControl()).build();

       
        // Set up request handlers
        UriHttpRequestHandlerMapper reqistry = new UriHttpRequestHandlerMapper();
        
       
        HttpFileHandler httpFileHandler=new HttpFileHandler(docRoot);
        
        reqistry.register("*", httpFileHandler);
       

        // Set up the HTTP service
        HttpService httpService = new HttpService(httpproc, reqistry);

        SSLServerSocketFactory sf = null;
        if (port == 8443) {
            // Initialize SSL context
            ClassLoader cl = ElementalHttpServer.class.getClassLoader();
            URL url = cl.getResource("my.keystore");
            if (url == null) {
                System.out.println("Keystore not found");
                System.exit(1);
            }
            KeyStore keystore  = KeyStore.getInstance("jks");
            keystore.load(url.openStream(), "secret".toCharArray());
            KeyManagerFactory kmfactory = KeyManagerFactory.getInstance(
                    KeyManagerFactory.getDefaultAlgorithm());
            kmfactory.init(keystore, "secret".toCharArray());
            KeyManager[] keymanagers = kmfactory.getKeyManagers();
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(keymanagers, null, null);
            sf = sslcontext.getServerSocketFactory();
        }

        Thread t = new RequestListenerThread(port, httpService, sf);
        t.setDaemon(false);
        t.start();
    }

    static class HttpFileHandler extends Observable implements HttpRequestHandler  {

        private final String docRoot;
 

        public HttpFileHandler(final String docRoot) {
            super();
            this.docRoot = docRoot;
        }

        public void handle(
                final HttpRequest request,
                final HttpResponse response,
                final HttpContext context) throws HttpException, IOException {
        	
            String method = request.getRequestLine().getMethod().toUpperCase(Locale.ENGLISH);
            if (!method.equals("GET") && !method.equals("HEAD") && !method.equals("POST")) {
                throw new MethodNotSupportedException(method + " method not supported");
            }
            
            String _url = request.getRequestLine().getUri();
            String[] splitUrl = _url.split("\\?");
//            System.err.println("_url is " + _url + request.getClass() + splitUrl[0]);
            ServerPacHandler sph=new ServerPacHandler();
//            System.err.println("heder is " + request.getHeaders("X-Client").length);
            if (request instanceof HttpEntityEnclosingRequest &&(request.getHeaders("X-Client").length == 0 ||
            		(request.getHeaders("X-Client").length != 0&&splitUrl[0].equals("/cc/mobile/bluetoothinfo/"))
            		)
            	) {

            	System.err.println("here");
            	int type = 0;
            	if (request.getHeaders("X-Client").length != 0) type = 1;
            	if(type == 0){ 
	            	HttpEntity entity = ((HttpEntityEnclosingRequest) request).getEntity(); 
	                byte[] entityContent = EntityUtils.toByteArray(entity);
	                sph.handle(entityContent,response,type); 
	            }
            	else{
            		System.err.println(request.getHeaders("X-Client")[0] + "收到数据包时间是：" + System.currentTimeMillis());
            		HttpEntity entity = ((HttpEntityEnclosingRequest) request).getEntity(); 
            		String entityString = EntityUtils.toString(entity);
            		JSONObject jObject = JSONObject.fromObject(entityString);
            		String value = jObject.getString("msg");
            		String returnString = "";
            		String imeiValue = request.getHeaders("X-Client")[0].getValue().split(";")[0].split("=")[1];
//            		for (int i = imeiValue.length();i < 16; i++){
//            			returnString += "0";
//            		}
  
            		value = "1.1.1.1;" + imeiValue + returnString + "," +value;
//            		BASE64Decoder decoder = new BASE64Decoder();
//            		byte[] bytes = decoder.decodeBuffer(value);
//            		System.err.println("entity is " + value);
            		sph.handle(value.getBytes(),response,type); 
            	}
            }
           
           
         
        }

    }

    static class RequestListenerThread extends Thread {

        private final HttpConnectionFactory<DefaultBHttpServerConnection> connFactory;
        private final ServerSocket serversocket;
        private final HttpService httpService;

        public RequestListenerThread(
                final int port,
                final HttpService httpService,
                final SSLServerSocketFactory sf) throws IOException {
            this.connFactory = DefaultBHttpServerConnectionFactory.INSTANCE;
            this.serversocket = sf != null ? sf.createServerSocket(port) : new ServerSocket(port);
            this.httpService = httpService;
        }

        @Override
        public void run() {
            System.out.println("Listening on port " + this.serversocket.getLocalPort());
            while (!Thread.interrupted()) {
                try {
                    // Set up HTTP connection
                    Socket socket = this.serversocket.accept();
                    ipString=socket.getInetAddress().toString();
                    System.out.println("Incoming connection from " + socket.getInetAddress());
                    HttpServerConnection conn = this.connFactory.createConnection(socket);

                    // Start worker thread
                    Thread t = new WorkerThread(this.httpService, conn);
                    t.setDaemon(true);
                    t.start();
                } catch (InterruptedIOException ex) {
                    break;
                } catch (IOException e) {
                    System.err.println("I/O error initialising connection thread: "
                            + e.getMessage());
                    break;
                }
            }
        }
    }

    static class WorkerThread extends Thread {

        private final HttpService httpservice;
        private final HttpServerConnection conn;

        public WorkerThread(
                final HttpService httpservice,
                final HttpServerConnection conn) {
            super();
            this.httpservice = httpservice;
            this.conn = conn;
            //this.conn.setSocketTimeout(8000);
        }

        @Override
        public void run() {
            //System.out.println("New connection thread");
            HttpContext context = new BasicHttpContext(null);
            try {
                while (!Thread.interrupted() && this.conn.isOpen()) {
                    this.httpservice.handleRequest(this.conn, context);
                }
            } catch (ConnectionClosedException ex) {
                System.err.println("Client closed connection");
            } catch (IOException ex) {
                System.err.println("I/O error: " + ex.getMessage());
            } catch (HttpException ex) {
                System.err.println("Unrecoverable HTTP protocol violation: " + ex.getMessage());
            } finally {
                try {
                	System.err.println("shut down the conn");
                    this.conn.shutdown();
                } catch (IOException ignore) {}
            }
        }

    }

}

