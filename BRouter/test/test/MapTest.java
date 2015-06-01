package test;

import java.util.HashMap;
import java.util.Map;

public class MapTest {
	public static void main(String[] args){
		Map<Integer, String> map = new HashMap<Integer, String>();
		
		//System.out.println("get()方法的返回结果：");
		String aString=map.get(220180);
		map.put(220180, "aa"+aString);
		//System.out.print("------ " + map.get(220180));
		/*int[][]a=new int[3][2];
		for(int index=0;index<3;index++){
			a[index][0]=index;
		}
		a[0][1]=1;
		a[1][1]=2;
		a[2][1]=0;
		//int[][]a={{0,3},{1,4},{2,1},{3,2}};
		bubbleSort(a);*/
		int[]b={2,0,1};
		String[]ccStrings={"cc","aa","bb"};
		bubbleSort1(b,ccStrings);
		for(int i=0;i<ccStrings.length;i++){
			System.out.println(ccStrings[i]+"---"+b[i]);
		}
		
	}
	public static void bubbleSort1(int[] seq,String[] itemStrings){
		int temp;
		String temp1;
        for (int i = 0; i < seq.length - 1; i++) {
      	  for (int j = 0; j < seq.length - i - 1; j++) {
      		  if (seq[j] > seq[j + 1]) {
      			  temp1 = itemStrings[j + 1];
      			  itemStrings[j + 1] = itemStrings[j];
      			  itemStrings[j] = temp1;
      			  temp = seq[j + 1];
      			  seq[j + 1] = seq[j];
      			  seq[j] = temp;
      		  }
      	  }
        }
	}
	public static void bubbleSort(int[][] seq){
		  int temp;
		  int temp1;
          for (int i = 0; i < seq.length - 1; i++) {
        	  for (int j = 0; j < seq.length - i - 1; j++) {
        		  if (seq[j][1] > seq[j + 1][1]) {
        			  temp1 = seq[j + 1][0];
        			  seq[j + 1][0] = seq[j][0];
        			  seq[j][0] = temp1;
        			  temp = seq[j + 1][1];
        			  seq[j + 1][1] = seq[j][1];
        			  seq[j][1] = temp;
        		  }
        	  }
          }
	}
}
