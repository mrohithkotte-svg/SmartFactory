package smartfactory.co3;

public class EditDistance {
    public static int levenshtein(String a, String b) {
        int[] dp = new int[b.length() + 1];
        for (int j = 0; j <= b.length(); j++) dp[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            int prev = dp[0]; dp[0] = i;
            for (int j = 1; j <= b.length(); j++) {
                int old = dp[j];
                dp[j] = a.charAt(i - 1) == b.charAt(j - 1) ? prev : 1 + Math.min(prev, Math.min(dp[j], dp[j - 1]));
                prev = old;
            }
        }
        return dp[b.length()];
    }

    public static int damerauLevenshtein(String a, String b) {
        int n = a.length(), m = b.length();
        int[][] d = new int[n + 1][m + 1];
        for (int i = 0; i <= n; i++) d[i][0] = i;
        for (int j = 0; j <= m; j++) d[0][j] = j;
        for (int i = 1; i <= n; i++)
            for (int j = 1; j <= m; j++) {
                int c = a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1;
                d[i][j] = Math.min(d[i - 1][j - 1] + c, Math.min(d[i - 1][j] + 1, d[i][j - 1] + 1));
                if (i > 1 && j > 1 && a.charAt(i - 1) == b.charAt(j - 2) && a.charAt(i - 2) == b.charAt(j - 1))
                    d[i][j] = Math.min(d[i][j], d[i - 2][j - 2] + 1);
            }
        return d[n][m];
    }
}
