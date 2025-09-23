import java.util.*;
public class PrintListFromTailToHead {

    public void printListFromTailToHead(List<Integer> list) {
        
        if (list.size() < 1) return;

        Deque<Integer> s = new ArrayDeque<>();
        for (int i = 0; i < list.size(); i++) {
            s.push(list.get(i));
        }

        List<Integer> print = new ArrayList<>();
        while (! s.isEmpty()) {
            int num = s.pop();
            print.add(num);
        }

        
        for (int i = 0; i < print.size(); i++) {
            System.out.println(print.get(i) + " ");
        }
    }


    public static void main(String[] args) {
        Random r = new Random();
        int len = r.nextInt(20);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            list.add(r.nextInt(100));
            System.out.print(list.get(i) + " ");
        }

        System.out.println();

        PrintListFromTailToHead p = new PrintListFromTailToHead();
        p.printListFromTailToHead(list);
    }
}