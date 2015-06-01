package mathmatic;
import java.io.*;
public class JosephCycle {
	/**
	 * 作者：徐守威
	 * 功能：约瑟夫问题(丢手帕问题)
	 * 具体问题：设编号为1,2,3....n的n个人围坐一圈，约定编号为k（1<=k<=n）的人从1开始
	 * 报数，数到m的那个人出列，它的下一位从一开始报数，报到m的那个人又出列，以此类推，
	 * 直到所有人出列为止，如此产生一个出列编号的序列...
	 * 解决方案：链表
	 */
		/**
		 * @param args
		 */
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
				CycLink cyclink=new CycLink();
				cyclink.setLen(joinLen);
				cyclink.createLink();
				cyclink.setK(num2);
				cyclink.setM(2);
				cyclink.show();
				cyclink.play();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
	}

	//构建各个孩子
	class Childhood
	{
		//定义Childhood的编号
		int no;
		//定义可以指向下一个的指针
		
		Childhood nextChildhood=null;
		//创建构造函数
		public Childhood(int no)
		{
			//赋编号
			this.no=no;
		}
	}
	
	//构建一个环形链表
	class CycLink
	{
		//先定义一个指向链表第一个小孩的引用
		//指向第一个小孩的引用，不能动
		Childhood firstChildhood=null;
		//定义一个游标，相当于跑龙套
		Childhood temp=null;
		//定义一个表示大小的的len，表示共有几个小孩
		int len=0;
		int k=0;
		int m=0;
		//构造函数，用来设置环形链表的大小
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
		//游戏实现
		public void play()
		{
			//设置跑龙套的小孩
			Childhood temp=this.firstChildhood;
			//1.找到开始数数的人
			for(int i=1;i<k;i++)
			{
				temp=temp.nextChildhood;
			}
			while(this.len!=1)
			{
				//2.数m下
				for(int j=1;j<m;j++)
				{
					temp=temp.nextChildhood;
				}
				//3.找到要出圈的前一个小孩
				//定义一个2号跑龙套的人
				Childhood temp2=temp;
				while(temp2.nextChildhood!=temp)
				{
					temp2=temp2.nextChildhood;
				}
				//4.数完了过后将数到m的小孩退出圈
				temp2.nextChildhood=temp.nextChildhood;
				//让temp指向下一个数数的小孩
				temp=temp.nextChildhood;
				this.len--;
			}
			//输出最后一个小孩
			System.out.println("最后出圈的小孩是："+temp.no+"号小孩！");
		}
		
		//初始化环形链表
		public void createLink()
		{
			//统计有多少个孩子
			for(int i=1;i<=len;i++)
			{
				if(i==1)
				{
					//首先创建第一个小孩
					Childhood ch=new Childhood(i);
					//将第一个孩子的应用交给firstChildhood
					this.firstChildhood=ch;
					//同时也要将引用交给跑龙套用
					this.temp=ch;
				}
				else
				{
					//判断是否是创建最后一个小孩
					if(i==len)
					{
						//继续创建小孩
						Childhood ch=new Childhood(i);
						//将temp的指针指向下一条地址的引用
						temp.nextChildhood=ch;//这条线相当于搭桥的线
						//桥答完后还要让temp指向刚刚创建的那个孩子
						temp=ch;
						//最后一个小孩的指针应指向头一个地址的引用
						temp.nextChildhood=this.firstChildhood;
					}
					else
					{
						//继续创建小孩
						Childhood ch=new Childhood(i);
						//将temp的指针指向下一条地址的引用
						temp.nextChildhood=ch;//这条线相当于搭桥的线
						//桥答完后还要让temp指向刚刚创建的那个孩子
						temp=ch;
					}
					
				}
			}
		}
		//打印该环形链表
		public void show()
		{
			//定义一个跑龙套
			Childhood temp=this.firstChildhood;
			do
			{
			System.out.print(temp.no+" ");
			temp=temp.nextChildhood;
			}while(temp!=this.firstChildhood);
		}
	}


