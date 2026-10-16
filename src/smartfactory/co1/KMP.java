package smartfactory.co1;

import java.util.ArrayList;
import java.util.List;

public class KMP {
    public static List<Integer> search(String text, String pat) {
        List<Integer> ans = new ArrayList<>();
        if (pat == null || pat.isEmpty() || text == null) return ans;

        int[] lps = new int[pat.length()];
        for (int i = 1, len = 0; i < pat.length(); ) {
            if (pat.charAt(i) == pat.charAt(len)) {
                lps[i++] = ++len;
            } else if (len > 0) {
                len = lps[len - 1];
            } else {
                lps[i++] = 0;
            }
        }

        for (int i = 0, j = 0; i < text.length(); ) {
            if (text.charAt(i) == pat.charAt(j)) {
                i++; j++;
                if (j == pat.length()) {
                    ans.add(i - j);
                    j = lps[j - 1];
                }
            } else if (j > 0) {
                j = lps[j - 1];
            } else {
                i++;
            }
        }
        return ans;
    }
}
