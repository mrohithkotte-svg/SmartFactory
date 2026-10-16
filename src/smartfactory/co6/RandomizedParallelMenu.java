package smartfactory.co6;

import smartfactory.util.InputUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomizedParallelMenu {
    public static void display() {
        while (true) {
            InputUtils.header("CO6 - RANDOMIZED AND PARALLEL ALGORITHMS");
            System.out.println("1. Randomized QuickSort (Production Order Prioritization)");
            System.out.println("2. Miller-Rabin Primality Test (Factory Security Key Verification)");
            System.out.println("3. Reservoir Sampling (IoT Sensor Stream Sampling)");
            System.out.println("4. Blelloch Scan (Work-Efficient Parallel Prefix Sum)");
            System.out.println("5. Parallel Reduce (Multi-Threaded Production Aggregation)");
            System.out.println("6. Brent's Theorem Analysis (Parallel Work & Depth Bounds)");
            System.out.println("7. Back to Main Menu");
            System.out.println("0. Back to Main Menu");

            String c = InputUtils.readLine("\nChoice: ").trim();
            if (c.equals("0") || c.equals("7")) return;
            switch (c) {
                case "1" -> demoRandomizedQuickSort();
                case "2" -> demoMillerRabin();
                case "3" -> demoReservoirSampling();
                case "4" -> demoBlellochScan();
                case "5" -> demoParallelReduce();
                case "6" -> demoBrentTheorem();
                default -> InputUtils.pause("Invalid choice.");
            }
        }
    }

    private static void demoRandomizedQuickSort() {
        InputUtils.header("RANDOMIZED QUICKSORT - PRODUCTION ORDER PRIORITIZATION");
        System.out.println("Sorting production orders by priority using randomized pivot selection.\n");

        List<RandomizedQuickSort.ProductionOrder> list = new ArrayList<>();
        list.add(new RandomizedQuickSort.ProductionOrder("ORD-101", 75, "Gearbox CNC Milling"));
        list.add(new RandomizedQuickSort.ProductionOrder("ORD-102", 18, "Emergency Hydraulic Repair"));
        list.add(new RandomizedQuickSort.ProductionOrder("ORD-103", 92, "Batch Fastener Assembly"));
        list.add(new RandomizedQuickSort.ProductionOrder("ORD-104", 45, "Robotic Arm Calibration"));
        list.add(new RandomizedQuickSort.ProductionOrder("ORD-105", 18, "Cooling Line Leakage (Duplicate Priority)"));
        list.add(new RandomizedQuickSort.ProductionOrder("ORD-106", 63, "Welding Fixture Alignment"));
        list.add(new RandomizedQuickSort.ProductionOrder("ORD-107", 5,  "Critical Turbine Blade Polishing"));
        list.add(new RandomizedQuickSort.ProductionOrder("ORD-108", 84, "Press Machine Die Replacement"));
        list.add(new RandomizedQuickSort.ProductionOrder("ORD-109", 45, "Motor Spindle Inspection (Duplicate Priority)"));

        RandomizedQuickSort.ProductionOrder[] arr = list.toArray(new RandomizedQuickSort.ProductionOrder[0]);
        System.out.println("Unsorted Production Orders:");
        System.out.printf("%-10s | %-10s | %s%n", "Order ID", "Priority", "Description");
        System.out.println("-----------------------------------------------------------------");
        for (RandomizedQuickSort.ProductionOrder o : arr) {
            System.out.printf("%-10s | %-10d | %s%n", o.getId(), o.getPriority(), o.getDescription());
        }

        RandomizedQuickSort.sort(arr);

        System.out.println("\nSorted Production Orders (Ascending Priority Value):");
        System.out.println("-----------------------------------------------------------------");
        System.out.printf("%-10s | %-10s | %s%n", "Order ID", "Priority", "Description");
        System.out.println("-----------------------------------------------------------------");
        for (RandomizedQuickSort.ProductionOrder o : arr) {
            System.out.printf("%-10s | %-10d | %s%n", o.getId(), o.getPriority(), o.getDescription());
        }

        boolean sorted = RandomizedQuickSort.isSorted(arr);
        System.out.println("-----------------------------------------------------------------");
        System.out.println("Sort Verification Status : " + (sorted ? "CORRECT (Strictly Non-Decreasing)" : "FAILED"));
        System.out.println("Note: Selecting a random pivot avoids worst-case O(N^2) time on");
        System.out.println("already-sorted inputs, guaranteeing O(N log N) expected runtime.");
        InputUtils.pause();
    }

    private static void demoMillerRabin() {
        InputUtils.header("MILLER-RABIN PROBABILISTIC PRIMALITY TEST");
        System.out.println("Verifying industrial security/encryption keys and prime IDs.\n");
        System.out.println("Select a Test Case or Enter Custom Integer:");
        System.out.println("  1. Small Prime (97)");
        System.out.println("  2. Small Composite (91 = 7 * 13)");
        System.out.println("  3. Carmichael Number (561 = 3 * 11 * 17 - fools Fermat test!)");
        System.out.println("  4. Large Prime: Mersenne Prime 2^31 - 1 (2147483647)");
        System.out.println("  5. Large Cryptographic Prime (1000000007)");
        System.out.println("  6. Large Composite (1000000011 = 3 * 333333337)");
        System.out.println("  7. Enter Custom Integer");

        String c = InputUtils.readLine("Choice: ").trim();
        BigInteger n;
        switch (c) {
            case "1" -> n = BigInteger.valueOf(97);
            case "2" -> n = BigInteger.valueOf(91);
            case "3" -> n = BigInteger.valueOf(561);
            case "4" -> n = BigInteger.valueOf(2147483647L);
            case "5" -> n = BigInteger.valueOf(1000000007L);
            case "6" -> n = BigInteger.valueOf(1000000011L);
            case "7" -> {
                try {
                    n = new BigInteger(InputUtils.readLine("Enter integer to test: ").trim());
                } catch (Exception e) {
                    InputUtils.pause("Invalid integer input.");
                    return;
                }
            }
            default -> {
                InputUtils.pause("Invalid choice.");
                return;
            }
        }

        int rounds = 15;
        System.out.printf("%nRunning Miller-Rabin test on N = %s with k = %d random rounds...%n", n, rounds);

        MillerRabin.TestResult tr = MillerRabin.testPrimality(n, rounds);
        if (tr.witnessMessage != null && tr.roundLogs.isEmpty()) {
            System.out.println("Result: " + (tr.isPrime ? "PRIME" : "COMPOSITE") + " (" + tr.witnessMessage + ")");
            InputUtils.pause();
            return;
        }

        System.out.printf("Decomposition: N - 1 = 2^%d * %s%n", tr.s, tr.d);
        for (String log : tr.roundLogs) {
            System.out.println("  " + log);
        }

        System.out.println("\n------------------------------------------------------------");
        if (tr.isPrime) {
            System.out.println("TEST CONCLUSION: PROBABLY PRIME");
            System.out.printf("Error Probability: <= (1/4)^%d = 9.31 x 10^-10 (Practically zero).%n", rounds);
            System.out.println("Explanation: Miller-Rabin is a Monte Carlo randomized algorithm.");
            System.out.println("Passing k=15 independent rounds provides industrial-grade confidence.");
        } else {
            System.out.println("TEST CONCLUSION: DEFINITELY COMPOSITE");
            System.out.println("Explanation: " + tr.witnessMessage);
        }
        InputUtils.pause();
    }

    private static void demoReservoirSampling() {
        InputUtils.header("RESERVOIR SAMPLING - IOT SENSOR STREAM SAMPLING");
        System.out.println("Problem: Stream of continuous machine observations of unknown");
        System.out.println("length N arrives. Maintain an unbiased sample of size k = 5");
        System.out.println("without storing the entire stream in memory.\n");

        int k = 5;
        String[] sensorStream = {
            "MTR-101 Spindle Temp 42.1 C",
            "MTR-102 Arm Vibration 1.4 mm/s",
            "MTR-103 Hydraulic Pressure 140 bar",
            "MTR-104 Assembly Torque 28.5 Nm",
            "MTR-105 Drill Speed 1450 rpm",
            "MTR-101 Spindle Temp 43.8 C",
            "MTR-204 Motor Vibration 3.2 mm/s",
            "MTR-205 Cutting Coolant Flow 12 L/min",
            "MTR-304 Welding Arc Temp 1250 C",
            "MTR-102 Arm Current Draw 14.2 A",
            "MTR-103 Oil Level 92%",
            "MTR-104 Micro-switch Latency 2 ms",
            "MTR-204 Motor Vibration 4.5 mm/s (ALERT)",
            "MTR-105 Bit Wear Index 0.14",
            "MTR-101 Bearing Acoustic 28 dB",
            "MTR-304 Shield Gas Pressure 4.2 bar",
            "MTR-102 Joint 3 Temperature 54.0 C",
            "MTR-205 Feed Rate 250 mm/min",
            "MTR-103 Cycle Counter 84200 units",
            "MTR-104 Conveyor Speed 0.8 m/s"
        };

        ReservoirSampling.SampleResult res = ReservoirSampling.sample(sensorStream, k, new Random(101));

        System.out.println("Processing Streaming Telemetry Data Points:");
        for (ReservoirSampling.StepLog step : res.stepLogs) {
            if (step.itemIndex <= k) {
                System.out.printf("  Item %2d: [%-35s] -> Put in Reservoir slot %d%n",
                    step.itemIndex, step.item, step.replacedSlot);
            } else if (step.replaced) {
                System.out.printf("  Item %2d: [%-35s] -> Random j=%d < k=5 => REPLACED slot %d%n",
                    step.itemIndex, step.item, step.randomJ, step.replacedSlot);
            } else {
                System.out.printf("  Item %2d: [%-35s] -> Random j=%d >= k=5 => Skipped%n",
                    step.itemIndex, step.item, step.randomJ);
            }
        }

        System.out.println("\n------------------------------------------------------------");
        System.out.println("FINAL REPRESENTATIVE RESERVOIR SAMPLE (Size k = " + k + "):");
        System.out.println("------------------------------------------------------------");
        for (int i = 0; i < k; i++) {
            System.out.printf("  Sample [%d]: %s%n", i + 1, res.finalReservoir[i]);
        }
        System.out.println("------------------------------------------------------------");
        System.out.println("Mathematical Guarantee: Every incoming item in a stream of");
        System.out.println("length N has had an identical uniform probability of k / N");
        System.out.println("(here 5 / " + sensorStream.length + " = 25%) of appearing in the final sample.");
        InputUtils.pause();
    }

    private static void demoBlellochScan() {
        InputUtils.header("BLELLOCH SCAN - WORK-EFFICIENT PARALLEL PREFIX SUM");
        System.out.println("Computing cumulative daily production count using Up-Sweep and Down-Sweep.\n");

        int[] rawInput = {12, 18, 15, 22, 10, 25};
        System.out.print("Raw Daily Production Counts (N = " + rawInput.length + "): ");
        InputUtils.printArray(rawInput, 10);

        BlellochScan.ScanResult res = BlellochScan.scan(rawInput);
        System.out.print("Padded Input Array (Power of 2, M = " + res.paddedInput.length + "): ");
        InputUtils.printArray(res.paddedInput, 10);

        System.out.println("\n1. Up-Sweep (Parallel Reduction Tree) Phase:");
        for (String log : res.upSweepLogs) {
            System.out.println("  " + log);
        }

        System.out.println("\n2. Down-Sweep (Exclusive Prefix Distribution) Phase:");
        for (String log : res.downSweepLogs) {
            System.out.println("  " + log);
        }

        System.out.println("\n------------------------------------------------------------");
        System.out.println("Production Prefix Scan Results:");
        System.out.printf("%-10s | %-12s | %-16s | %-16s%n", "Hour", "Units Produced", "Exclusive Prefix", "Inclusive Total");
        System.out.println("------------------------------------------------------------");
        for (int i = 0; i < rawInput.length; i++) {
            System.out.printf("Hour %-5d | %-14d | %-16d | %-16d%n", i + 1, rawInput[i], res.exclusivePrefixSum[i], res.inclusiveTotal[i]);
        }
        System.out.println("------------------------------------------------------------");
        System.out.println("Verification Check with Sequential Sum:");
        System.out.println("Validation Status: " + (res.isVerified ? "CORRECT (Matches sequential scan 100%)" : "FAILED"));
        System.out.println("Algorithm Complexity: Total Work = O(N) operations, Critical Depth = O(log N).");
        InputUtils.pause();
    }

    private static void demoParallelReduce() {
        InputUtils.header("PARALLEL REDUCE - MULTI-THREADED AGGREGATION");
        System.out.println("Aggregating large factory sensor production logs using ForkJoinPool.\n");

        int dataSize = 500_000;
        int[] data = new int[dataSize];
        Random r = new Random(42);
        for (int i = 0; i < dataSize; i++) {
            data[i] = r.nextInt(20) + 1;
        }

        System.out.println("Dataset Size: " + dataSize + " factory telemetry records");

        ParallelReduce.ReduceResult res = ParallelReduce.compute(data);

        System.out.println("\nAggregation Execution Comparison:");
        System.out.println("------------------------------------------------------------");
        System.out.printf("Sequential Loop Sum : %,15d (Time: %,8d microseconds)%n", res.seqSum, res.durationSeqMicros);
        System.out.printf("Parallel ForkJoin   : %,15d (Time: %,8d microseconds)%n", res.parSum, res.durationParMicros);
        System.out.printf("Worker Threads Used : %d cores%n", res.cores);
        System.out.println("------------------------------------------------------------");
        System.out.println("Verification Status : " + (res.seqSum == res.parSum ? "PASSED (Results match exactly)" : "FAILED"));
        System.out.println("Note: ForkJoinPool divides arrays into balanced subtasks, runs");
        System.out.println("them across CPU cores, and combines partial sums seamlessly.");
        InputUtils.pause();
    }

    private static void demoBrentTheorem() {
        InputUtils.header("BRENT'S THEOREM - PARALLEL WORK & DEPTH BOUNDS");
        System.out.println("Theoretical performance bounds for parallel factory algorithms.\n");
        System.out.println("Brent's Theorem Bound:");
        System.out.println("  T(P) <= (W - D) / P + D");
        System.out.println("Where:");
        System.out.println("  W = Total Work (operations on 1 processor)");
        System.out.println("  D = Critical Path Depth / Span (execution time on infinite processors)");
        System.out.println("  P = Number of Available Processors\n");

        System.out.println("Choose Workload Scenario:");
        System.out.println("  1. Batch Vision Defect Inspection (W = 10,000 ops, D = 40 stages)");
        System.out.println("  2. Distributed Sensor Fusion Tree (W = 4,096 ops, D = 12 steps)");
        System.out.println("  3. Custom Work (W) and Depth (D)");
        String c = InputUtils.readLine("Choice: ").trim();

        long W = 10_000, D = 40;
        if (c.equals("2")) {
            W = 4096; D = 12;
        } else if (c.equals("3")) {
            W = InputUtils.readInt("Enter Total Work W (operations): ", 10000);
            D = InputUtils.readInt("Enter Critical Depth D (steps): ", 40);
        }

        System.out.printf("%nParallel Scalability Analysis for W = %,d ops, Depth D = %,d steps:%n", W, D);
        System.out.println("----------------------------------------------------------------------");
        System.out.printf("%-15s | %-18s | %-14s | %-14s%n", "Processors (P)", "Bound T(P) Steps", "Speedup S(P)", "Efficiency");
        System.out.println("----------------------------------------------------------------------");

        int[] pValues = {1, 2, 4, 8, 16, 32, 64};
        List<BrentsTheorem.Row> rows = BrentsTheorem.analyze(W, D, pValues);
        for (BrentsTheorem.Row row : rows) {
            System.out.printf("%-15d | %-18.1f | %-12.2fx | %6.1f%%%n",
                row.processors, row.timeBound, row.speedup, row.efficiency);
        }
        System.out.println("----------------------------------------------------------------------");
        System.out.println("Key Insights:");
        System.out.println("1. When P is small, execution time scales almost linearly with processors.");
        System.out.println("2. As P -> infinity, execution time T(P) asymptotes to the critical depth D.");
        System.out.println("3. Brent's Theorem provides a theoretical upper bound; actual hardware runtime");
        System.out.println("   will also incur cache contention, thread context-switching and bus overhead.");
        InputUtils.pause();
    }
}
