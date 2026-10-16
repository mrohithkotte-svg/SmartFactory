package smartfactory.co5;

public class ThreeSATToClique {
    public static final String[] VERTICES = {
        "v1[C1: x1]", "v2[C1: x2]", "v3[C1: x3]",
        "v4[C2:~x1]", "v5[C2:~x2]", "v6[C2: x3]",
        "v7[C3: x1]", "v8[C3:~x2]", "v9[C3:~x3]"
    };

    public static boolean hasEdge(int i, int j) {
        int clauseI = i / 3;
        int clauseJ = j / 3;
        if (clauseI == clauseJ) return false;

        // Contradiction checks between literal indices
        if ((i == 0 && j == 3) || (i == 3 && j == 0)) return false; // x1 vs ~x1
        if ((i == 1 && j == 4) || (i == 4 && j == 1)) return false; // x2 vs ~x2
        if ((i == 1 && j == 7) || (i == 7 && j == 1)) return false; // x2 vs ~x2
        if ((i == 2 && j == 8) || (i == 8 && j == 2)) return false; // x3 vs ~x3
        if ((i == 5 && j == 8) || (i == 8 && j == 5)) return false; // x3 vs ~x3
        if ((i == 3 && j == 6) || (i == 6 && j == 3)) return false; // ~x1 vs x1

        return true;
    }
}
