import java.util.*;
public class InsertionSort {

    public static void insertionSort(List<Integer> list) {
        if (list.size() <= 1) return;

        for (int i = 1; i < list.size(); i++) {
            int target = list.get(i);

            int j = i - 1;

            while (j >= 0) {
                // System.out.println(j + " " + list.get(j) + " " + target);
                if (list.get(j) > target) {
                    list.set(j + 1, list.get(j));
                    j--;
                } else {
                    break;
                }
            }
            list.set(j + 1, target);
        }

        // for (int i = 0; i < list.size(); i++) {
        //     System.out.println(list.get(i));
        // }
    }

    public static void main(String[] args) {
        Random r = new Random();
        int len = 10000;
        int max = r.nextInt(10000);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            list.add(r.nextInt(max));
            // System.out.println(list.get(i));
        }

        // System.out.println("=================");

        List<Integer> copy = new ArrayList<>(list);
        Collections.copy(copy, list);

        long startTime = System.currentTimeMillis();

        insertionSort(list);

        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);

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



    }
}