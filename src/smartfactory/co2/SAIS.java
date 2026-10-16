package smartfactory.co2;

import java.util.Arrays;

public class SAIS {
    public static int[] build(String s) {
        int n = s.length();
        if (n == 0) return new int[0];
        int[] a = new int[n + 1];
        for (int i = 0; i < n; i++) a[i] = s.charAt(i) + 1;
        a[n] = 0;
        int[] full = saIs(a, 256 + 1);
        int[] out = new int[n];
        int j = 0;
        for (int x : full) if (x != n) out[j++] = x;
        return out;
    }

    private static int[] saIs(int[] s, int upper) {
        int n = s.length;
        if (n == 0) return new int[0];
        if (n == 1) return new int[]{0};
        if (n == 2) return s[0] < s[1] ? new int[]{0, 1} : new int[]{1, 0};

        boolean[] ls = new boolean[n];
        ls[n - 1] = true;
        for (int i = n - 2; i >= 0; i--)
            ls[i] = (s[i] == s[i + 1]) ? ls[i + 1] : s[i] < s[i + 1];

        int[] sumL = new int[upper + 1], sumS = new int[upper + 1];
        for (int i = 0; i < n; i++) {
            if (!ls[i]) sumS[s[i]]++;
            else sumL[s[i] + 1]++;
        }
        for (int i = 0; i <= upper; i++) {
            sumS[i] += (i == 0 ? 0 : sumL[i]);
            if (i < upper) sumL[i + 1] += sumS[i];
        }

        int[] lmsMap = new int[n + 1];
        Arrays.fill(lmsMap, -1);
        int m = 0;
        for (int i = 1; i < n; i++)
            if (!ls[i - 1] && ls[i]) lmsMap[i] = m++;

        int[] lms = new int[m];
        for (int i = 0, j = 0; i < n; i++)
            if (lmsMap[i] != -1) lms[j++] = i;

        int[] sa = induceSA(s, ls, sumL, sumS, lms);

        if (m > 0) {
            int[] sortedLms = new int[m];
            for (int i = 0, j = 0; i < n; i++)
                if (lmsMap[sa[i]] != -1) sortedLms[j++] = sa[i];

            int[] recS = new int[m];
            int recUpper = 0;
            recS[lmsMap[sortedLms[0]]] = 0;

            for (int i = 1; i < m; i++) {
                int l = sortedLms[i - 1], r = sortedLms[i];
                int endL = (lmsMap[l] + 1 < m) ? lms[lmsMap[l] + 1] : n;
                int endR = (lmsMap[r] + 1 < m) ? lms[lmsMap[r] + 1] : n;
                boolean same = (endL - l) == (endR - r);
                while (same && l < endL) {
                    if (s[l] != s[r] || ls[l] != ls[r]) same = false;
                    l++; r++;
                }
                if (!same) recUpper++;
                recS[lmsMap[sortedLms[i]]] = recUpper;
            }

            int[] recSA = saIs(recS, recUpper);
            for (int i = 0; i < m; i++) sortedLms[i] = lms[recSA[i]];
            sa = induceSA(s, ls, sumL, sumS, sortedLms);
        }
        return sa;
    }

    private static int[] induceSA(int[] s, boolean[] ls, int[] sumL, int[] sumS, int[] lms) {
        int n = s.length;
        int[] sa = new int[n];
        Arrays.fill(sa, -1);

        int[] buf = sumS.clone();
        for (int d : lms) {
            if (d < n) {
                sa[buf[s[d]]] = d;
                buf[s[d]]++;
            }
        }

        buf = sumL.clone();
        sa[buf[s[n - 1]]] = n - 1;
        buf[s[n - 1]]++;

        for (int i = 0; i < n; i++) {
            int v = sa[i];
            if (v >= 1 && !ls[v - 1]) {
                sa[buf[s[v - 1]]] = v - 1;
                buf[s[v - 1]]++;
            }
        }

        buf = sumL.clone();
        for (int i = n - 1; i >= 0; i--) {
            int v = sa[i];
            if (v >= 1 && ls[v - 1]) {
                buf[s[v - 1] + 1]--;
                sa[buf[s[v - 1] + 1]] = v - 1;
            }
        }
        return sa;
    }
}
