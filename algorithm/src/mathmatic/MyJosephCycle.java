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
			System.out.println("������μӸ���ϷС���ĸ�����");
			String joinNmu=br.readLine();
			System.out.println("����������ӱ��Ϊ����С����ʼ������");
			String n1=br.readLine();
			System.out.println("������������������С�����У�");
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

//����ڵ�
	class Child{
		int num;//����ڵ���
		Child nextChild = null;
		public Child(int no){
			this.num = no;
		}
	}
	
	//���廷������ṹ
	class CycleLink{
		
		Child firstChild = null;//ͷ���
		Child temp = null;//��ʱָ��
		Child temp2 = null;
		int len = 0;//������
		int k = 0;//�ڼ����˿�ʼ����
		int m = 0;//ÿ�γ���
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
		
		//��ʼ��ѭ������
		public void createLink() {
			for (int i = 1; i<=len; i++){
				if (i == 1) {//ͷ���
					Child fir = new Child(i);
					firstChild = fir;
					temp = fir;
				}
				else if (i == len){//β�ڵ�
					Child las = new Child(i);
					temp.nextChild = las;
					temp = las;
					temp.nextChild = firstChild;
				}
				else {//���������ڵ�
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
				for (int i = 1; i<m-1; i++) {//��M��
					temp = temp.nextChild;
				}
				temp2 = temp;
				temp = temp.nextChild;
				temp2.nextChild = temp.nextChild;
				temp = temp.nextChild;
				this.len--;
			}
		
			System.out.println("���ս����"+temp.num);
		}		
	}

