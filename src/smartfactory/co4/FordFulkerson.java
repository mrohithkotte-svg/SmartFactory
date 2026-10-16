package smartfactory.co4;

import java.util.ArrayList;
import java.util.List;

public class FordFulkerson {
    public static class FlowResult {
        public int maxFlow;
        public List<String> pathLogs;

        public FlowResult(int maxFlow, List<String> pathLogs) {
            this.maxFlow = maxFlow;
            this.pathLogs = pathLogs;
        }
    }

    public static FlowResult compute(int[][] cap, int source, int sink) {
        int n = cap.length;
        int[][] res = new int[n][n];
        for (int i = 0; i < n; i++) res[i] = cap[i].clone();

        int maxFlow = 0;
        int pathCount = 0;
        List<String> logs = new ArrayList<>();

        while (true) {
            boolean[] visited = new boolean[n];
            List<Integer> path = new ArrayList<>();
            boolean found = dfsAugment(res, source, sink, visited, path);
            if (!found) break;

            int bottleneck = Integer.MAX_VALUE;
            for (int i = 0; i < path.size() - 1; i++) {
                int u = path.get(i), v = path.get(i + 1);
                bottleneck = Math.min(bottleneck, res[u][v]);
            }

            for (int i = 0; i < path.size() - 1; i++) {
                int u = path.get(i), v = path.get(i + 1);
                res[u][v] -= bottleneck;
                res[v][u] += bottleneck;
            }

            pathCount++;
            maxFlow += bottleneck;
            logs.add(String.format("Path %d: %-22s | Bottleneck: %2d units/hr | Cum. Flow: %2d",
                pathCount, formatPath(path), bottleneck, maxFlow));
        }

        return new FlowResult(maxFlow, logs);
    }

    private static boolean dfsAugment(int[][] res, int u, int t, boolean[] visited, List<Integer> path) {
        visited[u] = true;
        path.add(u);
        if (u == t) return true;
        for (int v = 0; v < res.length; v++) {
            if (!visited[v] && res[u][v] > 0) {
                if (dfsAugment(res, v, t, visited, path)) return true;
            }
        }
        path.remove(path.size() - 1);
        return false;
    }

    private static String formatPath(List<Integer> path) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < path.size(); i++) {
            if (i > 0) sb.append("->");
            sb.append(path.get(i));
        }
        return sb.toString();
    }
}
