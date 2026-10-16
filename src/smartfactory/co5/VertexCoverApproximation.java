package smartfactory.co5;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class VertexCoverApproximation {
    public static class StepLog {
        public int stepNumber;
        public int u, v;
        public List<Integer> newlyCoveredEdgeIndices = new ArrayList<>();

        public StepLog(int stepNumber, int u, int v) {
            this.stepNumber = stepNumber;
            this.u = u;
            this.v = v;
        }
    }

    public static class ApproxResult {
        public Set<Integer> cover;
        public List<int[]> selectedMatchingEdges;
        public List<StepLog> steps;
        public boolean isVerified;

        public ApproxResult(Set<Integer> cover, List<int[]> selectedMatchingEdges, List<StepLog> steps, boolean isVerified) {
            this.cover = cover;
            this.selectedMatchingEdges = selectedMatchingEdges;
            this.steps = steps;
            this.isVerified = isVerified;
        }
    }

    public static ApproxResult compute(int totalVertices, int[][] edges) {
        Set<Integer> cover = new TreeSet<>();
        boolean[] edgeCovered = new boolean[edges.length];
        List<int[]> selectedEdges = new ArrayList<>();
        List<StepLog> steps = new ArrayList<>();

        int step = 0;
        for (int i = 0; i < edges.length; i++) {
            if (!edgeCovered[i]) {
                step++;
                int u = edges[i][0], v = edges[i][1];
                cover.add(u);
                cover.add(v);
                selectedEdges.add(edges[i]);
                StepLog stepLog = new StepLog(step, u, v);

                for (int j = 0; j < edges.length; j++) {
                    if (!edgeCovered[j]) {
                        if (edges[j][0] == u || edges[j][0] == v || edges[j][1] == u || edges[j][1] == v) {
                            edgeCovered[j] = true;
                            stepLog.newlyCoveredEdgeIndices.add(j);
                        }
                    }
                }
                steps.add(stepLog);
            }
        }

        boolean verified = true;
        for (int[] edge : edges) {
            if (!cover.contains(edge[0]) && !cover.contains(edge[1])) {
                verified = false;
                break;
            }
        }

        return new ApproxResult(cover, selectedEdges, steps, verified);
    }
}
