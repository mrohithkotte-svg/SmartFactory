package smartfactory.menu;

import smartfactory.co1.StringAlgorithmsMenu;
import smartfactory.co2.SuffixStructuresMenu;
import smartfactory.co3.DynamicProgrammingMenu;
import smartfactory.co4.NetworkFlowMenu;
import smartfactory.co5.NPCompletenessMenu;
import smartfactory.co6.RandomizedParallelMenu;
import smartfactory.data.FactoryData;
import smartfactory.model.Machine;
import smartfactory.model.Report;
import smartfactory.util.InputUtils;

import java.util.List;

public class MainMenu {
    public static void run() {
        while (true) {
            System.out.println("\n========== SMARTFACTORY ==========");
            System.out.println("1. CO1 - String Algorithms");
            System.out.println("2. CO2 - Suffix Structures");
            System.out.println("3. CO3 - Advanced Dynamic Programming");
            System.out.println("4. CO4 - Network Flow");
            System.out.println("5. CO5 - NP-Completeness and Approximation");
            System.out.println("6. CO6 - Randomized and Parallel Algorithms");
            System.out.println("7. Factory Dashboard");
            System.out.println("8. View Factory Data");
            System.out.println("0. Exit");

            String choice = InputUtils.readLine("Enter your choice: ").trim();

            switch (choice) {
                case "1" -> StringAlgorithmsMenu.display();
                case "2" -> SuffixStructuresMenu.display();
                case "3" -> DynamicProgrammingMenu.display();
                case "4" -> NetworkFlowMenu.display();
                case "5" -> NPCompletenessMenu.display();
                case "6" -> RandomizedParallelMenu.display();
                case "7" -> showDashboard();
                case "8" -> showFactoryData();
                case "0" -> {
                    System.out.println("\nThank you for using SmartFactory Platform.");
                    return;
                }
                default -> InputUtils.pause("Invalid choice. Please select from 0 to 8.");
            }
        }
    }

    private static void showDashboard() {
        InputUtils.header("FACTORY DASHBOARD");
        List<Machine> machines = FactoryData.getMachines();
        List<Report> reports = FactoryData.getReports();

        long running = machines.stream().filter(m -> m.getStatus().equals("Running")).count();
        long critical = reports.stream().filter(r -> r.getSeverity().equals("Critical") || r.getSeverity().equals("High")).count();

        System.out.println("Total Machines       : " + machines.size());
        System.out.println("Running Machines     : " + running);
        System.out.println("Maintenance Reports  : " + reports.size());
        System.out.println("High/Critical Reports: " + critical);
        System.out.println("Factory Documents    : " + FactoryData.getDocuments().size());
        System.out.println("\nSystem Modules Implemented:");
        System.out.println("  CO1 -> String Search (KMP, Z-Function, Rabin-Karp, Aho-Corasick)");
        System.out.println("  CO2 -> Factory Document Indexing (Suffix Array, SA-IS, Kasai, Suffix Automaton)");
        System.out.println("  CO3 -> Advanced DP (Edit Distances, Matrix-Chain, Bitmask DP, Route DP)");
        System.out.println("  CO4 -> Network Flow (Ford-Fulkerson, Edmonds-Karp, Dinic, Bipartite, Min-Cut)");
        System.out.println("  CO5 -> NP-Completeness & Approx (3-SAT, Reductions, Vertex-Cover 2-Approx)");
        System.out.println("  CO6 -> Randomized & Parallel (QuickSort, Miller-Rabin, Reservoir, Blelloch, Reduce, Brent)");
        InputUtils.pause();
    }

    private static void showFactoryData() {
        InputUtils.header("FACTORY DATA");
        System.out.println("MACHINES");
        for (Machine m : FactoryData.getMachines()) {
            System.out.printf("%-10s %-18s %-12s cost=%-3d value=%d%n",
                m.getId(), m.getType(), m.getStatus(), m.getCost(), m.getValue());
        }
        System.out.println("\nMAINTENANCE REPORTS");
        for (Report r : FactoryData.getReports()) {
            System.out.println(r.getId() + " | " + r.getMachine() + " | " + r.getSeverity() + " | " + r.getDescription());
        }
        InputUtils.pause();
    }
}
