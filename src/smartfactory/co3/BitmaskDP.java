package smartfactory.co3;

import smartfactory.model.Machine;

import java.util.ArrayList;
import java.util.List;

public class BitmaskDP {
    public static class SelectionResult {
        public int bestValue;
        public int bestCost;
        public List<Machine> selectedMachines;

        public SelectionResult(int bestValue, int bestCost, List<Machine> selectedMachines) {
            this.bestValue = bestValue;
            this.bestCost = bestCost;
            this.selectedMachines = selectedMachines;
        }
    }

    public static SelectionResult selectMachines(List<Machine> machines, int capacity) {
        int n = machines.size();
        int best = 0, bestMask = 0, bestCost = 0;

        for (int mask = 0; mask < (1 << n); mask++) {
            int cost = 0, value = 0;
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    cost += machines.get(i).getCost();
                    value += machines.get(i).getValue();
                }
            }
            if (cost <= capacity && value > best) {
                best = value;
                bestMask = mask;
                bestCost = cost;
            }
        }

        List<Machine> chosen = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if ((bestMask & (1 << i)) != 0) {
                chosen.add(machines.get(i));
            }
        }
        return new SelectionResult(best, bestCost, chosen);
    }
}
