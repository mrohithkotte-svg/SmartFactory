package smartfactory.co3;

public class MatrixChainMultiplication {
    public static class Result {
        public long minMultiplications;
        public String optimalOrder;

        public Result(long minMultiplications, String optimalOrder) {
            this.minMultiplications = minMultiplications;
            this.optimalOrder = optimalOrder;
        }
    }

    public static Result compute(int[] p) {
        int n = p.length - 1;
        long[][] dp = new long[n][n];
        int[][] split = new int[n][n];

        for (int len = 2; len <= n; len++) {
            for (int i = 0; i + len <= n; i++) {
                int j = i + len - 1;
                dp[i][j] = Long.MAX_VALUE;
                for (int k = i; k < j; k++) {
                    long q = dp[i][k] + dp[k + 1][j] + (long) p[i] * p[k + 1] * p[j + 1];
                    if (q < dp[i][j]) {
                        dp[i][j] = q;
                        split[i][j] = k;
                    }
                }
            }
        }
        String order = buildOrder(split, 0, n - 1);
        return new Result(dp[0][n - 1], order);
    }

    private static String buildOrder(int[][] s, int i, int j) {
        if (i == j) return "A" + (i + 1);
        int k = s[i][j];
        return "(" + buildOrder(s, i, k) + " x " + buildOrder(s, k + 1, j) + ")";
    }
}
