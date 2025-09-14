import java.util.*;

public class QuickSort {


    public static int pivot(List<Integer> list, int start, int end) {
        // Random r = new Random();
        // System.out.println(start + " " + end);
        int pivot = start;
        // System.out.println(pivot);

        int actualPivotPos = start + 1;
        for (int i = start + 1; i < end; i++) {
            if (list.get(i) < list.get(pivot)) {
                int temp = list.get(i);
                list.set(i, list.get(actualPivotPos));
                list.set(actualPivotPos, temp);
                actualPivotPos++;
            }
        }

        int temp = list.get(pivot);
        list.set(pivot, list.get(actualPivotPos - 1));
        list.set(actualPivotPos - 1, temp);

        // System.out.println(pivotPos);
        return actualPivotPos - 1;

    }

    public static void quickSort(List<Integer> list, int start, int end) {

        if (start >= end) return;

        int pivot = pivot(list, start, end);
        quickSort(list, start, pivot);
        quickSort(list, pivot + 1, end);

    }



    public static void main(String[] args) {
        Random r = new Random();
        int len = r.nextInt(0, 100);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            list.add(r.nextInt(1000));
            System.out.println(list.get(i));
        }

        System.out.println("length is " + len);//

        List<Integer> copy = new ArrayList<>(list);
        Collections.copy(copy, list);
        Collections.sort(copy);

        quickSort(list, 0, len);

        int count = 0;
        for (int i = 0; i < len; i++) {
            System.out.println(i + " " + list.get(i) + " " + copy.get(i));
            if (list.get(i).equals(copy.get(i))) {
                count++;
            }
        }
        System.err.println(count == len);
    }
}