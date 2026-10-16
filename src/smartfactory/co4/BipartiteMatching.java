package smartfactory.co4;

import java.util.Arrays;

public class BipartiteMatching {
    public static class MatchingResult {
        public int totalMatches;
        public int[] matchMapping; // machine -> worker index

        public MatchingResult(int totalMatches, int[] matchMapping) {
            this.totalMatches = totalMatches;
            this.matchMapping = matchMapping;
        }
    }

    public static MatchingResult findMaximumMatching(boolean[][] eligible, int numWorkers, int numMachines) {
        int[] match = new int[numMachines];
        Arrays.fill(match, -1);
        int total = 0;

        for (int w = 0; w < numWorkers; w++) {
            boolean[] visited = new boolean[numMachines];
            if (bpmDfs(w, eligible, visited, match)) {
                total++;
            }
        }
        return new MatchingResult(total, match);
    }

    private static boolean bpmDfs(int u, boolean[][] eligible, boolean[] visited, int[] match) {
        for (int v = 0; v < match.length; v++) {
            if (eligible[u][v] && !visited[v]) {
                visited[v] = true;
                if (match[v] < 0 || bpmDfs(match[v], eligible, visited, match)) {
                    match[v] = u;
                    return true;
                }
            }
        }
        return false;
    }
}
