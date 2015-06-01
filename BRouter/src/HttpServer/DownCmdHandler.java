package HttpServer;

import internalStorageStructure.CmdBuffer;
import internalStorageStructure.MDIDIPMap;
import internalStorageStructure.MobileRouter;
import internalStorageStructure.Router;

import JDO.IDMapOperator;
/*
 * ר�Ŵ����·�������յ��������ʽ��MDID CMD
 * ���ȸ���MDID����һ����λê�ڵ���Ϊ�·�·������ȫ��MDIDת��ΪPANID��
 * ����λê�ڵ��ź�ת�����������浽������
 * ��λê�ڵ��ϴ�����ʱ����ʸ�������������Ӧ�������ȡ��
 */
public class DownCmdHandler {
	public void handleCmd(byte[]cmdByte,String cmd){
		if(!cmd.equals("---OK---") ){
			System.out.println("down cmd:"+cmd);
			int len=cmdByte.length-14;
			String idString = cmd.substring(0, 16);    //get mdid
			//�˴��������ݿ�
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
			
			//�ж��Ƿ�ʧACK��������ֱ�ӴӰ�����ȡ��ָ�����ֻ���ʾ���������
			String miss = cmd.substring(16,18);
			
			if(miss.equals("gd")||miss.equals("ge")){
				gateway=MobileRouter.getInstance().queryGateway(miss); 
			}
			
			//�жϽ���
			//get the gateway with strongest rssi
			//String gateway="a000000000000009";
			System.out.println("gateway:"+gateway+"--"+ new String(cmdChar));
			if(!gateway.equals(""))
			{
				/*�����ѡ���������·�����޸�����
				 * ���2�п��Զ�һ�������·� 
				 * Object[]cmdItem3={gatewayID,cmdChar};
				 * cmdBuffer.addCmd(cmdItem3);
				 */
				/**
				 * in order to adapt to the bluetooth
				 * ȥ�����̵�ַת�䣬ֱ�ӽ�ԭ�·����ݰ����ͻ�ȥcmdChar ��ΪcmdByte
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
