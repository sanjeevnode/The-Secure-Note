package com.sanjeevnode.thesecurenote.utils;

public class Helper {
    public static String milliSecondsToTime(Long milliSeconds) {
        long seconds = milliSeconds / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        seconds = seconds % 60;
        minutes = minutes % 60;

        return String.format("%02dH:%02dM:%02dS", hours, minutes, seconds);
    }

    public static Long lockTimeInMilliSeconds(int count) {
        long initialTime = 5 * 1000L;
        return initialTime * getFibonacciTerm(count);
    }

    public static int getFibonacciTerm(int n) {
        if (n <= 1) {
            return n;
        }
        return getFibonacciTerm(n - 1) + getFibonacciTerm(n - 2);
    }
}
