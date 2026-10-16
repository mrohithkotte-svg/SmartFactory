package smartfactory.co5;

import java.util.Set;
import java.util.TreeSet;

public class IndependentSetToVertexCover {
    public static Set<Integer> deriveVertexCover(int totalVertices, Set<Integer> independentSet) {
        Set<Integer> vertexCover = new TreeSet<>();
        for (int i = 0; i < totalVertices; i++) {
            if (!independentSet.contains(i)) {
                vertexCover.add(i);
            }
        }
        return vertexCover;
    }

    public static boolean verifyEdgeCoverage(int[][] edges, Set<Integer> vertexCover) {
        for (int[] e : edges) {
            if (!vertexCover.contains(e[0]) && !vertexCover.contains(e[1])) {
                return false;
            }
        }
        return true;
    }
}
