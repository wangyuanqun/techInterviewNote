
import java.util.*;



public class LongestValidParentheses32 {


    public static int longestValidParentheses(String s) {

        Deque<Integer> stack = new ArrayDeque<>();
        char[] c = s.toCharArray();
        int max = 0;
        int mostRecentExtraCloseIndex = -1;
        for (int i = 0; i < c.length; i++) {
            if (c[i] == '(') {
                stack.push(i);
            } else {
                if (! stack.isEmpty()) {
                    stack.pop();
                } else {
                    max = Math.max(max, i - mostRecentExtraCloseIndex - 1);
                    mostRecentExtraCloseIndex = i;
                }
            }
        }

        if (stack.isEmpty()) {
            return Math.max(max, c.length - mostRecentExtraCloseIndex - 1);
        } else {
            int end = c.length;
            while (! stack.isEmpty()) {
                int extraOpenIndex = stack.pop();
                max = Math.max(max, end - extraOpenIndex - 1);
                end = extraOpenIndex;
            }

            return Math.max(max, end - mostRecentExtraCloseIndex - 1);
        }
    }

    public static void main(String[] args) {
        System.out.println(longestValidParentheses("((()")); // )())()()(() 4, )()(()()(()4, )())()())()4, )()(()())()10,
    }
}