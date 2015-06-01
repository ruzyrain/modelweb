package leetCode;

import java.util.Arrays;
/**
 * @question
 * Given an array of integers, every element appears twice except for one. 
 * Find that single one.
 * Note:Your algorithm should have a linear runtime complexity. 
 * Could you implement it without using extra memory?
 * 
 * @author Taoye
 *
 */
public class SingleNum {

	
    public int singleNumber(int[] A) {
        int k = 0;
        for (int i = 0; i < A.length; i++){
            k ^= A[i];
        }
        return k;
    }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
