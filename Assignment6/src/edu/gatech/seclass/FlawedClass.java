package edu.gatech.seclass;

/**
 * This is a Georgia Tech provided code example for use in assigned
 * private GT repositories. Students and other users of this template
 * code are advised not to share it with other students or to make it
 * available on publicly viewable websites including repositories such
 * as GitHub and GitLab. Such sharing may be investigated as a GT
 * honor code violation. Created for CS6300 Summer 2022.
 *
 * Template provided for the White-Box Testing Assignment. Follow the
 * assignment directions to either implement or provide comments for
 * the appropriate methods.
 */

public class FlawedClass {

    public static void exampleMethod1(int a) {
        // ...
        int x = a / 0; // Example of instruction that makes the method
                       // fail with an ArithmeticException
        // ...
    }

    public static int exampleMethod2(int a, int b) {
        // ...
        return (a + b) / 0; // Example of instruction that makes the
                            // method fail with an ArithmeticException
    }

    public static void exampleMethod3() {
        // NOT POSSIBLE: This method cannot be implemented because
        // <REPLACE WITH REASON> (this is the example format for a
        // method that is not possible) ***/
    }

    public static void flawedMethod1(int y) {
        int a = -100;
        if (y > 0)
            a = 100;
        a = a / y;
    }

    public static void flawedMethod2(int a, int b) {
        int c = 2;
        if (a > b)
            c = a;

        if (b > 50)
            c = 0;

        c = 100 / c;
    }

    public static void flawedMethod3(int a) {
        if (a > 10) {
            if (a > 100) {
                if (a > 1000)
                    a = 10;
                a = a / 0;
            }
        }
    }

    public static int flawedMethod4(int a, int b, int c, int d) {

        int result = 0;
        if (a != 0) {
            if ((b < 0) && ((c > 0) || (d > 0))) {
                result = 1;
            } else {
                result = 2;
            }
        } else {
            result = 3;
        }
        return result;
    }

    public static String[] flawedMethod5() {
        String a[] = new String[7];
        /*
         * public static boolean flawedMethod5(boolean a, boolean b) {
         * int x = 2;
         * int y = 0;
         * if (a)
         * x = y;
         * else
         * x = (2*x + 1);
         * if (b)
         * y = 4;
         * else
         * y -= 5;
         * return ((42/(x+y)) >= 0);
         * }
         */
        //
        // Replace the "?" in column "output" with "T", "F", or "E":
        //
        // | a | b |output|
        // ================
        a[0] = /* | T | T | <T, F, or E> (e.g., "T") */ "T";
        a[1] = /* | T | F | <T, F, or E> (e.g., "T") */ "F";
        a[2] = /* | F | T | <T, F, or E> (e.g., "T") */ "T";
        a[3] = /* | F | F | <T, F, or E> (e.g., "T") */ "E";
        // ================
        //
        // Replace the "?" in the following sentences with "NEVER",
        // "SOMETIMES" or "ALWAYS":
        //
        a[4] = /* Test suites with 100% path coverage */ "ALWAYS";
        /* reveal the fault in this method. */
        a[5] = /* Test suites with 100% branch coverage */ "ALWAYS";
        /* reveal the fault in this method. */
        a[6] = /* Test suites with 100% statement coverage */ "NEVER";
        /* reveal the fault in this method. */
        // ================
        return a;
    }
}
