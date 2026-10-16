package smartfactory.util;

import java.util.Scanner;

public class InputUtils {
    private static final Scanner sc = new Scanner(System.in);

    public static String readLine() {
        return sc.nextLine();
    }

    public static String readLine(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }

    public static int readInt(String prompt, int defaultVal) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (Exception e) {
            return defaultVal;
        }
    }

    public static void header(String title) {
        System.out.println("\n============================================================");
        System.out.println("  " + title);
        System.out.println("============================================================");
    }

    public static void pause() {
        System.out.println("\nPress ENTER to continue...");
        sc.nextLine();
    }

    public static void pause(String message) {
        System.out.println("\n" + message);
        pause();
    }

    public static void printArray(int[] a, int max) {
        System.out.print("[");
        for (int i = 0; i < Math.min(a.length, max); i++) {
            if (i > 0) System.out.print(", ");
            System.out.print(a[i]);
        }
        if (a.length > max) System.out.print(", ...");
        System.out.println("]");
    }
}
