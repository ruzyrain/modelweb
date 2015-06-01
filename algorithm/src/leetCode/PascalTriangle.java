package leetCode;

import java.util.*;
/**
 * @question
 * Given numRows, generate the first numRows of Pascal's triangle.
 * For example, given numRows = 5,
 * Return
 * 	[
 *	     [1],
 *	    [1,1],
 *	   [1,2,1],
 *	  [1,3,3,1],
 *	 [1,4,6,4,1]
 *	]
 *
 * @author Taoye
 *
 */
public class PascalTriangle {
   public List<List<Integer>> generate(int numRows) {
        
	   List<List<Integer>> reList = new ArrayList<List<Integer>>();
        if (numRows <= 0) return reList;
        else {
            for (int i = 0; i < numRows; i ++){
                List<Integer> rowList =  new ArrayList<Integer>();
                for (int j = 0; j <= i; j++){
                    if (j == 0 || j == i) rowList.add(1);
                    else{
                        rowList.add(reList.get(i-1).get(j-1)+reList.get(i-1).get(j));
                    }
                }
                reList.add(rowList);
            }
        }
        return reList;
   }
   
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
