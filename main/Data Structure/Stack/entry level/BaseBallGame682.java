import java.util.*;

public class BaseBallGame682 {

    @SuppressWarnings("ConvertToStringSwitch")
    public int calPoints(String[] operations) {

        Deque<Integer> stack = new ArrayDeque<>();

        for (String str : operations) {

            if ("D".equals(str)) {
                int i = 2 * stack.peek();
                stack.push(i);
            } else if ("C".equals(str)) {
                stack.pop();
            } else if ("+".equals(str)) {
                int i = stack.pop();
                int j = stack.peek();
                stack.push(i);
                stack.push(i + j);
            } else {
                stack.push(Integer.valueOf(str));
            }
        }
        int res = 0;
        while (! stack.isEmpty()) {
            res += stack.pop();
        }
        return res;
    }


    public static void main(String[] args) {
        BaseBallGame682 b = new BaseBallGame682();

        // String[] str = "52CD+".split("");
        String[] s = new String[] {"5","-2","4","C","D","9","+","+"};
        // String[] s1 = new String[] {"1","C"};
        System.out.println(b.calPoints(s));

    }
}