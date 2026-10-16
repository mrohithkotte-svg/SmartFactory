package smartfactory.co2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SuffixAutomaton {
    public static class State {
        public Map<Character, Integer> next = new HashMap<>();
        public int link = -1;
        public int len = 0;
        public int occ = 0;
    }

    private final ArrayList<State> st = new ArrayList<>();
    private int last = 0;

    public SuffixAutomaton(String s) {
        st.add(new State());
        for (char c : s.toCharArray()) extend(c);
        propagate();
    }

    private void extend(char c) {
        int cur = st.size();
        st.add(new State());
        st.get(cur).len = st.get(last).len + 1;
        st.get(cur).occ = 1;
        int p = last;
        while (p != -1 && !st.get(p).next.containsKey(c)) {
            st.get(p).next.put(c, cur);
            p = st.get(p).link;
        }
        if (p == -1) {
            st.get(cur).link = 0;
        } else {
            int q = st.get(p).next.get(c);
            if (st.get(p).len + 1 == st.get(q).len) {
                st.get(cur).link = q;
            } else {
                int clone = st.size();
                State x = new State();
                x.len = st.get(p).len + 1;
                x.next.putAll(st.get(q).next);
                x.link = st.get(q).link;
                st.add(x);
                while (p != -1 && st.get(p).next.getOrDefault(c, -1) == q) {
                    st.get(p).next.put(c, clone);
                    p = st.get(p).link;
                }
                st.get(q).link = st.get(cur).link = clone;
            }
        }
        last = cur;
    }

    private void propagate() {
        int max = 0;
        for (State x : st) max = Math.max(max, x.len);
        int[] cnt = new int[max + 1];
        for (State x : st) cnt[x.len]++;
        for (int i = 1; i < cnt.length; i++) cnt[i] += cnt[i - 1];
        int[] order = new int[st.size()];
        for (int i = st.size() - 1; i >= 0; i--) order[--cnt[st.get(i).len]] = i;
        for (int i = order.length - 1; i > 0; i--) {
            int v = order[i], p = st.get(v).link;
            if (p >= 0) st.get(p).occ += st.get(v).occ;
        }
    }

    public int count(String p) {
        int v = 0;
        for (char c : p.toCharArray()) {
            if (!st.get(v).next.containsKey(c)) return 0;
            v = st.get(v).next.get(c);
        }
        return st.get(v).occ;
    }
}
