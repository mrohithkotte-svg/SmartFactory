package smartfactory.co1;

import java.util.ArrayList;
import java.util.List;

public class RabinKarp {
    public static List<Integer> search(String text, String pat) {
        List<Integer> ans = new ArrayList<>();
        if (pat == null || text == null) return ans;
        int n = text.length(), m = pat.length();
        if (m == 0 || m > n) return ans;

        long mod = 1_000_000_007L, base = 256, high = 1, hp = 0, ht = 0;
        for (int i = 0; i < m; i++) {
            hp = (hp * base + pat.charAt(i)) % mod;
            ht = (ht * base + text.charAt(i)) % mod;
            if (i < m - 1) high = (high * base) % mod;
        }

        for (int i = 0; i <= n - m; i++) {
            if (hp == ht && text.regionMatches(i, pat, 0, m)) {
                ans.add(i);
            }
            if (i < n - m) {
                ht = (ht - text.charAt(i) * high % mod + mod) % mod;
                ht = (ht * base + text.charAt(i + m)) % mod;
            }
        }
        return ans;
    }
}
