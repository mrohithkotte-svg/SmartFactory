package smartfactory.co4;

import smartfactory.util.InputUtils;

import java.util.ArrayList;
import java.util.List;

public class NetworkFlowMenu {
    public static final String[] FLOW_NODES = {
        "Source (Raw Material Intake)",
        "Stage 1A (CNC Milling)",
        "Stage 1B (Drilling/Lathe)",
        "Stage 2A (Assembly Line 1)",
        "Stage 2B (Assembly Line 2)",
        "Sink (Dispatch & Packaging)"
    };

    public static int[][] getFactoryNetworkCapacities() {
        int n = 6;
        int[][] cap = new int[n][n];
        cap[0][1] = 10;
        cap[0][2] = 10;
        cap[1][2] = 2;
        cap[1][3] = 4;
        cap[1][4] = 8;
        cap[2][4] = 9;
        cap[3][5] = 10;
        cap[4][3] = 6;
        cap[4][5] = 10;
        return cap;
    }

    public static void display() {
        while (true) {
            InputUtils.header("CO4 - NETWORK FLOW ALGORITHMS");
            System.out.println("1. Ford-Fulkerson Algorithm (DFS Augmenting Paths)");
            System.out.println("2. Edmonds-Karp Algorithm (BFS Augmenting Paths)");
            System.out.println("3. Dinic's Algorithm (Level Graph & Blocking Flow)");
            System.out.println("4. Bipartite Matching (Worker-to-Machine Assignment)");
            System.out.println("5. Maximum Flow and Minimum Cut (Bottleneck Analysis)");
            System.out.println("6. Back to Main Menu");
            System.out.println("0. Back to Main Menu");

            String c = InputUtils.readLine("\nChoice: ").trim();
            if (c.equals("0") || c.equals("6")) return;
            switch (c) {
                case "1" -> demoFordFulkerson();
                case "2" -> demoEdmondsKarp();
                case "3" -> demoDinic();
                case "4" -> demoBipartiteMatching();
                case "5" -> demoMaxFlowMinCut();
                default -> InputUtils.pause("Invalid choice.");
            }
        }
    }

    public static void printNetworkSummary(int[][] cap) {
        System.out.println("Factory Production Network Topology (Capacities in Units/Hour):");
        for (int i = 0; i < FLOW_NODES.length; i++) {
            System.out.println("  Node " + i + ": " + FLOW_NODES[i]);
        }
        System.out.println("Inter-stage Capacity Links:");
        for (int u = 0; u < cap.length; u++) {
            for (int v = 0; v < cap.length; v++) {
                if (cap[u][v] > 0) {
                    System.out.printf("  Link %d -> %d (%-22s -> %-24s): %d units/hr%n",
                        u, v, FLOW_NODES[u].split(" \\(")[0], FLOW_NODES[v].split(" \\(")[0], cap[u][v]);
                }
            }
        }
        System.out.println("------------------------------------------------------------");
    }

    private static void demoFordFulkerson() {
        InputUtils.header("FORD-FULKERSON ALGORITHM (DFS PATH SEARCH)");
        int[][] cap = getFactoryNetworkCapacities();
        printNetworkSummary(cap);

        System.out.println("Augmenting Path Search via DFS:");
        FordFulkerson.FlowResult res = FordFulkerson.compute(cap, 0, 5);
        for (String log : res.pathLogs) {
            System.out.println("  " + log);
        }

        System.out.println("\n------------------------------------------------------------");
        System.out.println("RESULT: Maximum Material Flow = " + res.maxFlow + " units/hour");
        System.out.println("Algorithm Details: Ford-Fulkerson repeatedly finds augmenting");
        System.out.println("paths using Depth-First Search (DFS) and pushes bottleneck");
        System.out.println("material flow until no s-t path exists in the residual network.");
        InputUtils.pause();
    }

    private static void demoEdmondsKarp() {
        InputUtils.header("EDMONDS-KARP ALGORITHM (BFS SHORTEST PATH)");
        int[][] cap = getFactoryNetworkCapacities();
        printNetworkSummary(cap);

        System.out.println("Augmenting Path Search via BFS (Shortest Path by Edge Count):");
        EdmondsKarp.FlowResult res = EdmondsKarp.compute(cap, 0, 5);
        for (String log : res.pathLogs) {
            System.out.println("  " + log);
        }

        System.out.println("\n------------------------------------------------------------");
        System.out.println("RESULT: Maximum Material Flow = " + res.maxFlow + " units/hour");
        System.out.println("Algorithm Details: Edmonds-Karp is an implementation of");
        System.out.println("Ford-Fulkerson that uses BFS to find shortest augmenting paths.");
        System.out.println("This guarantees polynomial runtime O(V * E^2).");
        InputUtils.pause();
    }

