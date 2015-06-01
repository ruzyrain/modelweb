package mathmatic;

public class Math {
	public static long j = 1;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int k = 0;
		for(int i = 1; i < 32; i++ ){
			j = j*2 ;
			k = i;
		}
		
		System.out.println("2的"+k+"次方是:"+j);
		int i = 2147483647;
	}

}
