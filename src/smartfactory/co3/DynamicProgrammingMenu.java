package smartfactory.co3;

import smartfactory.data.FactoryData;
import smartfactory.model.Machine;
import smartfactory.util.InputUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DynamicProgrammingMenu {
    public static void display() {
        while (true) {
            InputUtils.header("CO3 - ADVANCED DYNAMIC PROGRAMMING");
            System.out.println("1. Fuzzy Machine ID Correction (Levenshtein / Damerau / Weighted)");
            System.out.println("2. Matrix-Chain Multiplication (Production Stage Ordering)");
            System.out.println("3. Bitmask DP - Machine Selection Under Resource Limit");
            System.out.println("4. DP on Subsets - Maintenance Route Optimization");
            System.out.println("0. Back to Main Menu");

            String c = InputUtils.readLine("\nChoice: ").trim();
            if (c.equals("0")) return;
            switch (c) {
                case "1" -> fuzzyMenu();
                case "2" -> matrixDemo();
                case "3" -> machineSelection();
                case "4" -> routeDemo();
                default -> InputUtils.pause("Invalid choice.");
            }
        }
    }

    private static void fuzzyMenu() {
        while (true) {
            InputUtils.header("CO3 - FUZZY MACHINE ENTRY CORRECTION");
            System.out.println("1. Levenshtein Distance");
            System.out.println("2. Damerau-Levenshtein Distance");
            System.out.println("3. Weighted Edit Distance");
            System.out.println("0. Back to CO3 Menu");

            String c = InputUtils.readLine("\nChoice: ").trim();
            if (c.equals("0")) return;
            if (!c.matches("[123]")) {
                InputUtils.pause("Invalid choice.");
                continue;
            }

            String input = InputUtils.readLine("Enter machine ID / production code: ");
            List<Machine> machines = FactoryData.getMachines();
            List<int[]> scored = new ArrayList<>();

            for (int i = 0; i < machines.size(); i++) {
                int d;
                String target = machines.get(i).getId();
                if (c.equals("1")) d = EditDistance.levenshtein(input, target);
                else if (c.equals("2")) d = EditDistance.damerauLevenshtein(input, target);
                else d = WeightedEditDistance.calculate(input, target, 1, 1, 2);
                scored.add(new int[]{d, i});
            }
            scored.sort(Comparator.comparingInt(a -> a[0]));

            String alg = c.equals("1") ? "Levenshtein" : c.equals("2") ? "Damerau-Levenshtein" : "Weighted Edit Distance";
            InputUtils.header(alg + " RESULT");
            System.out.printf("%-12s %-18s %-12s %-10s%n", "Machine", "Type", "Distance", "Status");
            System.out.println("----------------------------------------------------------");
            for (int i = 0; i < Math.min(6, scored.size()); i++) {
                Machine m = machines.get(scored.get(i)[1]);
                System.out.printf("%-12s %-18s %-12d %-10s%n", m.getId(), m.getType(), scored.get(i)[0], m.getStatus());
            }
            InputUtils.pause();
        }
    }

    private static void matrixDemo() {
        String input = InputUtils.readLine("Enter matrix dimensions (example 10,20,30,40): ");
        String[] parts = input.split(",");
        int[] p = new int[parts.length];
        try {
            for (int i = 0; i < p.length; i++) p[i] = Integer.parseInt(parts[i].trim());
        } catch (Exception e) {
            InputUtils.pause("Invalid dimensions.");
            return;
        }
        if (p.length < 3) {
            InputUtils.pause("Need at least 3 dimensions.");
            return;
        }

        MatrixChainMultiplication.Result res = MatrixChainMultiplication.compute(p);
        InputUtils.header("MATRIX-CHAIN RESULT");
        System.out.println("Minimum scalar multiplications: " + res.minMultiplications);
        System.out.println("Optimal order: " + res.optimalOrder);
        InputUtils.pause();
    }

    private static void machineSelection() {
        int cap = InputUtils.readInt("Enter resource capacity (example 45): ", -1);
        if (cap < 0) {
            InputUtils.pause("Invalid capacity.");
            return;
        }
        List<Machine> machines = FactoryData.getMachines();
        if (machines.size() > 20) {
            InputUtils.pause("Dataset too large for demonstration bitmask.");
            return;
        }

        BitmaskDP.SelectionResult result = BitmaskDP.selectMachines(machines, cap);
        InputUtils.header("BITMASK DP RESULT");
        System.out.println("Resource capacity : " + cap);
        System.out.println("Selected machines :");
        for (Machine m : result.selectedMachines) {
            System.out.println("  " + m.getId() + " (cost " + m.getCost() + ", value " + m.getValue() + ")");
        }
        System.out.println("Total resource cost: " + result.bestCost);
        System.out.println("Total production value: " + result.bestValue);
        InputUtils.pause();
    }

    private static void routeDemo() {
        int[][] d = {
            {0, 10, 15, 20},
            {10, 0, 35, 25},
            {15, 35, 0, 30},
            {20, 25, 30, 0}
        };

        SubsetDP.RouteResult res = SubsetDP.findOptimalRoute(d);
        InputUtils.header("DP ON SUBSETS - MAINTENANCE ROUTE");
        System.out.println("Machines represented: M1, M2, M3, M4");
        System.out.println("Minimum route cost : " + res.minCost);
        System.out.println("Optimal route       : " + res.route);
        InputUtils.pause();
    }
}
