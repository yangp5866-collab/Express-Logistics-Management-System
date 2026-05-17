// Subsystem owned by student #2

package util;

import java.util.Scanner;

/**
 * Task 2: New class - console input utility
 * Purpose: simplifies CLI input and improves code reuse
 */
public class InputUtil {
    private static final Scanner scanner = new Scanner(System.in);

    public static String nextLine(String tip) {
        System.out.print(tip);
        return scanner.nextLine().trim();
    }

    public static int nextInt(String tip) {
        System.out.print(tip);
        int num = scanner.nextInt();
        scanner.nextLine();
        return num;
    }
}
