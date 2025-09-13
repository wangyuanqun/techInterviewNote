import java.util.*;


public class SelectionSort {

    public static void selectionSort(List<Integer> list) {
        if (list.size() <= 1) return;

        for (int i = 0; i < list.size(); i++) {
            
            int min = i;

            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(j) < list.get(min)) {
                    min = j;
                }
            }

            int cur = list.get(min);
            list.set(min, list.get(i));
            list.set(i, cur);
            
            //bitwise operation
            // int a = list.get(i);
            // int b = list.get(min);

            // a ^= b;
            // b ^= a;
            // a ^= b;

            // list.set(i, a);
            // list.set(min, b);
        }

        for (int i = 0; i < list.size(); i++) {
            System.err.println(list.get(i));
        }
    }


    public static void main(String[] args) {
        Random r = new Random();
        int len = 10000;
        int max = r.nextInt(1000);

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            list.add(r.nextInt(max));
        }
        
        List<Integer> cpy = new ArrayList<>(list);
        Collections.copy(cpy, list);

        long startTime = System.currentTimeMillis();

        selectionSort(list);

        long endTime = System.currentTimeMillis();

        System.out.println(endTime - startTime);

        // Collections.sort(cpy);

        int count = 0;
        for (int i = 0; i < len; i++) {
            if (list.get(i).equals(cpy.get(i))) {
                count++;
                System.out.println(i + " " + list.get(i) + " " + cpy.get(i));
            }
        }

        System.out.println(count);
    }
}