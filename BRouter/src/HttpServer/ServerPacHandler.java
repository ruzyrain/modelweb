package HttpServer;

import internalStorageStructure.GatewayIDIPMap;
import internalStorageStructure.MDIDIPMap;
import internalStorageStructure.MobileRouter;
import internalStorageStructure.Router;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import internalStorageStructure.CmdBuffer;
import JDO.UploadBuffer;



/*
 * 处理从定位毛节点接受到的数据包
 */
public class ServerPacHandler {
	public boolean handle(byte[] pac,HttpResponse response, int type){
		String originString;
		try {
			originString = new String(pac,"utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
		System.out.println("所有的数据包:" + originString);
		System.out.println("所有的数据包 byte:" + Arrays.toString(pac));
		
		String splitString[]=originString.split(";", 2);
		if(splitString.length==2){
			//String tip=splitString[0];
			originString=splitString[1];
			
			String splitContent[]=originString.split(",", 3);
			if(splitContent.length!=3){
				return false;
			}
			
			if(splitContent[2].charAt(0)==ConstantInterface.POSITIONING_HEADER){
	    		String GID=originString.substring(0, 16);
	    		deliverResponse(GID,response,type);
	            String srcString = originString.substring(41);
	            String stringarray[]=srcString.split(";"); 
	            int count[]=new int[2];
	            for(String string:stringarray){
	            	String array[]=string.split(",");
	            	if(array.length<6)
	            		return false;
	            	int rssi1,rssi2;
	    	        rssi1=Integer.valueOf(array[3]);
	            	rssi2=Integer.valueOf(array[5]);
	            	int rssi=Math.max(rssi1, rssi2);
	        		Router.getInstance().updateRouter(array[0],GID, rssi, array[6]);	
	        		if(rssi1==-999 && rssi2>-60){
	        			count[0]++;
	        		}
	        		else if(rssi2==-999 && rssi1>-60){
	        			count[1]++;
	        		}
	            }
	            if(stringarray.length>count[0]){
	            	count[0]=0;
	            }
	            if(stringarray.length>count[1]){
	            	count[1]=0;
	            }
	            if(count[0]==0 && count[1]==0){
	            	DiagnoseAntenna.getInstance().antennaRecover(GID);
	            }
	            else{
	            	DiagnoseAntenna.getInstance().updataMap(GID, count);
	            }
	            	
	            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	    		String theTime=df.format(new Date());
	    		
//	    		if(GID.equals("a000000000000008")){
//	    			FileTest.getInstance().PrintStreamDemo(theTime+","+originString);
//	    		}
	    		try {
	    			System.out.println("推送到定位服务器的数据是:" + originString);
//	    			new ElementalHttpPostBytes().post2Server(originString.getBytes(), ConstantInterface.SERVICEID);
//	    			new ElementalHttpPostBytes().post2Server(originString.getBytes(), ConstantInterface.SERVICEID1);
	    			ElementalHttpPostBytes post = new ElementalHttpPostBytes(originString.getBytes(),ConstantInterface.SERVICEID);
	    			new Thread(post,"new post").start();
	    		} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        }
	        else {
	        	
	            uploadHandler(pac,response,type); //to be freed
	        }  
		}
		else{
			System.out.println("split length not 2:"+originString);
			String json = "{\"err\":1,\"msg\":\"fail\"}";
			byte[] responseMsg = json.getBytes();
			HttpEntity entity = new ByteArrayEntity(responseMsg, ContentType.create("application/json", "UTF-8"));
			response.setEntity(entity);
	        response.setStatusCode(HttpStatus.SC_OK);
		}
		//GatewayIDIPMap.getInstance().printGatewayIDIPMap();
		return true;

	}
	
	/*
	 * 上传数据格式 gatewayip;gatewayid,time,data
	 */
	public boolean uploadHandler(byte[] pac,HttpResponse response,int type){
		String contentString;
		try {
			contentString = new String(pac,"utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
	
		//get gateway ip
		String splitString[]=contentString.split(";", 2);    
		String tip=splitString[0];
		 
		if(splitString.length==2){
			contentString=splitString[1];
			String GID;
			if(type == 1) GID=contentString.substring(0, ConstantInterface.GATEWY_GID_LEN-1);
			else GID=contentString.substring(0, ConstantInterface.GATEWY_GID_LEN);   //get gateway id
			deliverResponse(GID,response,type);    //send http response
			
			byte[] sendByte = null;
			//网关心跳包
			if(contentString.length()==ConstantInterface.GATEWY_HEARTBEAT_LENGTH && contentString.substring(37, 38).equals(ConstantInterface.GATEWAYHEARTBEAT_HEADER)){
	            try {
	            	System.out.println("心跳输出:" + contentString);
//	            	new ElementalHttpPostBytes().post2Server(contentString.getBytes(), ConstantInterface.SERVERID);
    				ElementalHttpPostBytes post = new ElementalHttpPostBytes(contentString.getBytes(),ConstantInterface.SERVERID);
	    			new Thread(post,"new post2").start();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            GatewayIDIPMap.getInstance().addIDIPMapItem(GID, tip);  //store GID--ip map relation
	        }
	        else{
	        	//小节点编号
				String MdID;
				String idString;
				//如果是手机发送
				if (type == 1){
					
					System.err.println("进入到Mobile网关处理阶段");
					
		        	//查找第一个分号位置
					int i; 
		        	for (i = 0; i < pac.length; i++){
		        		if(pac[i] == 59) break;
		        	}
		      
					//查找第一个逗号位置
					int j;
					for (j = i + 1; j < pac.length; j++){
						if(pac[j] == 44) break;
					}
					
					//查找第二个逗号位置
					int k;
					for (k = j + 1; k < pac.length; k++){
						if(pac[k] == 44) break;
					}
					//查找加号位置
					int p;
					for (p = k + 1; p < pac.length; p++){
						if(pac[p] == 43) break;
					}
					
					byte[] realPac = new byte[pac.length -8];
					if(pac.length > 75){
						//将拆解的包重新组装
						int l = 0;
						for (int m = 0 ; m <= k; m++,l++){
							realPac[l] = pac[m];
						}
//						System.err.println("组合1111： " + k );
						for(int m = k + 1 ; m < pac.length; m++,l++){
							if(m >= k + 23 && m <= k + 38){
//								System.err.println("组合： " + m);
								byte temp = (byte) ((pac[m] << 4) & 0xf0);
								m++;
								realPac[l] = (byte) (temp + pac[m]);
							}
							else {
//								System.err.println("m is : " + m + "l is :" + l + " realpac is " + realPac.length + "\n orignal pac is " + pac.length);
								realPac[l] = pac[m];
							}
						}
						//完成
					}
					else{
						realPac = pac;
					}
					
					byte[] MdIDByte = new byte[16];
					System.arraycopy(realPac, k + 1, MdIDByte, 0, 16);
					String mdID ="";
					try {
						mdID = new String(MdIDByte,"utf-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					byte[] MissByte = new byte[2];
					System.arraycopy(realPac, k + 17, MissByte, 0, 2);
					String identify = MissByte.toString();
					
					idString = mdID;
					if(identify.equals(ConstantInterface.BLUE_MISS_MARK) || identify.equals(ConstantInterface.BLUE_RECONNECT_MARK)){
						System.err.println("进入到失联包");
						MobileRouter.getInstance().updateRouter(mdID, GID,identify, "latesttime");
						System.err.println("the mobile router is :" + MobileRouter.getInstance().queryGateway(mdID));
					}
					//非失联包更新普通router
					else{
						Router.getInstance().updateRouter(mdID,GID,-(int)(contentString.split("\\+", 2)[1].charAt(0)) , 
	    					contentString.split(",",3)[1]);
					}
					//组包，将网关标志填充为小结点标志，去掉小结点长标志
	    			UploadBuffer uploadBuffer=UploadBuffer.getInstance();

		        	byte[] srcByte = new byte[realPac.length - i - 1];
		        	System.arraycopy(realPac, i + 1, srcByte, 0, realPac.length - i - 1);
		        	
		        	System.err.println("the source byte is " + Arrays.toString(srcByte));
	    			sendByte = new byte[srcByte.length-16];
		        	System.arraycopy(MdIDByte, 0, sendByte, 0, ConstantInterface.MD_GID_LEN);
		        	System.arraycopy(srcByte, 16, sendByte, 16, 21);
	        		System.arraycopy(srcByte, 53, sendByte, 37, sendByte.length-37);
	        		
					/**
					MdID = contentString.substring(ConstantInterface.BLUE_MD_POS,ConstantInterface.BLUE_MD_POS + ConstantInterface.MD_GID_LEN);
					//失联包与恢复包处理
					String MissPos = contentString.substring(ConstantInterface.MISS_POS, ConstantInterface.MISS_POS+2);
					System.err.println("Mobile网关包标志位是：" + MissPos);
					idString = MdID;
					
					//失联包更新手机的router
					if(MissPos.equals(ConstantInterface.BLUE_MISS_MARK) || MissPos.equals(ConstantInterface.BLUE_RECONNECT_MARK)){
						System.err.println("进入到失联包");
						MobileRouter.getInstance().updateRouter(MdID, GID,MissPos, "latesttime");
						System.err.println("the mobile router is :" + MobileRouter.getInstance().queryGateway(MdID));
					}
					//非失联包更新普通router
					else{
						Router.getInstance().updateRouter(idString,GID,-(int)(contentString.split("\\+", 2)[1].charAt(0)) , 
	    					contentString.split(",",3)[1]);
					}
					
					
//					//组包，将网关标志填充为小结点标志，去掉小结点长标志
//	    			UploadBuffer uploadBuffer=UploadBuffer.getInstance();
//		        	int i; 
//		        	for (i = 0; i < pac.length; i++){
//		        		if(pac[i] == 59) break;//在字节数组中找到“；”位置
//		        	}
//		        	byte[] srcByte = new byte[pac.length - i - 1];
//		        	System.arraycopy(pac, i + 1, srcByte, 0, pac.length - i - 1);
//		        	
//		        	System.err.println("the source byte is " + Arrays.toString(srcByte));
//	    			sendByte = new byte[srcByte.length-16];
//		        	System.arraycopy(idString.getBytes(), 0, sendByte, 0, ConstantInterface.MD_GID_LEN);
//		        	System.arraycopy(srcByte, 16, sendByte, 16, 21);
//	        		System.arraycopy(srcByte, 53, sendByte, 37, sendByte.length-37);
	        		//组包完成
	        		 * 
	        		 */
				}
				
				//如果是蓝牙网关发送
				else if(contentString.length() > 55 && contentString.substring(ConstantInterface.BLUE_MD_POS + 2,ConstantInterface.BLUE_MD_POS + 6).equals("####")) {//是蓝牙网关发送过来
					System.err.println("进入到Bluetooth网关处理阶段");
					MdID = contentString.substring(ConstantInterface.BLUE_MD_POS + 2,ConstantInterface.BLUE_MD_POS + 2 + ConstantInterface.MD_GID_LEN);
					idString = MdID;
					
					String MissPos = contentString.substring(ConstantInterface.BLUE_MD_POS + 2 + ConstantInterface.MD_GID_LEN, ConstantInterface.BLUE_MD_POS + 4 + ConstantInterface.MD_GID_LEN);
					System.err.println("Bluetooth网关包标志位是：" + MissPos);
					//组包，将网关标志填充为小结点标志，去掉小结点长、短标志
					UploadBuffer uploadBuffer=UploadBuffer.getInstance();
		        	int i; 
		        	for (i = 0; i < pac.length; i++){
		        		if(pac[i] == 59) break;//在字节数组中找到“；”位置
		        	}
		        	byte[] srcByte = new byte[pac.length - i - 1];
		        	System.arraycopy(pac, i + 1, srcByte, 0, pac.length - i - 1);
		        	
		        	System.err.println("the source byte is " + Arrays.toString(srcByte));
		        	sendByte = new byte[srcByte.length-18];
		        	System.arraycopy(idString.getBytes(), 0, sendByte, 0, ConstantInterface.MD_GID_LEN);
		        	System.arraycopy(srcByte, 16, sendByte, 16, 21);
	        		System.arraycopy(srcByte, 55, sendByte, 37, sendByte.length-37);
	        		//组包完成
				}
				
				//是zigbee网关发送
				else{
					System.err.println("进入到Zigbee网关处理阶段");
					String panid = contentString.substring(37,39);
		    		//ADD BY TAOYE 
//		    		byte[] firid = {pac[ConstantInterface.PANID1_POS]};
//		    		
//		    		System.out.println("第一位是：" +new String(firid));
//		    		
//		    		byte[] secid = {pac[ConstantInterface.PANID2_POS]};
//		    		
//		    		System.out.println("第二位是：" +new String(secid));
		    		//Md心跳包
		        	if(pac[ConstantInterface.TYPE1_POS]==ConstantInterface.MD_HEATBEAT){
		    			System.out.println("进入到pan更新<><><><><><><>>");
		    			byte[]mdidByte=new byte[ConstantInterface.MD_GID_LEN];
		    			System.arraycopy(pac, ConstantInterface.TYPE2_POS+2, mdidByte, 0, ConstantInterface.MD_GID_LEN);
		    			String mdid=new String(mdidByte);
		        		MDIDIPMap.getInstance().addMapItem(mdid, panid);
		        		
		    		}
		        	
		        	idString=MDIDIPMap.getInstance().getGID(panid);
		        	if(idString==null){
		        		return false;
		        	}
		        	String MissPos = contentString.substring(39,41);
					System.err.println("Zigbee网关包标志位是：" + MissPos);
					
					//组包，将网关标志填充为小结点标志，去掉小结点短标志
		        	UploadBuffer uploadBuffer=UploadBuffer.getInstance();
		        	int i; 
		        	for (i = 0; i < pac.length; i++){
		        		if(pac[i] == 59) break;//在字节数组中找到“；”位置
		        	}
		        	byte[] srcByte = new byte[pac.length - i - 1];
		        	System.arraycopy(pac, i + 1, srcByte, 0, pac.length - i - 1);
		        	
		        	System.err.println("the source byte is " + Arrays.toString(srcByte));
		        	sendByte = new byte[srcByte.length-2];
		        	System.arraycopy(idString.getBytes(), 0, sendByte, 0, ConstantInterface.MD_GID_LEN);
		        	System.arraycopy(srcByte, 16, sendByte, 16, 21);
	        		System.arraycopy(srcByte, 39, sendByte, 37, sendByte.length-37);
	        		//组包完成
		        	
				}	

				//开始推送数据包
				System.out.println("开始推送非心跳包:" + Arrays.toString(sendByte));
	        	if(true)
	        	{   //filter the same package
	        		
	        		//uploadBuffer.addItem(contentString);       //add to the buffer , to act as filter to the same package
            		
        			try {
//        				System.out.println("post to server:"+Arrays.toString(sendByte));
//        				new ElementalHttpPostBytes().post2Server(sendByte, ConstantInterface.SERVERID);  //post to the server
        				ElementalHttpPostBytes post = new ElementalHttpPostBytes(sendByte,ConstantInterface.SERVERID);
    	    			new Thread(post,"new post1").start();
        				
        			} catch (Exception e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
	        		
	        	}
	        	else{
	        		//System.out.println("duplicate:"+contentString);
	        	}
	        	
	        }
			
			return true;
		}
		else{
			System.out.println("splitString.length!=2"+contentString);
		}
		return false;

	}
	public byte[] getCmd(String gatewayId){
		CmdBuffer cmdBuffer = CmdBuffer.getInstance();
		return cmdBuffer.getCmdByGatewayId(gatewayId);
	}
	public Boolean deleteCmd(Object[] cmd ){
		CmdBuffer cmdBuffer = CmdBuffer.getInstance();
		return cmdBuffer.deleteCmd(cmd);
	}
	public void deliverResponse(String GatewayID,HttpResponse response,int type){
        byte[] responseMsg = "OK!".getBytes();
        byte[] cmdBytes = getCmd(GatewayID);
        
        /**
         * 根据Bluetooth修改，判断是否是手机上传的数据包
         */
        String json = "";
        HttpEntity entity;
        if(cmdBytes!=null){
	        if (type == 1){
	        	if (cmdBytes.length > 25){
		        	StringBuilder str = new StringBuilder();
					for (int i = 0; i < cmdBytes.length; i++){
						if( i >=22 && i <= 29){
							str.append((char)((cmdBytes[i] >> 4) & 0x0f ));
							str.append((char)((cmdBytes[i]) & 0x0f ));
						}
						else
							str.append((char)cmdBytes[i]);
					}
					json = "{\"err\":0,\"msg\":" + "\"" +str.toString() + "\"" + "}";
	        	}
	        	else{
	        		try {
						json = "{\"err\":0,\"msg\":" + "\"" +new String(cmdBytes, "utf-8") + "\"" + "}";
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	}
				
				
	        	responseMsg = json.getBytes();
	        	System.err.println("cmdByte to String is " + json + "当前时间是:" + System.currentTimeMillis() + Arrays.toString(responseMsg));
	        	entity = new ByteArrayEntity(responseMsg, ContentType.create("application/json", "UTF-8"));
	        }
	        else{
	        	
	        	responseMsg = cmdBytes;
	        	entity = new ByteArrayEntity(responseMsg,
	                    ContentType.create("application/json", "UTF-8"));
	        }
        }
	    else{
	    	if (type == 1){
	    		System.err.println("return ok Json 数据包 当前时间是:" + System.currentTimeMillis());
	    		json = "{\"err\":0,\"msg\":\"ok\"}";
	    		responseMsg = json.getBytes();
	    		entity = new ByteArrayEntity(responseMsg, ContentType.create("application/json", "UTF-8"));
        	}
	    	else {
	    	    entity = new ByteArrayEntity(responseMsg,
	    	                ContentType.create("application/json", "UTF-8"));
	    	}
//	        entity = new ByteArrayEntity(responseMsg,
//                ContentType.create("text/html", "UTF-8"));
	        
	    }
        //修改完成
        response.setStatusCode(HttpStatus.SC_OK);
        response.setEntity(entity);
 
        try {
			System.err.println("数据下发完成，数据：------" + new String(responseMsg,"utf-8") + "------下发时间是" + new Date() + "byte 数组是" +  Arrays.toString(responseMsg));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	    if(cmdBytes!=null)
	    {
		    Object[] cmdItem=new Object[2];
		    cmdItem[0]= GatewayID;
		    byte[]cmdItem1=new byte[cmdBytes.length];
		    System.arraycopy(cmdBytes, 0, cmdItem1, 0, cmdBytes.length);
		    cmdItem[1]= cmdItem1;
		    deleteCmd(cmdItem);
		    System.out.println("deliver finished! "+cmdItem[0].toString()+","+new String(cmdItem1));
	    }   
		
	}
}
