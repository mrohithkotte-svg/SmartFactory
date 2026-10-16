package smartfactory.co3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SubsetDP {
    public static class RouteResult {
        public int minCost;
        public List<Integer> route;

        public RouteResult(int minCost, List<Integer> route) {
            this.minCost = minCost;
            this.route = route;
        }
    }

    public static RouteResult findOptimalRoute(int[][] distanceMatrix) {
        int n = distanceMatrix.length;
        int numStates = 1 << n;
        int inf = 1_000_000_000;

        int[][] dp = new int[numStates][n];
        int[][] parent = new int[numStates][n];
        for (int[] row : dp) Arrays.fill(row, inf);
        for (int[] row : parent) Arrays.fill(row, -1);

        dp[1][0] = 0; // Start at machine 0

        for (int mask = 1; mask < numStates; mask++) {
            for (int u = 0; u < n; u++) {
                if (dp[mask][u] < inf) {
                    for (int v = 0; v < n; v++) {
                        if ((mask & (1 << v)) == 0) {
                            int nextMask = mask | (1 << v);
                            int newDist = dp[mask][u] + distanceMatrix[u][v];
                            if (newDist < dp[nextMask][v]) {
                                dp[nextMask][v] = newDist;
                                parent[nextMask][v] = u;
                            }
                        }
                    }
                }
            }
        }

        int bestCost = inf;
        int lastNode = -1;
        for (int u = 0; u < n; u++) {
            int total = dp[numStates - 1][u] + distanceMatrix[u][0];
            if (total < bestCost) {
                bestCost = total;
                lastNode = u;
            }
        }

        List<Integer> path = new ArrayList<>();
        int mask = numStates - 1, curr = lastNode;
        while (curr != -1) {
            path.add(curr);
            int p = parent[mask][curr];
            mask ^= (1 << curr);
            curr = p;
        }
        Collections.reverse(path);
        path.add(0);

        return new RouteResult(bestCost, path);
    }
}
