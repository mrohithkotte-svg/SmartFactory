package smartfactory.co2;

import smartfactory.data.FactoryData;
import smartfactory.model.Document;
import smartfactory.util.InputUtils;

import java.util.List;

public class SuffixStructuresMenu {
    public static void display() {
        while (true) {
            InputUtils.header("CO2 - FACTORY DOCUMENT INDEXING");
            System.out.println("1. Build Suffix Array (Doubling)");
            System.out.println("2. Build Suffix Array (SA-IS)");
            System.out.println("3. LCP / Kasai Analysis");
            System.out.println("4. Suffix Automaton Query");
            System.out.println("0. Back to Main Menu");

            String c = InputUtils.readLine("\nChoice: ").trim();
            if (c.equals("0")) return;

            Document d = selectDocument();
            if (d == null) continue;
            String text = d.getContent();

            if (c.equals("1") || c.equals("2") || c.equals("3")) {
                int[] sa = c.equals("2") ? SAIS.build(text) : SuffixArray.buildDoubling(text);
                int[] lcp = LCPKasai.build(text, sa);
                InputUtils.header(c.equals("2") ? "SA-IS INDEX" : "SUFFIX ARRAY INDEX");
                System.out.println("Document: " + d.getId() + " - " + d.getTitle());
                System.out.println("Length  : " + text.length());
                System.out.println("Suffix Array:");
                InputUtils.printArray(sa, 40);
                if (c.equals("3")) {
                    System.out.println("\nLCP / Kasai Array:");
                    InputUtils.printArray(lcp, 40);
                    System.out.println("\nRepeated-prefix candidates:");
                    for (int i = 1; i < lcp.length; i++) {
                        if (lcp[i] > 0) {
                            System.out.println("  LCP=" + lcp[i] + " -> " + text.substring(sa[i], Math.min(text.length(), sa[i] + lcp[i])));
                        }
                    }
                }
                System.out.println("\nFirst sorted suffixes:");
                for (int i = 0; i < Math.min(10, sa.length); i++) {
                    System.out.println("  " + sa[i] + " -> " + text.substring(sa[i]));
                }
                InputUtils.pause();
            } else if (c.equals("4")) {
                String q = InputUtils.readLine("Enter substring query: ");
                SuffixAutomaton sam = new SuffixAutomaton(text);
                int count = sam.count(q);
                InputUtils.header("SUFFIX AUTOMATON RESULT");
                System.out.println("Document : " + d.getId());
                System.out.println("Query    : " + q);
                System.out.println("Exists   : " + (count > 0 ? "YES" : "NO"));
                System.out.println("Count    : " + count);
                InputUtils.pause();
            } else {
                InputUtils.pause("Invalid choice.");
            }
        }
    }

    private static Document selectDocument() {
        List<Document> docs = FactoryData.getDocuments();
        InputUtils.header("SELECT FACTORY DOCUMENT");
        for (int i = 0; i < docs.size(); i++) {
            System.out.println((i + 1) + ". " + docs.get(i).getId() + " - " + docs.get(i).getTitle());
        }
        int x = InputUtils.readInt("Document: ", -1);
        if (x >= 1 && x <= docs.size()) {
            return docs.get(x - 1);
        }
        InputUtils.pause("Invalid document.");
        return null;
    }
}
