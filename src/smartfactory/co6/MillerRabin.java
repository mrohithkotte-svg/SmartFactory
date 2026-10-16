package smartfactory.co6;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MillerRabin {
    public static class TestResult {
        public boolean isPrime;
        public int s;
        public BigInteger d;
        public List<String> roundLogs = new ArrayList<>();
        public String witnessMessage;

        public TestResult(boolean isPrime, int s, BigInteger d) {
            this.isPrime = isPrime;
            this.s = s;
            this.d = d;
        }
    }

    public static TestResult testPrimality(BigInteger n, int rounds) {
        if (n.compareTo(BigInteger.TWO) < 0) {
            TestResult tr = new TestResult(false, 0, BigInteger.ZERO);
            tr.witnessMessage = "Integers < 2 are not prime.";
            return tr;
        }
        if (n.equals(BigInteger.TWO) || n.equals(BigInteger.valueOf(3))) {
            TestResult tr = new TestResult(true, 0, n);
            tr.witnessMessage = "Small prime number.";
            return tr;
        }
        if (n.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            TestResult tr = new TestResult(false, 1, n.divide(BigInteger.TWO));
            tr.witnessMessage = "Even number greater than 2 is composite.";
            return tr;
        }

        BigInteger d = n.subtract(BigInteger.ONE);
        int s = 0;
        while (d.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            d = d.divide(BigInteger.TWO);
            s++;
        }

        TestResult tr = new TestResult(true, s, d);
        Random rand = new Random(42);
        BigInteger nMinusOne = n.subtract(BigInteger.ONE);
        BigInteger nMinusThree = n.subtract(BigInteger.valueOf(3));

        for (int r = 1; r <= rounds; r++) {
            BigInteger a;
            if (n.compareTo(BigInteger.valueOf(10)) <= 0) {
                a = BigInteger.TWO;
            } else {
                BigInteger randomBig = new BigInteger(n.bitLength(), rand);
                a = randomBig.mod(nMinusThree).add(BigInteger.TWO);
            }

            BigInteger x = a.modPow(d, n);
            if (x.equals(BigInteger.ONE) || x.equals(nMinusOne)) {
                tr.roundLogs.add(String.format("Round %2d: Base a = %-10s -> Passed (x = %s)", r, a, x));
                continue;
            }

            boolean compositeWitness = true;
            for (int j = 1; j < s; j++) {
                x = x.modPow(BigInteger.TWO, n);
                if (x.equals(nMinusOne)) {
                    compositeWitness = false;
                    tr.roundLogs.add(String.format("Round %2d: Base a = %-10s -> Passed via step j=%d (x = N - 1)", r, a, j));
                    break;
                }
            }

            if (compositeWitness) {
                tr.isPrime = false;
                tr.roundLogs.add(String.format("Round %2d: Base a = %-10s -> COMPOSITE WITNESS DETECTED (x != -1)", r, a));
                tr.witnessMessage = "Witness base a = " + a + " proved N is composite.";
                break;
            }
        }

        return tr;
    }
}
