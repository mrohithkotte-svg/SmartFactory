package smartfactory.co6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlellochScan {
    public static class ScanResult {
        public int[] rawInput;
        public int[] paddedInput;
        public List<String> upSweepLogs = new ArrayList<>();
        public List<String> downSweepLogs = new ArrayList<>();
        public int[] exclusivePrefixSum;
        public int[] inclusiveTotal;
        public boolean isVerified;

        public ScanResult(int[] rawInput, int[] paddedInput) {
            this.rawInput = rawInput;
            this.paddedInput = paddedInput;
        }
    }

    public static ScanResult scan(int[] rawInput) {
        int m = 1;
        while (m < rawInput.length) m *= 2;
        int[] a = new int[m];
        System.arraycopy(rawInput, 0, a, 0, rawInput.length);

        ScanResult result = new ScanResult(rawInput, a.clone());

        int levels = (int) (Math.log(m) / Math.log(2));

        // Up-sweep
        for (int d = 0; d < levels; d++) {
            int step = 1 << (d + 1);
            int half = 1 << d;
            for (int k = 0; k < m; k += step) {
                a[k + step - 1] += a[k + half - 1];
            }
            result.upSweepLogs.add(String.format("Level %d (Step = %2d): %s", d, step, Arrays.toString(a)));
        }

        // Down-sweep
        a[m - 1] = 0;
        result.downSweepLogs.add("Root set to 0      : " + Arrays.toString(a));

        for (int d = levels - 1; d >= 0; d--) {
            int step = 1 << (d + 1);
            int half = 1 << d;
            for (int k = 0; k < m; k += step) {
                int t = a[k + half - 1];
                a[k + half - 1] = a[k + step - 1];
                a[k + step - 1] += t;
            }
            result.downSweepLogs.add(String.format("Level %d (Step = %2d): %s", d, step, Arrays.toString(a)));
        }

        result.exclusivePrefixSum = Arrays.copyOf(a, rawInput.length);
        result.inclusiveTotal = new int[rawInput.length];
        for (int i = 0; i < rawInput.length; i++) {
            result.inclusiveTotal[i] = result.exclusivePrefixSum[i] + rawInput[i];
        }

        int seq = 0;
        boolean matches = true;
        for (int i = 0; i < rawInput.length; i++) {
            if (result.exclusivePrefixSum[i] != seq) matches = false;
            seq += rawInput[i];
        }
        result.isVerified = matches;

        return result;
    }
}
