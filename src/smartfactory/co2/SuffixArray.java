package smartfactory.co2;

import java.util.Arrays;

public class SuffixArray {
    public static int[] buildDoubling(String s) {
        int n = s.length();
        Integer[] a = new Integer[n];
        int[] rank = new int[n];
        for (int i = 0; i < n; i++) { a[i] = i; rank[i] = s.charAt(i); }
        for (int k = 1; k < n; k *= 2) {
            final int kk = k; final int[] rr = rank;
            Arrays.sort(a, (x, y) -> {
                if (rr[x] != rr[y]) return Integer.compare(rr[x], rr[y]);
                return Integer.compare(x + kk < n ? rr[x + kk] : -1, y + kk < n ? rr[y + kk] : -1);
            });
            int[] nr = new int[n];
            for (int i = 1; i < n; i++) {
                int x = a[i - 1], y = a[i];
                int x2 = x + k < n ? rank[x + k] : -1, y2 = y + k < n ? rank[y + k] : -1;
                nr[y] = nr[x] + ((rank[x] != rank[y] || x2 != y2) ? 1 : 0);
            }
            nr[a[0]] = 0; rank = nr;
            if (rank[a[n - 1]] == n - 1) break;
        }
        int[] out = new int[n];
        for (int i = 0; i < n; i++) out[i] = a[i];
        return out;
    }
}
