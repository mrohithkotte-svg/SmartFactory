package smartfactory.co5;

import smartfactory.util.InputUtils;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class NPCompletenessMenu {
    public static void display() {
        while (true) {
            InputUtils.header("CO5 - NP-COMPLETENESS & APPROXIMATION ALGORITHMS");
            System.out.println("1. SAT / 3-SAT Constraint Representation & Evaluation");
            System.out.println("2. 3-SAT to CLIQUE Reduction");
            System.out.println("3. CLIQUE to INDEPENDENT-SET Reduction");
            System.out.println("4. INDEPENDENT-SET to VERTEX-COVER Reduction");
            System.out.println("5. Vertex-Cover 2-Approximation Algorithm");
            System.out.println("6. Back to Main Menu");
            System.out.println("0. Back to Main Menu");

            String c = InputUtils.readLine("\nChoice: ").trim();
            if (c.equals("0") || c.equals("6")) return;
            switch (c) {
                case "1" -> demoSat3Sat();
                case "2" -> demo3SatToClique();
                case "3" -> demoCliqueToIndependentSet();
                case "4" -> demoIndependentSetToVertexCover();
                case "5" -> demoVertexCover2Approx();
                default -> InputUtils.pause("Invalid choice.");
            }
        }
    }

    private static void demoSat3Sat() {
        InputUtils.header("SAT / 3-SAT CONSTRAINT EVALUATION");
        System.out.println("Factory Production Safety Constraints (3-CNF Formula):");
        System.out.println("Variables:");
        System.out.println("  x1: CNC Machine MTR-101 Online");
        System.out.println("  x2: Coolant Pump Pressurized");
        System.out.println("  x3: Backup Generator Active");
        System.out.println("  x4: Ventilation Fan Running\n");

        System.out.println("Formula Clauses (C1 and C2 and C3 and C4):");
        System.out.println("  C1: ( x1  OR  x2  OR NOT x3)");
        System.out.println("  C2: (NOT x1  OR  x3  OR  x4)");
        System.out.println("  C3: (NOT x2  OR NOT x3  OR  x4)");
        System.out.println("  C4: ( x1  OR NOT x4  OR  x2)\n");

        System.out.println("Choose Evaluation Mode:");
        System.out.println("  1. Test Default Satisfying Assignment (x1=T, x2=T, x3=T, x4=T)");
        System.out.println("  2. Test Default Unsatisfying Assignment (x1=F, x2=F, x3=T, x4=F)");
        System.out.println("  3. Input Custom Truth Assignment");
        String ch = InputUtils.readLine("Choice: ").trim();

        boolean x1 = true, x2 = true, x3 = true, x4 = true;
        if (ch.equals("2")) {
            x1 = false; x2 = false; x3 = true; x4 = false;
        } else if (ch.equals("3")) {
            x1 = Boolean.parseBoolean(InputUtils.readLine("Enter value for x1 (true/false): ").trim());
            x2 = Boolean.parseBoolean(InputUtils.readLine("Enter value for x2 (true/false): ").trim());
            x3 = Boolean.parseBoolean(InputUtils.readLine("Enter value for x3 (true/false): ").trim());
            x4 = Boolean.parseBoolean(InputUtils.readLine("Enter value for x4 (true/false): ").trim());
        }

        System.out.printf("%nEvaluating assignment: [x1=%b, x2=%b, x3=%b, x4=%b]%n", x1, x2, x3, x4);
        SATSolverDemo.EvaluationResult eval = SATSolverDemo.evaluate(x1, x2, x3, x4);

        System.out.printf("  C1: (%b OR %b OR %b) = %b%n", x1, x2, !x3, eval.c1);
        System.out.printf("  C2: (%b OR %b OR %b) = %b%n", !x1, x3, x4, eval.c2);
        System.out.printf("  C3: (%b OR %b OR %b) = %b%n", !x2, !x3, x4, eval.c3);
        System.out.printf("  C4: (%b OR %b OR %b) = %b%n", x1, !x4, x2, eval.c4);

        System.out.println("\n------------------------------------------------------------");
        System.out.println("Overall Formula Evaluation: " + (eval.isSatisfied ? "SATISFIED (TRUE)" : "UNSATISFIED (FALSE)"));
        System.out.println("Note: 3-SAT is NP-complete. Finding an assignment for arbitrary");
        System.out.println("large formulas is hard, but verifying an assignment runs in O(N).");
        InputUtils.pause();
    }

    private static void demo3SatToClique() {
        InputUtils.header("3-SAT TO CLIQUE REDUCTION");
        System.out.println("Demonstrating polynomial-time reduction from 3-SAT to CLIQUE.");
        System.out.println("Sample 3-CNF formula with k = 3 clauses:");
        System.out.println("  Clause 1: ( x1  OR  x2  OR  x3)");
        System.out.println("  Clause 2: (NOT x1  OR NOT x2  OR  x3)");
        System.out.println("  Clause 3: ( x1  OR NOT x2  OR NOT x3)\n");

        System.out.println("Reduction Rules:");
        System.out.println("  1. Create a vertex for each literal in each clause (3 * 3 = 9 vertices).");
        System.out.println("  2. Place an edge between two vertices if:");
        System.out.println("     - They belong to DIFFERENT clauses.");
        System.out.println("     - They are NOT contradictory (e.g., cannot connect x1 with NOT x1).");
        System.out.println("  3. Formula is satisfiable <===> Graph has a CLIQUE of size k = 3.\n");

        System.out.println("Constructed Graph Vertices (9 total):");
        for (int i = 0; i < ThreeSATToClique.VERTICES.length; i++) {
            System.out.printf("  Vertex %d: %s%n", i, ThreeSATToClique.VERTICES[i]);
        }

        System.out.println("\nSatisfying Assignment Example: [x1 = TRUE, x2 = FALSE, x3 = TRUE]");
        System.out.println("Selected one true literal from each clause:");
        System.out.println("  From C1: Vertex 0 ( x1) is TRUE");
        System.out.println("  From C2: Vertex 4 (~x2) is TRUE");
        System.out.println("  From C3: Vertex 6 ( x1) is TRUE");

        System.out.println("\nChecking Clique Edges Between Selected Vertices {0, 4, 6}:");
        System.out.println("  Edge (0, 4): C1 vs C2, literals ( x1, ~x2) -> Non-contradictory: " +
            (ThreeSATToClique.hasEdge(0, 4) ? "YES, EDGE EXISTS" : "NO"));
        System.out.println("  Edge (4, 6): C2 vs C3, literals (~x2,  x1) -> Non-contradictory: " +
            (ThreeSATToClique.hasEdge(4, 6) ? "YES, EDGE EXISTS" : "NO"));
        System.out.println("  Edge (0, 6): C1 vs C3, literals ( x1,  x1) -> Non-contradictory: " +
            (ThreeSATToClique.hasEdge(0, 6) ? "YES, EDGE EXISTS" : "NO"));

        System.out.println("\nResult: Vertices {0, 4, 6} form a 3-CLIQUE!");
        System.out.println("Conclusion: The satisfying truth assignment maps directly to a");
        System.out.println("clique of size k=3 in the reduction graph.");
        InputUtils.pause();
    }

    private static void demoCliqueToIndependentSet() {
        InputUtils.header("CLIQUE TO INDEPENDENT-SET REDUCTION");
        System.out.println("Demonstrating graph complement transformation: G <---> G_complement\n");
        System.out.println("Factory Compatibility Graph G (5 Machines: M0, M1, M2, M3, M4):");
        System.out.println("  An edge in G means two machines CAN safely run concurrently.\n");

        int n = 5;
        boolean[][] g = new boolean[n][n];
        g[0][1] = g[1][0] = true;
        g[1][2] = g[2][1] = true;
        g[0][2] = g[2][0] = true;
        g[2][3] = g[3][2] = true;
        g[3][4] = g[4][3] = true;

        System.out.println("Edges in Original Compatibility Graph G:");
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (g[i][j]) System.out.printf("  (M%d, M%d) [Compatible]%n", i, j);
            }
        }
        System.out.println("Notice: Subset S = {M0, M1, M2} forms a 3-CLIQUE in G (all pairs connected).\n");

        boolean[][] gComp = CliqueToIndependentSet.buildComplement(g);

        System.out.println("Edges in Complement Graph G_complement (Interference / Conflict Graph):");
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (gComp[i][j]) System.out.printf("  (M%d, M%d) [Conflict/No Edge in G]%n", i, j);
            }
        }

        System.out.println("\nIndependent Set Verification in G_complement:");
        System.out.println("Look at the same subset S = {M0, M1, M2} in G_complement:");
        System.out.println("  Between M0 and M1: " + (gComp[0][1] ? "Edge exists" : "NO EDGE (Independent)"));
        System.out.println("  Between M1 and M2: " + (gComp[1][2] ? "Edge exists" : "NO EDGE (Independent)"));
        System.out.println("  Between M0 and M2: " + (gComp[0][2] ? "Edge exists" : "NO EDGE (Independent)"));

        System.out.println("\nResult: S = {M0, M1, M2} is an INDEPENDENT SET of size 3 in G_complement!");
        System.out.println("Theorem: S is a clique in G <===> S is an independent set in G_complement.");
        InputUtils.pause();
    }

    private static void demoIndependentSetToVertexCover() {
        InputUtils.header("INDEPENDENT-SET TO VERTEX-COVER REDUCTION");
        System.out.println("Demonstrating complementary vertex set relationship: S <---> V \\ S\n");

        int n = 6;
        int[][] edges = {
            {0, 1}, {1, 2}, {2, 3}, {3, 4}, {4, 5}, {1, 4}, {2, 5}
        };

        System.out.println("Graph G with |V| = 6 stations (0 to 5) and 7 pipeline connections:");
        for (int[] e : edges) System.out.printf("  Edge (%d, %d)%n", e[0], e[1]);

        Set<Integer> indSet = new TreeSet<>(Arrays.asList(0, 3));
        System.out.println("\n1. Independent Set S: " + indSet + " (|S| = 2)");
        System.out.println("   Verification: Edge (0, 3) does not exist in G. Independent set valid!");

        Set<Integer> vertexCover = IndependentSetToVertexCover.deriveVertexCover(n, indSet);
        System.out.println("\n2. Complementary Vertex Set C = V \\ S: " + vertexCover + " (|C| = " + vertexCover.size() + ")");

        System.out.println("\n3. Verifying Vertex Cover Property (every edge must touch >= 1 vertex in C):");
        for (int[] e : edges) {
            boolean covered = vertexCover.contains(e[0]) || vertexCover.contains(e[1]);
            int coverNode = vertexCover.contains(e[0]) ? e[0] : e[1];
            System.out.printf("   Edge (%d, %d) -> Covered by node %d: %s%n", e[0], e[1], coverNode, covered ? "YES" : "NO");
        }

        boolean allCovered = IndependentSetToVertexCover.verifyEdgeCoverage(edges, vertexCover);
        System.out.println("\n------------------------------------------------------------");
        System.out.println("Result: Every edge has at least one endpoint in V \\ S: " + (allCovered ? "CONFIRMED" : "FAILED"));
        System.out.println("Mathematical Duality Theorem:");
        System.out.println("  S is an Independent Set of size k in G");
        System.out.println("  <====> V \\ S is a Vertex Cover of size (|V| - k) in G.");
        InputUtils.pause();
    }

    private static void demoVertexCover2Approx() {
        InputUtils.header("VERTEX-COVER 2-APPROXIMATION ALGORITHM");
        System.out.println("Sensor Placement Problem: Install minimum monitoring sensors");
        System.out.println("on machines so that every communication pipeline (edge) is covered.\n");

        int n = 7;
        int[][] edges = {
            {0, 1}, {1, 2}, {1, 3}, {2, 4}, {3, 4}, {3, 5}, {4, 6}, {5, 6}
        };

        System.out.println("Factory Communication Network (7 Machines, 8 Edges):");
        for (int i = 0; i < edges.length; i++) {
            System.out.printf("  Edge %d: (M%d <---> M%d)%n", i, edges[i][0], edges[i][1]);
        }

        System.out.println("\nExecution Steps of 2-Approximation Algorithm:");
        System.out.println("While there exists an uncovered edge (u, v):");
        System.out.println("  Add BOTH endpoints u and v to cover, and mark all incident edges covered.\n");

        VertexCoverApproximation.ApproxResult result = VertexCoverApproximation.compute(n, edges);

        for (VertexCoverApproximation.StepLog s : result.steps) {
            System.out.printf("  Step %d: Selected uncovered edge (M%d, M%d) -> Added M%d and M%d to Cover%n",
                s.stepNumber, s.u, s.v, s.u, s.v);
            for (int eIdx : s.newlyCoveredEdgeIndices) {
                System.out.printf("          -> Edge %d (M%d, M%d) is now covered%n",
                    eIdx, edges[eIdx][0], edges[eIdx][1]);
            }
        }

        System.out.println("\n------------------------------------------------------------");
        System.out.println("Selected Vertex Cover : " + result.cover);
        System.out.println("Total Cover Size      : " + result.cover.size() + " machines");
        System.out.println("Maximal Matching Edges: " + result.selectedMatchingEdges.size() + " edges");

        System.out.println("\nEdge Coverage Verification Check:");
        System.out.println("Verification Status   : " + (result.isVerified ? "ALL EDGES COVERED (100% Valid)" : "ERROR: Uncovered edges found"));

        System.out.println("\n2-Approximation Ratio Guarantee Proof:");
        System.out.println("  1. The selected edges form a matching M (|M| = " + result.selectedMatchingEdges.size() + ").");
        System.out.println("  2. Any valid vertex cover must select AT LEAST one endpoint per edge in M.");
        System.out.println("     Therefore, OPT >= |M| = " + result.selectedMatchingEdges.size() + ".");
        System.out.println("  3. Our algorithm selected both endpoints of each edge in M:");
        System.out.println("     |C| = 2 * |M| = " + result.cover.size() + ".");
        System.out.println("  4. Thus, |C| <= 2 * OPT. The solution is guaranteed to be at");
        System.out.println("     most 2 times the optimal minimum vertex cover size!");
        InputUtils.pause();
    }
}
