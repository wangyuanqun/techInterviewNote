import java.util.*;

public class MergeSort {



    public static void mergeSort(List<Integer> list, int start, int end) {
        // since end is len - 1, so could be -1.
        if (start >= end) {
            // System.out.println(start);//
            return;
        }

        // System.out.println("s e: " + start + " " + end);//

        int mid = start + (end - start) / 2;
        mergeSort(list, start, mid);
        mergeSort(list, mid + 1, end);
        merge(list, start, mid, end);
    }

    public static void merge(List<Integer> list, int start, int mid, int end) {
        // System.out.println("s m e: " + start + " " + mid + " " + end);//
        List<Integer> temp = new ArrayList<>();

        int left = start;
        int right = mid + 1;

        while (left <= mid && right <= end) {
            if (list.get(left) < list.get(right)) {
                temp.add(list.get(left));
                left++;
            } else {
                temp.add(list.get(right));
                right++;
            }
        }

        if (left > mid && right <= end) {
            for (int i = right; i <= end; i++) temp.add(list.get(i));
        }

        if (right > end && left <= mid) {
            for (int i = left; i <= mid; i++) temp.add(list.get(i));
        }

        for (int i = start; i <= end; i++) {
            list.set(i, temp.get(i - start)); // for temp, starts from 0
        }

    }


    public static void main(String[] args) {
        Random r = new Random();
        int len = r.nextInt(100);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            list.add(r.nextInt(1000));
        }

        // System.out.println("length is " + len);//

        List<Integer> copy = new ArrayList<>(list);
        Collections.copy(copy, list);
        Collections.sort(copy);

        mergeSort(list, 0, len - 1);

        int count = 0;
        for (int i = 0; i < len; i++) {
            if (list.get(i).equals(copy.get(i))) {
                count++;
                System.out.println(i + " " + list.get(i) + " " + copy.get(i));
            }
        }
        System.err.println(count == len);
    }
}