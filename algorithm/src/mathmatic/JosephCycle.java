package mathmatic;
import java.io.*;
public class JosephCycle {
	/**
	 * ���ߣ�������
	 * ���ܣ�Լɪ������(����������)
	 * �������⣺����Ϊ1,2,3....n��n����Χ��һȦ��Լ�����Ϊk��1<=k<=n�����˴�1��ʼ
	 * ����������m���Ǹ��˳��У�������һλ��һ��ʼ����������m���Ǹ����ֳ��У��Դ����ƣ�
	 * ֱ�������˳���Ϊֹ����˲���һ�����б�ŵ�����...
	 * �������������
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
				System.out.println("������μӸ���ϷС���ĸ�����");
				String joinNmu=br.readLine();
				System.out.println("����������ӱ��Ϊ����С����ʼ������");
				String n1=br.readLine();
				System.out.println("������������������С�����У�");
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

	//������������
	class Childhood
	{
		//����Childhood�ı��
		int no;
		//�������ָ����һ����ָ��
		
		Childhood nextChildhood=null;
		//�������캯��
		public Childhood(int no)
		{
			//�����
			this.no=no;
		}
	}
	
	//����һ����������
	class CycLink
	{
		//�ȶ���һ��ָ�������һ��С��������
		//ָ���һ��С�������ã����ܶ�
		Childhood firstChildhood=null;
		//����һ���α꣬�൱��������
		Childhood temp=null;
		//����һ����ʾ��С�ĵ�len����ʾ���м���С��
		int len=0;
		int k=0;
		int m=0;
		//���캯�����������û�������Ĵ�С
		public void setLen(int len)
		{
			this.len=len;
		}
		//����m��ֵ
		public void setM(int m)
		{
			this.m=m;
		}
		//���ôӵڼ����˿�ʼ����
		public void setK(int k)
		{
			this.k=k;
		}
		//��Ϸʵ��
		public void play()
		{
			//���������׵�С��
			Childhood temp=this.firstChildhood;
			//1.�ҵ���ʼ��������
			for(int i=1;i<k;i++)
			{
				temp=temp.nextChildhood;
			}
			while(this.len!=1)
			{
				//2.��m��
				for(int j=1;j<m;j++)
				{
					temp=temp.nextChildhood;
				}
				//3.�ҵ�Ҫ��Ȧ��ǰһ��С��
				//����һ��2�������׵���
				Childhood temp2=temp;
				while(temp2.nextChildhood!=temp)
				{
					temp2=temp2.nextChildhood;
				}
				//4.�����˹�������m��С���˳�Ȧ
				temp2.nextChildhood=temp.nextChildhood;
				//��tempָ����һ��������С��
				temp=temp.nextChildhood;
				this.len--;
			}
			//������һ��С��
			System.out.println("����Ȧ��С���ǣ�"+temp.no+"��С����");
		}
		
		//��ʼ����������
		public void createLink()
		{
			//ͳ���ж��ٸ�����
			for(int i=1;i<=len;i++)
			{
				if(i==1)
				{
					//���ȴ�����һ��С��
					Childhood ch=new Childhood(i);
					//����һ�����ӵ�Ӧ�ý���firstChildhood
					this.firstChildhood=ch;
					//ͬʱҲҪ�����ý�����������
					this.temp=ch;
				}
				else
				{
					//�ж��Ƿ��Ǵ������һ��С��
					if(i==len)
					{
						//��������С��
						Childhood ch=new Childhood(i);
						//��temp��ָ��ָ����һ����ַ������
						temp.nextChildhood=ch;//�������൱�ڴ��ŵ���
						//�Ŵ����Ҫ��tempָ��ոմ������Ǹ�����
						temp=ch;
						//���һ��С����ָ��Ӧָ��ͷһ����ַ������
						temp.nextChildhood=this.firstChildhood;
					}
					else
					{
						//��������С��
						Childhood ch=new Childhood(i);
						//��temp��ָ��ָ����һ����ַ������
						temp.nextChildhood=ch;//�������൱�ڴ��ŵ���
						//�Ŵ����Ҫ��tempָ��ոմ������Ǹ�����
						temp=ch;
					}
					
				}
			}
		}
		//��ӡ�û�������
		public void show()
		{
			//����һ��������
			Childhood temp=this.firstChildhood;
			do
			{
			System.out.print(temp.no+" ");
			temp=temp.nextChildhood;
			}while(temp!=this.firstChildhood);
		}
	}


