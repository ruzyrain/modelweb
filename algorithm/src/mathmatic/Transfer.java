package mathmatic;


public class Transfer {

	/**
	 * @param args
	 */
	public static void swapFun(Fun fun1, Fun fun2){
		System.out.println(fun1.hashCode());
		System.out.println(fun2.hashCode());
		Fun temp = fun1;
		fun1 = fun2;
		fun2 = temp;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Fun fun1 = new Fun(1);
		Fun fun2 = new Fun(2);
 		System.out.println(fun1.i);
		System.out.println(fun1.hashCode());
		System.out.println(fun2.i);
		System.out.println(fun2.hashCode());
		Transfer.swapFun(fun1, fun2);
		System.out.println(fun1.i);
		System.out.println(fun1.hashCode());
		System.out.println(fun2.i);
		System.out.println(fun2.hashCode());
	}

}
