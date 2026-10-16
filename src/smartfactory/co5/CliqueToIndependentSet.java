package smartfactory.co5;

public class CliqueToIndependentSet {
    public static boolean[][] buildComplement(boolean[][] g) {
        int n = g.length;
        boolean[][] gComp = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j && !g[i][j]) {
                    gComp[i][j] = true;
                }
            }
        }
        return gComp;
    }
}
