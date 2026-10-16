package smartfactory.co1;

import java.util.ArrayList;
import java.util.List;

public class ZFunction {
    public static int[] calculateZ(String s) {
        int[] z = new int[s.length()];
        int l = 0, r = 0;
        for (int i = 1; i < s.length(); i++) {
            if (i <= r) z[i] = Math.min(r - i + 1, z[i - l]);
            while (i + z[i] < s.length() && s.charAt(z[i]) == s.charAt(i + z[i])) z[i]++;
            if (i + z[i] - 1 > r) { l = i; r = i + z[i] - 1; }
        }
        return z;
    }

    public static List<Integer> search(String text, String pat) {
        List<Integer> ans = new ArrayList<>();
        if (pat == null || pat.isEmpty() || text == null) return ans;
        String s = pat + "\0" + text;
        int[] z = calculateZ(s);
        int m = pat.length();
        for (int i = m + 1; i < s.length(); i++) {
            if (z[i] >= m) ans.add(i - m - 1);
        }
        return ans;
    }
}
