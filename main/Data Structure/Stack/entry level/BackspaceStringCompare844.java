import java.util.*;
public class BackspaceStringCompare844 {
    
    public boolean backspaceCompare(String s, String t) {
        Deque<Character> stack1 = new ArrayDeque<>();
        Deque<Character> stack2 = new ArrayDeque<>();

        char[] arr1 = s.toCharArray();
        char[] arr2 = t.toCharArray();

        for (char c : arr1) {
            if ('#' == c) {
                if (! stack1.isEmpty()) stack1.pop();
            } else {
                stack1.push(c);
            }
        }

        for (char c : arr2) {
            if ('#' == c) {
                if (! stack2.isEmpty()) stack2.pop();
            } else {
                stack2.push(c);
            }
        }

        System.out.println(stack1.peek() + " " + stack2.peek());

        while (!stack1.isEmpty() && !stack2.isEmpty()) {
            char c1 = stack1.pop();
            char c2 = stack2.pop();

            if (c1 != c2) return false;
        }

        return stack1.isEmpty() && stack2.isEmpty();
    }

    public static void main(String[] args) {
        System.out.println(new BackspaceStringCompare844().backspaceCompare("y#fo##f", "y#f#o##f"));
    }
}