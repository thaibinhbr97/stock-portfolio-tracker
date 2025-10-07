package utils;

public class DoubleCompare {
    private static final double EPSILON = 1e-6;

    // Returns true if two doubles are approximately equal
    public static boolean approximatelyEqual(double a, double b) {
        return Math.abs(a - b) < EPSILON;
    }

    // Returns -1 if a < b, 0 if approximately equal, 1 if a > b
    public static int compareDoubles(double a, double b) {
        if (approximatelyEqual(a, b)) {
            return 0;
        } else if (a < b) {
            return -1;
        } else {
            return 1;
        }
    }
}
