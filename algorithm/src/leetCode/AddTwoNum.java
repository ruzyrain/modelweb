package leetCode;

/**
 * @question
 * You are given two linked lists representing two non-negative numbers. 
 * The digits are stored in reverse order and each of their nodes contain a single digit. 
 * Add the two numbers and return it as a linked list.
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8
 * 
 * @author Taoye
 *
 */
public class AddTwoNum {
	class ListNode{
		ListNode next;
		int val;
		ListNode(int x){
			val = x;
			next = null;
		}
	}
	
	
	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode c1 = l1;
        ListNode c2 = l2;
        ListNode sentinel = new ListNode(0);
        ListNode d = sentinel;
        int sum = 0;
        while (c1 != null || c2 != null) {
            sum /= 10;
            if (c1 != null) {
                sum += c1.val;
                c1 = c1.next;
            }
            if (c2 != null) {
                sum += c2.val;
                c2 = c2.next;
            }
            d.next = new ListNode(sum % 10);
            d = d.next;
        }
        if (sum / 10 == 1)
            d.next = new ListNode(1);
        return sentinel.next;
    }

	public static void main(String[] args) {
		AddTwoNum addList = new AddTwoNum();
		ListNode l1 = addList.new ListNode(1);
		ListNode l2 = addList.new ListNode(2);
		ListNode l3 = addList.new ListNode(3);
		ListNode l4 = addList.new ListNode(4);
		ListNode l5 = addList.new ListNode(5);
		ListNode l6 = addList.new ListNode(6);
		ListNode l7 = addList.new ListNode(7);
		ListNode l8 = addList.new ListNode(8);
		l1.next = l2;
		l2.next = l3;
		l3.next = l4;
		l4.next = l5;
		l6.next = l7;
		l7.next = l8;
		ListNode ma =  addList.addTwoNumbers(l1, l6);
		while (ma !=null){
			System.out.println("->" + ma.val);
			ma = ma.next;
		}
		
	}

}
