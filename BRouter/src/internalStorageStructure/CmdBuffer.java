package internalStorageStructure;

import java.util.ArrayList;
import java.util.Arrays;

import javassist.expr.NewArray;

/*
 * 存取下发命令链表
 * 每条链表信息包括两项定位锚节点编号和命令
 */
public class CmdBuffer {

	private static CmdBuffer instance = null;
	private ArrayList<Object[]>cmdList=new ArrayList<Object[]>();
	private CmdBuffer() {
		
	}
		
	public static CmdBuffer getInstance() {
		if (instance == null) { 
		      instance = new CmdBuffer(); 
	    }
		         return instance;
	}
	public synchronized void addCmd(Object[] cmd){
		Boolean flag=false;
		for (int i = 0; i < cmdList.size(); i++) {
			if(cmdList.get(i)[0].toString().equals(cmd[0]) 
					&& Arrays.equals(((byte[])cmdList.get(i)[1]), (byte[])cmd[1]))
			{
				flag = true;
			}
		}
		if(!flag)
		{
			cmdList.add(cmd);
		}
	}
	public ArrayList<Object[]> getCmdList(){
		return cmdList;
	}
	public synchronized boolean deleteCmd(Object[] cmd){
		for (int i = 0; i <cmdList.size(); i++) {
			if(cmdList.get(i)[0].toString().equals(cmd[0].toString())&&
					Arrays.equals((byte[])cmdList.get(i)[1], (byte[])cmd[1]))
			{
				cmdList.remove(i);
				return true;
			}
		}

		return false;
	}
	public synchronized byte[] getCmdByGatewayId(String gatewayId){
		for (Object[] cmdItem : cmdList) {
			if(cmdItem[0].toString().equals(gatewayId)){
				return (byte[])cmdItem[1];
			}
		}
		return null;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*CmdBuffer cmdBuffer = new CmdBuffer();
		String cmd="661111111112111111111211111111121111111112111111111211111111121111111112111d123456789012345";
		String[]ac = new String[2];
		ac[0]="d123456789012345";
		ac[1]="661111111112111111111211111111121111111112111111111211111111121111111112111d123456789012345";
		cmdBuffer.addCmd(ac);
		ArrayList<String[]>list=cmdBuffer.getCmdList();
		for (String[] string : list) {
			System.out.println(string[1]);
		}
		cmdBuffer.deleteCmd(ac);
		list=cmdBuffer.getCmdList();
		System.out.println(list.size());*/

	}

}
