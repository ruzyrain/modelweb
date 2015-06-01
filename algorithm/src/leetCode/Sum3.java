package leetCode;
import java.util.*;
/**
 * @question
 * Given an array S of n integers, are there elements a, b, c in S such 
 * that a + b + c = 0? Find all unique triplets in the array which gives 
 * the sum of zero.
 * Note:
 * Elements in a triplet (a,b,c) must be in non-descending order. (ie, a ¡Ü b ¡Ü c)
 * The solution set must not contain duplicate triplets.
 * For example, given array S = {-1 0 1 2 -1 -4},
 * A solution set is:
 * (-1, 0, 1)
 * (-1, -1, 2)
 * 
 * @author Taoye
 *
 */
public class Sum3 {
	public List<List<Integer>> threeSum(int[] num) {
	    List<List<Integer>> reList = new ArrayList<List<Integer>>();
	    //judge the size
	    if (num == null || num.length == 0){
	        return reList;
	    }
	    
	    
	    Arrays.sort(num);
	    for (int i = 0; i < num.length-2; i++) {
	        if (i == 0 || (i > 0 && num[i] != num[i-1])) {
	            int lo = i+1, hi = num.length-1, sum = 0 - num[i];
	            while (lo < hi) {
	                if (num[lo] + num[hi] == sum) {
	                    reList.add(Arrays.asList(num[i], num[lo], num[hi]));
	                    while (lo < hi && num[lo] == num[lo+1]) lo++;
	                    while (lo < hi && num[hi] == num[hi-1]) hi--;
	                    lo++; hi--;
	                } else if (num[lo] + num[hi] < sum) lo++;
	                else hi--;
	        }
	        }
	    }
	    /**
	    for(int i = 0; i < num.length - 2;i++){
	        for(int j = i + 1; j < num.length - 1; j++){
	            for(int k = j + 1; k < num.length; k++){
	                if(num[i] + num[j] + num[k] == 0) {
	                    List<Integer> addList = new ArrayList<Integer>();
	                    addList.add(num[i]);
	                    addList.add(num[j]);
	                    addList.add(num[k]);
	                    reList.add(addList);
	                }
	            }
	        }
	    }
	    */
	    return reList;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
