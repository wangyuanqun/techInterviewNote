import java.util.*;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    // ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

public class ReverseLinkedList206 {
    ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) return head;

        Deque<ListNode> s = new ArrayDeque<>();
        ListNode temp = head;

        while (temp != null) {
            // System.out.println("add temp " + temp.val);
            s.push(temp);
            temp = temp.next;
        }

        ListNode newHead = new ListNode();
        ListNode temphead = newHead;

        while (! s.isEmpty()) {
            ListNode node = s.pop();
            // System.out.println("pop " + node.val);
            node.next = null;
            temphead.next = node;
            temphead = temphead.next;
        }
        
        
        return newHead.next;
    }


    public static void main(String[] args) {
        ListNode head = new ListNode(-1);
        Random r = new Random();
        ListNode temp = head;
        for (int i = 0; i < 10; i++) {
            temp.next = new ListNode(r.nextInt(10));
            temp = temp.next;
        }

        temp = head;
        while (temp != null) {
            System.out.println(temp.val);
            temp = temp.next;
        }

        System.out.println("===");

        ReverseLinkedList206 rClass = new ReverseLinkedList206();
        ListNode reverse = rClass.reverseList(head);

        while (reverse != null) {
            System.out.println(reverse.val);
            reverse = reverse.next;
        }
    }
}