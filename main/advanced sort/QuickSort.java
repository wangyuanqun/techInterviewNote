import java.util.*;

public class QuickSort {

    /**
     * choose the start as pivot, worst case is On(n^2) for an ascending order list,
     */
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

    /**
     * choose a random as pivot to reduce the worst time complexity to O(nlogn)
     */
    public static int pivotRandom(List<Integer> list, int start, int end) {
        Random r = new Random();
        // System.out.println(start + " " + end + "==========");//
        int pivot = r.nextInt(start, end);
        // System.out.println("pivot: " + pivot);
        // for (int i = 0; i < list.size(); i++) System.out.print(list.get(i) + " ");
        // System.out.println("before");

        int actualPos = start;
        for (int i = start; i < end; i++) {
            // System.out.println("    " + i);
            if (list.get(i) < list.get(pivot)) {
                // System.out.println("exchange " + i + " " + actualPos);
                int temp = list.get(i);
                list.set(i, list.get(actualPos));
                list.set(actualPos, temp);
                if (actualPos == pivot) pivot = i;
                actualPos++;
            }
        }

        // System.out.println("actualPos: " + actualPos);

        // actualPos = actualPos - 1 >= 0 ? actualPos : 0;

        int temp = list.get(pivot);
        list.set(pivot, list.get(actualPos));
        list.set(actualPos, temp);
        // for (int i = 0; i < list.size(); i++) System.out.print(list.get(i) + " ");
        // System.out.println("after");
        // System.out.println("exchange " + pivot + " " + actualPos);
        

        return actualPos;

    }

    public static void quickSort(List<Integer> list, int start, int end) {

        if (start >= end) return;

        int pivot = pivotRandom(list, start, end);
        // int pivot = pivot(list, start, end);
        quickSort(list, start, pivot);
        quickSort(list, pivot + 1, end);

    }



    public static void main(String[] args) {
        for (int test = 0; test < 1000; test++) {
            Random r = new Random();
            int len = r.nextInt(0, 1000);
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < len; i++) {
                list.add(r.nextInt(1000));
            }

            // System.out.println("length is " + len);//

            List<Integer> copy = new ArrayList<>(list);
            Collections.copy(copy, list);
            Collections.sort(copy);

            quickSort(list, 0, len);

            int count = 0;
            for (int i = 0; i < len; i++) {
                // System.out.println(i + " " + list.get(i) + " " + copy.get(i));
                if (list.get(i).equals(copy.get(i))) {
                    count++;
                }
            }
            if (count != len) System.err.println(count == len);
        }
        System.out.println("hello world");
        
    }

    // public static void quickSort1(List<Integer> list, int start, int end) {

    //     if (start >= end) return;

    //     int pivot = quickSortRandom(list, start, end);
    //     quickSort1(list, start, pivot);
    //     quickSort1(list, pivot + 1, end);
    // }

    // private static int quickSortRandom(List<Integer> list, int start, int end) {
        
    //     Random r = new Random();
    //     int pivot = r.nextInt(start, end);

    //     int small = start;
    //     for (int i = start; i < end; i++) {
    //         if (list.get(i) < list.get(pivot)) {

    //             int temp = list.get(i);
    //             list.set(i, list.get(small));
    //             list.set(small, temp);
    //             if (small == pivot) pivot = i;
    //             small++;
    //         }
    //     }

    //     int temp = list.get(pivot);
    //     list.set(pivot, list.get(small));
    //     list.set(small, temp);


    //     return small;
    // }
}