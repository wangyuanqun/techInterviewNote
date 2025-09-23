import java.util.*;

public class BubbleSort {

    public static void bubbleSort(List<Integer> list) {
        if (list.size() <= 1) return;

        for (int i = list.size() - 1; i > 0; i--) {

            for (int j = 0; j < i; j++) {

                if (list.get(j) > list.get(j + 1)) {
                    int cur = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, cur);
                    //bitwise operator
                    // list.set(j, list.get(j) ^ list.get(j + 1));
                    // list.set(j + 1, list.get(j) ^ list.get(j + 1));
                    // list.set(j, list.get(j) ^ list.get(j + 1));
                }
            }
        }

    }



    public static void main(String[] args) {
        Random r = new Random();
        int len = 10000;
        int max = r.nextInt(10000);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            list.add(r.nextInt(max));
        }

        List<Integer> copy = new ArrayList<>(list);
        Collections.copy(copy, list);

        long startTime = System.currentTimeMillis();

        bubbleSort(list);

        Collections.sort(copy);

        int count = 0;
        for (int i = 0; i < len; i++) {
            if (list.get(i).equals(copy.get(i))){
                count++;
                System.out.println(i + " " + list.get(i) + " " + copy.get(i));
            }
            // System.out.println(list.get(i));

        }
        System.out.println(count);

        long endTime = System.currentTimeMillis();

        System.err.println(endTime - startTime);

    }
}