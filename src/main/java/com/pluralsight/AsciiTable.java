package com.pluralsight;

import java.util.List;

public final class AsciiTable {

    private static final String HDR = "\u001B[33m";  // Yellow
    private static final String DIM = "\u001B[2m";   // Dim gray
    private static final String RESET = "\u001B[0m"; // Reset

    private AsciiTable() {} // Prevent instantiation

    public static void print(String title, String[] headers, List<String[]> rows) {
        int[] w = widths(headers, rows);
        String bar = line(w);

        System.out.println("\n" + HDR + title + RESET);
        System.out.println(bar);

        printRow(headers, w, true);  // Header row
        System.out.println(bar);

        for (String[] r : rows) printRow(r, w, false);  // Data rows
        System.out.println(bar);
        System.out.printf(DIM + "%d item(s)%n" + RESET, rows.size());
    }

    private static int[] widths(String[] headers, List<String[]> rows) {
        int cols = headers.length;
        int[] w = new int[cols];
        for (int c = 0; c < cols; c++) w[c] = headers[c].length();
        for (String[] r : rows)
            for (int c = 0; c < cols; c++)
                w[c] = Math.max(w[c], r[c].length());
        return w;
    }


    private static String line(int[] w) {
        StringBuilder sb = new StringBuilder("+");
        for (int width : w) sb.append("-".repeat(width + 2)).append("+");
        return sb.toString();
    }

    private static void printRow(String[] cells, int[] w, boolean isHeader) {
        System.out.print("|");
        for (int c = 0; c < w.length; c++) {
            String format = " %-" + w[c] + "s |";
            if (isHeader) {
                System.out.printf(HDR + format + RESET, cells[c]);
            } else {
                System.out.printf(format, cells[c]);
            }
        }
        System.out.println();
    }
}
