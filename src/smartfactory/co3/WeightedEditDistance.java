package smartfactory.co3;

public class WeightedEditDistance {
    public static int calculate(String a, String b, int ins, int del, int rep) {
        int n = a.length(), m = b.length();
        int[][] d = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++) d[i][0] = i * del;
        for (int j = 1; j <= m; j++) d[0][j] = j * ins;
        for (int i = 1; i <= n; i++)
            for (int j = 1; j <= m; j++) {
                int r = a.charAt(i - 1) == b.charAt(j - 1) ? 0 : rep;
                d[i][j] = Math.min(d[i - 1][j] + del, Math.min(d[i][j - 1] + ins, d[i - 1][j - 1] + r));
            }
        return d[n][m];
    }
}
