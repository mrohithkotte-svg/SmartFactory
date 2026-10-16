package smartfactory.co6;

import java.util.ArrayList;
import java.util.List;

public class BrentsTheorem {
    public static class Row {
        public int processors;
        public double timeBound;
        public double speedup;
        public double efficiency;

        public Row(int processors, double timeBound, double speedup, double efficiency) {
            this.processors = processors;
            this.timeBound = timeBound;
            this.speedup = speedup;
            this.efficiency = efficiency;
        }
    }

    public static List<Row> analyze(long work, long depth, int[] processorList) {
        List<Row> rows = new ArrayList<>();
        for (int p : processorList) {
            double tp = (double) (work - depth) / p + depth;
            double speedup = (double) work / tp;
            double efficiency = (speedup / p) * 100.0;
            rows.add(new Row(p, tp, speedup, efficiency));
        }
        return rows;
    }
}
