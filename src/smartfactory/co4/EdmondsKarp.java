package smartfactory.co4;

import java.util.*;

public class EdmondsKarp {
    public static class FlowResult {
        public int maxFlow;
        public List<String> pathLogs;
        public int[][] finalResidual;

        public FlowResult(int maxFlow, List<String> pathLogs, int[][] finalResidual) {
            this.maxFlow = maxFlow;
            this.pathLogs = pathLogs;
            this.finalResidual = finalResidual;
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
            int[] parent = new int[n];
            Arrays.fill(parent, -1);
            Queue<Integer> q = new LinkedList<>();
            q.add(source);
            parent[source] = source;

            while (!q.isEmpty() && parent[sink] == -1) {
                int u = q.poll();
                for (int v = 0; v < n; v++) {
                    if (parent[v] == -1 && res[u][v] > 0) {
                        parent[v] = u;
                        q.add(v);
                    }
                }
            }

            if (parent[sink] == -1) break;

            List<Integer> path = new ArrayList<>();
            int curr = sink;
            while (curr != source) {
                path.add(curr);
                curr = parent[curr];
            }
            path.add(source);
            Collections.reverse(path);

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

        return new FlowResult(maxFlow, logs, res);
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
