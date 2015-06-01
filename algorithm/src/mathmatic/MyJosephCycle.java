package mathmatic;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MyJosephCycle {	
	public static void main(String[] args){
		// TODO Auto-generated method stub
		try
		{
			InputStreamReader isr=new InputStreamReader(System.in);
			BufferedReader br=new BufferedReader(isr);
			System.out.println("请输入参加该游戏小孩的个数：");
			String joinNmu=br.readLine();
			System.out.println("请输入您想从编号为几的小孩开始报数？");
			String n1=br.readLine();
			System.out.println("请输入您想数到几的小孩出列？");
			String n2=br.readLine();
			int num1=Integer.parseInt(n1);
			int num2=Integer.parseInt(n2);
			int joinLen=Integer.parseInt(joinNmu);
			CycleLink cycleLink=new CycleLink();
			cycleLink.setLen(joinLen);
			cycleLink.createLink();
			cycleLink.setK(num1);
			cycleLink.setM(num2);
			cycleLink.play();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
		
}

//定义节点
	class Child{
		int num;//定义节点编号
		Child nextChild = null;
		public Child(int no){
			this.num = no;
		}
	}
	
	//定义环形链表结构
	class CycleLink{
		
		Child firstChild = null;//头结点
		Child temp = null;//临时指针
		Child temp2 = null;
		int len = 0;//链表长度
		int k = 0;//第几个人开始数数
		int m = 0;//每次长度
		public void setLen(int len)
		{
			this.len=len;
		}
		//设置m的值
		public void setM(int m)
		{
			this.m=m;
		}
		//设置从第几个人开始数数
		public void setK(int k)
		{
			this.k=k;
		}
		
		//初始化循环链表
		public void createLink() {
			for (int i = 1; i<=len; i++){
				if (i == 1) {//头结点
					Child fir = new Child(i);
					firstChild = fir;
					temp = fir;
				}
				else if (i == len){//尾节点
					Child las = new Child(i);
					temp.nextChild = las;
					temp = las;
					temp.nextChild = firstChild;
				}
				else {//继续创建节点
					Child child = new Child(i);
					temp.nextChild = child;
					temp = child;
				}
			}
			
		}
		public void play() {
			
			Child temp = firstChild;
			for (int i = 1; i<k; i++){
				temp = temp.nextChild;
			}
			while (this.len != 1) {
				for (int i = 1; i<m-1; i++) {//数M步
					temp = temp.nextChild;
				}
				temp2 = temp;
				temp = temp.nextChild;
				temp2.nextChild = temp.nextChild;
				temp = temp.nextChild;
				this.len--;
			}
		
			System.out.println("最终结果是"+temp.num);
		}		
	}

