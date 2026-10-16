package smartfactory.co4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Dinic {
    public static class FlowResult {
        public int maxFlow;
        public List<String> phaseLogs;

        public FlowResult(int maxFlow, List<String> phaseLogs) {
            this.maxFlow = maxFlow;
            this.phaseLogs = phaseLogs;
        }
    }

    public static FlowResult compute(int[][] cap, int source, int sink) {
        int n = cap.length;
        int[][] res = new int[n][n];
        for (int i = 0; i < n; i++) res[i] = cap[i].clone();

        int maxFlow = 0;
        int phase = 0;
        List<String> phaseLogs = new ArrayList<>();

        int[] level = new int[n];
        int[] ptr = new int[n];

        while (dinicBfs(res, source, sink, level)) {
            phase++;
            Arrays.fill(ptr, 0);
            phaseLogs.add(String.format("Phase %d: Level Graph constructed via BFS -> Levels: %s",
                phase, Arrays.toString(level)));

            int phaseFlow = 0;
            while (true) {
                int pushed = dinicDfs(res, source, sink, Integer.MAX_VALUE, level, ptr);
                if (pushed == 0) break;
                phaseFlow += pushed;
            }
            maxFlow += phaseFlow;
            phaseLogs.add(String.format("         Blocking Flow pushed in Phase %d = %d units/hr | Total = %d",
                phase, phaseFlow, maxFlow));
        }

        return new FlowResult(maxFlow, phaseLogs);
    }

    private static boolean dinicBfs(int[][] res, int s, int t, int[] level) {
        Arrays.fill(level, -1);
        level[s] = 0;
        Queue<Integer> q = new LinkedList<>();
        q.add(s);
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int v = 0; v < res.length; v++) {
                if (level[v] < 0 && res[u][v] > 0) {
                    level[v] = level[u] + 1;
                    q.add(v);
                }
            }
        }
        return level[t] >= 0;
    }

    private static int dinicDfs(int[][] res, int u, int t, int pushed, int[] level, int[] ptr) {
        if (pushed == 0 || u == t) return pushed;
        for (int v = ptr[u]; v < res.length; v = ++ptr[u]) {
            if (level[v] == level[u] + 1 && res[u][v] > 0) {
                int tr = dinicDfs(res, v, t, Math.min(pushed, res[u][v]), level, ptr);
                if (tr > 0) {
                    res[u][v] -= tr;
                    res[v][u] += tr;
                    return tr;
                }
            }
        }
        return 0;
    }
}
