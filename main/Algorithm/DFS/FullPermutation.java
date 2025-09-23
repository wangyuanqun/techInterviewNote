import java.util.*;

public class FullPermutation {
    public void recursion(int cur, int n, List<Integer> list, List<List<Integer>> ans, boolean[] visited) {
        if (cur == n + 1) {
            // for (int j = 0; j < list.size(); j++) {
            //     System.out.print(list.get(j));
            // }
            // System.out.println();
            List<Integer> l = new ArrayList<>(list);
            // Collections.copy(l,list);
            ans.add(l);
            return;
        }

        for (int i = 1; i <= n; i++) {
            if (visited[i]) continue;
            visited[i] = true;
            list.add(i);
            recursion(cur + 1, n, list, ans, visited);
            visited[i] = false;
            list.remove(list.size() - 1);       
        }
    }


    public List<List<Integer>> fullPermutation(int n) {
        List<List<Integer>> ans = new ArrayList<>();

        recursion(1, n, new ArrayList<>(), ans, new boolean[n + 1]);


        return ans;
    }

    public static void main(String[] args) {
        FullPermutation f = new FullPermutation();
        List<List<Integer>> list = f.fullPermutation(4);

        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).size(); j++) {
                System.out.print(list.get(i).get(j));
            }
            System.err.println("");
        }
    }
}