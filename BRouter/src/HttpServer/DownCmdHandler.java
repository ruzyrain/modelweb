package HttpServer;

import internalStorageStructure.CmdBuffer;
import internalStorageStructure.MDIDIPMap;
import internalStorageStructure.MobileRouter;
import internalStorageStructure.Router;

import JDO.IDMapOperator;
/*
 * 专门处理下发命令，接收到的命令格式：MDID CMD
 * 首先根据MDID查找一个定位锚节点作为下发路径，将全局MDID转换为PANID，
 * 将定位锚节点编号和转化后的命令包存到链表中
 * 定位锚节点上传数据时会访问该链表，如果有相对应的命令将其取走
 */
public class DownCmdHandler {
	public void handleCmd(byte[]cmdByte,String cmd){
		if(!cmd.equals("---OK---") ){
			System.out.println("down cmd:"+cmd);
			int len=cmdByte.length-14;
			String idString = cmd.substring(0, 16);    //get mdid
			//此处操作数据库
			char[] panID = 	MDIDIPMap.getInstance().QueryPanid(idString);  //query md panid
			byte[]cmdChar=new byte[len];
			
			System.arraycopy(cmdByte, 16, cmdChar, 2,len-2);
			if(panID!=null){
				cmdChar[0]=(byte) panID[0];
				cmdChar[1]=(byte) panID[1];
				int a,b;
				a=panID[0];
				b=panID[1];
				System.out.println("query result PAN id:"+a+","+b);
			}
			else{
				cmdChar[0]=(byte) 0XFF;
				cmdChar[1]=(byte) 0XFF;
				System.out.println("query result id is none");
			}
			String gateway=Router.getInstance().queryGateway(idString);
			
			//判断是否丢失ACK，若是则直接从包中提取出指定的手机标示填入命令表
			String miss = cmd.substring(16,18);
			
			if(miss.equals("gd")||miss.equals("ge")){
				gateway=MobileRouter.getInstance().queryGateway(miss); 
			}
			
			//判断结束
			//get the gateway with strongest rssi
			//String gateway="a000000000000009";
			System.out.println("gateway:"+gateway+"--"+ new String(cmdChar));
			if(!gateway.equals(""))
			{
				/*如果想选择多个网关下发命令，修改这里
				 * 添加2行可以多一个网关下发 
				 * Object[]cmdItem3={gatewayID,cmdChar};
				 * cmdBuffer.addCmd(cmdItem3);
				 */
				/**
				 * in order to adapt to the bluetooth
				 * 去掉长短地址转变，直接将原下发数据包发送回去cmdChar 改为cmdByte
				 */
				Object[] cmdItem = new Object[2];
				if(idString.substring(0, 4).equals(ConstantInterface.BLUE_MDID_MARK)){
					cmdItem[0] = gateway;
					cmdItem[1] = cmdByte;
				}
				else {
					cmdItem[0] = gateway;
					cmdItem[1] = cmdChar;	
				}
				//Object[]cmdItem3={"a000000000000009",cmdChar};
				//Object[]cmdItem4={"a000000000000004",cmdChar};
				CmdBuffer cmdBuffer = CmdBuffer.getInstance();
				cmdBuffer.addCmd(cmdItem);                                           //add gateway and cmd to cmdbuffer
				//cmdBuffer.addCmd(cmdItem3);
				//cmdBuffer.addCmd(cmdItem4);
				//System.out.println("cmd buffer size:"+cmdBuffer.getCmdList().size());
			}
			else
			{
				System.out.println("query gateway is none");
			}
		
		}
	}
}
