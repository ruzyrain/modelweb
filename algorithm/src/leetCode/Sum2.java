package leetCode;
import java.util.*;
/**
 * @question
 * Given an array of integers, find two numbers such that 
 * they add up to a specific target number.
 * The function twoSum should return indices of the two numbers such 
 * that they add up to the target, where index1 must be less than index2. Please
 * note that your returned answers (both index1 and index2) are not zero-based.
 * You may assume that each input would have exactly one solution.
 * Input: numbers={2, 7, 11, 15}, target=9
 * Output: index1=1, index2=2
 * 
 * @author Taoye
 *
 */
public class Sum2 {

    public int[] twoSum(int[] numbers, int target) {

    	HashMap<Integer, Integer> hash = new HashMap<Integer, Integer>();
        for (int i = 0; i < numbers.length; i++){
            if(hash.containsKey(target - numbers[i])){
                int re[] = {hash.get(target - numbers[i])+1 , i+1};
                return re;
            }
            hash.put(numbers[i], i);
        }
        return null;
    }
    
    public static void main(String[] args) {
		
	}
	

}
