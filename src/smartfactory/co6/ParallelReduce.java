package smartfactory.co6;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelReduce {
    public static class SumTask extends RecursiveTask<Long> {
        private static final int THRESHOLD = 25_000;
        private final int[] arr;
        private final int start, end;

        public SumTask(int[] arr, int start, int end) {
            this.arr = arr;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            if (end - start <= THRESHOLD) {
                long sum = 0;
                for (int i = start; i < end; i++) sum += arr[i];
                return sum;
            } else {
                int mid = start + (end - start) / 2;
                SumTask left = new SumTask(arr, start, mid);
                SumTask right = new SumTask(arr, mid, end);
                left.fork();
                long rightResult = right.compute();
                long leftResult = left.join();
                return leftResult + rightResult;
            }
        }
    }

    public static class ReduceResult {
        public long seqSum;
        public long parSum;
        public long durationSeqMicros;
        public long durationParMicros;
        public int cores;

        public ReduceResult(long seqSum, long parSum, long durationSeqMicros, long durationParMicros, int cores) {
            this.seqSum = seqSum;
            this.parSum = parSum;
            this.durationSeqMicros = durationSeqMicros;
            this.durationParMicros = durationParMicros;
            this.cores = cores;
        }
    }

    public static ReduceResult compute(int[] data) {
        // Sequential
        long startSeq = System.nanoTime();
        long seqSum = 0;
        for (int x : data) seqSum += x;
        long durationSeq = (System.nanoTime() - startSeq) / 1_000;

        // Parallel
        ForkJoinPool pool = new ForkJoinPool();
        long startPar = System.nanoTime();
        long parSum = pool.invoke(new SumTask(data, 0, data.length));
        long durationPar = (System.nanoTime() - startPar) / 1_000;
        pool.shutdown();

        return new ReduceResult(seqSum, parSum, durationSeq, durationPar, Runtime.getRuntime().availableProcessors());
    }
}