    private static void demoDinic() {
        InputUtils.header("DINIC'S ALGORITHM (LEVEL GRAPH & BLOCKING FLOW)");
        int[][] cap = getFactoryNetworkCapacities();
        printNetworkSummary(cap);

        Dinic.FlowResult res = Dinic.compute(cap, 0, 5);
        for (String log : res.phaseLogs) {
            System.out.println(log);
        }

        System.out.println("\n------------------------------------------------------------");
        System.out.println("RESULT: Maximum Material Flow = " + res.maxFlow + " units/hour");
        System.out.println("Algorithm Details: Dinic's algorithm builds a Level Graph using");
        System.out.println("BFS and pushes Blocking Flows using DFS. Runs in O(V^2 * E).");
        InputUtils.pause();
    }

    private static void demoBipartiteMatching() {
        InputUtils.header("BIPARTITE MATCHING - WORKER TO MACHINE ASSIGNMENT");
        String[] workers = {
            "W1 - Alice (CNC Specialist)",
            "W2 - Bob (Robotics Tech)",
            "W3 - Charlie (Welding & Assembly)",
            "W4 - David (General Machinist)",
            "W5 - Eva (Quality & Drilling)"
        };
        String[] factoryMachines = {
            "MTR-101 (CNC Lathe)",
            "MTR-102 (Robotic Arm)",
            "MTR-103 (Press Machine)",
            "MTR-104 (Assembly Robot)",
            "MTR-105 (Drilling Unit)"
        };

        boolean[][] eligible = {
            {true,  false, false, false, true },
            {false, true,  false, true,  false},
            {false, false, true,  true,  false},
            {true,  false, true,  false, false},
            {false, true,  false, false, true }
        };

        System.out.println("Worker Machine Eligibility Matrix:");
        System.out.printf("%-32s | %s%n", "Operator", "Eligible Factory Machines");
        System.out.println("----------------------------------------------------------------------");
        for (int i = 0; i < workers.length; i++) {
            List<String> list = new ArrayList<>();
            for (int j = 0; j < factoryMachines.length; j++) {
                if (eligible[i][j]) list.add(factoryMachines[j].split(" \\(")[0]);
            }
            System.out.printf("%-32s | %s%n", workers[i], String.join(", ", list));
        }

        BipartiteMatching.MatchingResult matchRes = BipartiteMatching.findMaximumMatching(eligible, workers.length, factoryMachines.length);

        System.out.println("\nOptimal Worker-to-Machine Assignment Result:");
        System.out.println("----------------------------------------------------------------------");
        System.out.printf("%-30s <---> %-30s%n", "Factory Operator", "Assigned Production Machine");
        System.out.println("----------------------------------------------------------------------");
        for (int m = 0; m < factoryMachines.length; m++) {
            if (matchRes.matchMapping[m] != -1) {
                System.out.printf("%-30s <---> %-30s%n", workers[matchRes.matchMapping[m]], factoryMachines[m]);
            }
        }
        System.out.println("----------------------------------------------------------------------");
        System.out.println("Total Machines Mapped : " + matchRes.totalMatches + " of " + workers.length);
        System.out.println("Efficiency Rate       : " + (matchRes.totalMatches * 100 / workers.length) + "% Worker Utilization");
        InputUtils.pause();
    }

    private static void demoMaxFlowMinCut() {
        InputUtils.header("MAXIMUM FLOW & MINIMUM CUT - BOTTLENECK ANALYSIS");
        int[][] cap = getFactoryNetworkCapacities();
        printNetworkSummary(cap);

        MaxFlowMinCut.MinCutResult res = MaxFlowMinCut.compute(cap, 0, 5);

        System.out.println("Maximum Material Flow Result: " + res.maxFlow + " units/hour\n");
        System.out.println("Network Partition (Cut):");
        System.out.print("  Intake Partition (S)   : ");
        for (int i = 0; i < FLOW_NODES.length; i++) {
            if (res.reachableFromSource[i]) System.out.print("[" + i + ": " + FLOW_NODES[i].split(" \\(")[0] + "] ");
        }
        System.out.print("\n  Dispatch Partition (T) : ");
        for (int i = 0; i < FLOW_NODES.length; i++) {
            if (!res.reachableFromSource[i]) System.out.print("[" + i + ": " + FLOW_NODES[i].split(" \\(")[0] + "] ");
        }
        System.out.println("\n");

        System.out.println("Bottleneck Cut Edges (Edges from S to T in original graph):");
        for (MaxFlowMinCut.CutEdge edge : res.cutEdges) {
            System.out.printf("  * Saturated Link: Node %d -> Node %d (%s -> %s) | Capacity: %d units/hr%n",
                edge.u, edge.v, FLOW_NODES[edge.u].split(" \\(")[0], FLOW_NODES[edge.v].split(" \\(")[0], edge.capacity);
        }

        System.out.println("\nVerification: Total Minimum Cut Capacity = " + res.minCutCapacity + " units/hr");
        System.out.println("Max-Flow Min-Cut Theorem Confirmed: Max Flow (" + res.maxFlow + ") == Min Cut (" + res.minCutCapacity + ")");
        System.out.println("\nFactory Impact Analysis:");
        System.out.println("The saturated links listed above represent the true physical bottlenecks");
        System.out.println("of the factory floor. Even if downstream assembly lines have surplus capacity,");
        System.out.println("total throughput cannot exceed " + res.minCutCapacity + " units/hr without upgrading these specific links.");
        InputUtils.pause();
    }
}
