package smartfactory.co4;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MaxFlowMinCut {
    public static class CutEdge {
        public int u, v, capacity;
        public CutEdge(int u, int v, int capacity) {
            this.u = u;
            this.v = v;
            this.capacity = capacity;
        }
    }

    public static class MinCutResult {
        public int maxFlow;
        public int minCutCapacity;
        public boolean[] reachableFromSource;
        public List<CutEdge> cutEdges;

        public MinCutResult(int maxFlow, int minCutCapacity, boolean[] reachableFromSource, List<CutEdge> cutEdges) {
            this.maxFlow = maxFlow;
            this.minCutCapacity = minCutCapacity;
            this.reachableFromSource = reachableFromSource;
            this.cutEdges = cutEdges;
        }
    }

    public static MinCutResult compute(int[][] cap, int source, int sink) {
        EdmondsKarp.FlowResult flowRes = EdmondsKarp.compute(cap, source, sink);
        int n = cap.length;
        int[][] res = flowRes.finalResidual;

        boolean[] reachable = new boolean[n];
        Queue<Integer> rq = new LinkedList<>();
        rq.add(source);
        reachable[source] = true;
        while (!rq.isEmpty()) {
            int u = rq.poll();
            for (int v = 0; v < n; v++) {
                if (!reachable[v] && res[u][v] > 0) {
                    reachable[v] = true;
                    rq.add(v);
                }
            }
        }

        List<CutEdge> cutEdges = new ArrayList<>();
        int cutCapacity = 0;
        for (int u = 0; u < n; u++) {
            if (reachable[u]) {
                for (int v = 0; v < n; v++) {
                    if (!reachable[v] && cap[u][v] > 0) {
                        cutCapacity += cap[u][v];
                        cutEdges.add(new CutEdge(u, v, cap[u][v]));
                    }
                }
            }
        }

        return new MinCutResult(flowRes.maxFlow, cutCapacity, reachable, cutEdges);
    }
}
