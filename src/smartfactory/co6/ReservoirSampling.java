package smartfactory.co6;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReservoirSampling {
    public static class StepLog {
        public int itemIndex;
        public String item;
        public int randomJ;
        public boolean replaced;
        public int replacedSlot;

        public StepLog(int itemIndex, String item, int randomJ, boolean replaced, int replacedSlot) {
            this.itemIndex = itemIndex;
            this.item = item;
            this.randomJ = randomJ;
            this.replaced = replaced;
            this.replacedSlot = replacedSlot;
        }
    }

    public static class SampleResult {
        public String[] finalReservoir;
        public List<StepLog> stepLogs;

        public SampleResult(String[] finalReservoir, List<StepLog> stepLogs) {
            this.finalReservoir = finalReservoir;
            this.stepLogs = stepLogs;
        }
    }

    public static SampleResult sample(String[] stream, int k, Random rand) {
        String[] reservoir = new String[k];
        List<StepLog> logs = new ArrayList<>();

        for (int i = 0; i < stream.length; i++) {
            String item = stream[i];
            if (i < k) {
                reservoir[i] = item;
                logs.add(new StepLog(i + 1, item, i, true, i));
            } else {
                int j = rand.nextInt(i + 1);
                if (j < k) {
                    reservoir[j] = item;
                    logs.add(new StepLog(i + 1, item, j, true, j));
                } else {
                    logs.add(new StepLog(i + 1, item, j, false, -1));
                }
            }
        }

        return new SampleResult(reservoir, logs);
    }
}
