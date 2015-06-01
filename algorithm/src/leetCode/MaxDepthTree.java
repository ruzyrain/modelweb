package leetCode;
/**
 * @question
 * Given a binary tree, find its maximum depth.
 * The maximum depth is the number of nodes along the longest
 * path from the root node down to the farthest leaf node.
 * 
 * @author Taoye
 *
 */
public class MaxDepthTree {

	class TreeNode {
	    int val;
	    TreeNode left;
		TreeNode right;
		TreeNode(int x) { val = x; 
		}
	}
	
    public int maxDepth(TreeNode root) {
        return root==null? 0 : Math.max(maxDepth(root.left), maxDepth(root.right))+1;
    }
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
