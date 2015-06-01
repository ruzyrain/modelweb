package leetCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * @question
 * Given an array S of n integers, are there elements a, b, c, and d in S such that a + b + c + d = target? Find all unique quadruplets in the array which gives the sum of target.
 * Note:
 * Elements in a quadruplet (a,b,c,d) must be in non-descending order. (ie, a ≤ b ≤ c ≤ d)
 * The solution set must not contain duplicate quadruplets.
 * For example, given array S = {1 0 -1 0 -2 2}, and target = 0.
 * 
 * A solution set is:
 * (-1,  0, 0, 1)
 * (-2, -1, 1, 2)
 * (-2,  0, 0, 2)
 * 
 * @author Taoye
 *
 */
public class Sum4 {

    public static List<List<Integer>> fourSum(int[] num, int target) {
        List<List<Integer>> reList = new ArrayList<List<Integer>>();
        int length = num.length;
        if (length < 4) return reList;//判断数组长度
        Arrays.sort(num);
        for (int i = 0; i < length - 3; i++){
        	if( i > 0 && num[i] == num[i-1]) continue;
            for (int j = i+1; j < length - 2; j++){
            	System.out.println("1111");
                if(j > i+1 &&(j < length-1 && num[j] == num[j-1])) continue;
                int k = j + 1;
                int l = length - 1;
                while(k < length -1 && k < l){
                    if (num[i] + num[j] + num[k] + num[l] == target){
                    	List<Integer> addList = new ArrayList<Integer>();
                        addList.add(num[i]);
                        addList.add(num[j]);
                        addList.add(num[k]);
                        addList.add(num[l]);
                        if (addList.size() > 0) reList.add(addList);
                        while (num[k] == num[k+1] && l > k+1) k++;
                        while (num[l] == num[l-1] && l > k+1) l--;
                        k++;
                        l--;
                    }
                    else if(num[i] + num[j] + num[k] + num[l] > target) l--;
                    else if(num[i] + num[j] + num[k] + num[l] < target) k++;
                }
                
            }
        }
        return reList;	        
    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] num = {0,1,1,2,2,2};
		System.out.println(Sum4.fourSum(num, 7));
	}

}
