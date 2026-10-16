package smartfactory.co1;

import smartfactory.data.FactoryData;
import smartfactory.util.InputUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StringAlgorithmsMenu {
    public static void display() {
        while (true) {
            InputUtils.header("CO1 - STRING ALGORITHMS");
            System.out.println("1. KMP - Machine/Fault Search");
            System.out.println("2. Z-Function - Repeated Pattern Search");
            System.out.println("3. Rabin-Karp - Production/Inventory Search");
            System.out.println("0. Back to Main Menu");

            String c = InputUtils.readLine("\nChoice: ").trim();
            if (c.equals("0")) return;

            String corpus = FactoryData.buildReportCorpus();
            switch (c) {
                case "1" -> {
                    String p = InputUtils.readLine("Enter machine ID/fault text: ");
                    List<Integer> pos = KMP.search(corpus, p);
                    showResult("KMP", p, pos);
                }
                case "2" -> {
                    String p = InputUtils.readLine("Enter pattern to find: ");
                    List<Integer> pos = ZFunction.search(corpus, p);
                    showResult("Z-Function", p, pos);
                }
                case "3" -> {
                    String p = InputUtils.readLine("Enter production/inventory code or text: ");
                    List<Integer> pos = RabinKarp.search(corpus, p);
                    showResult("Rabin-Karp", p, pos);
                }
                default -> InputUtils.pause("Invalid choice.");
            }
        }
    }

    private static void showResult(String alg, String p, List<Integer> pos) {
        InputUtils.header(alg + " RESULT");
        System.out.println("Query  : " + p);
        System.out.println("Matches: " + pos.size());
        System.out.println("Positions in factory report corpus: " + pos);
        System.out.println("Status : " + (pos.isEmpty() ? "NOT FOUND" : "FOUND"));
        InputUtils.pause();
    }
}
