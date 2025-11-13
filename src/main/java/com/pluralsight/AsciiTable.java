package com.pluralsight;

import java.util.List;

public final class AsciiTable {

    private static final String YELLOW = "\u001B[33m";
    private static final String CYAN   = "\u001B[36m";
    private static final String GREEN  = "\u001B[32m";
    private static final String DIM    = "\u001B[2m";
    private static final String RESET  = "\u001B[0m";

    private AsciiTable() {}

    public static void print(String title, String[] headers, List<String[]> rows, String filePath) {
        int[] colWidths = calculateColumnWidths(headers, rows);
        String border = createBorder(colWidths);

        System.out.println();
        System.out.println(YELLOW + "==============================================" + RESET);
        System.out.println(YELLOW + " " + title + RESET);
        System.out.println(YELLOW + "==============================================" + RESET);
        System.out.println(border);

        printRow(headers, colWidths, true);   // Header row
        System.out.println(border);

        for (String[] row : rows) {
            printRow(row, colWidths, false);  // Data rows
        }

        System.out.println(border);
        System.out.printf(DIM + "%d item(s)%n" + RESET, rows.size());
        System.out.println(YELLOW + "==============================================" + RESET);
    }

    private static int[] calculateColumnWidths(String[] headers, List<String[]> rows) {
        int cols = headers.length;
        int[] widths = new int[cols];
        for (int c = 0; c < cols; c++) widths[c] = headers[c].length();

        for (String[] r : rows)
            for (int c = 0; c < cols; c++)
                widths[c] = Math.max(widths[c], r[c].length());
        return widths;
    }

    private static String createBorder(int[] widths) {
        StringBuilder sb = new StringBuilder("+");
        for (int w : widths) sb.append("-".repeat(w + 2)).append("+");
        return sb.toString();
    }

    private static void printRow(String[] cells, int[] widths, boolean header) {
        System.out.print("|");
        for (int c = 0; c < widths.length; c++) {
            String cell = cells[c];
            boolean numeric = isNumeric(cell);
            String format = numeric ? " %" + widths[c] + "s |" : " %-" + widths[c] + "s |";

            if (header) {
                System.out.printf(YELLOW + format + RESET, cell);
            } else if (numeric) {
                System.out.printf(GREEN + format + RESET, cell); // numbers in green
            } else {
                System.out.printf(CYAN + format + RESET, cell);  // text in cyan
            }
        }
        System.out.println();
    }

    private static boolean isNumeric(String text) {
        if (text == null || text.isEmpty()) return false;
        String cleaned = text.replace("$", "").replace(",", "").trim();
        if (cleaned.isEmpty()) return false;
        for (char ch : cleaned.toCharArray()) {
            if (!Character.isDigit(ch) && ch != '.' && ch != '-') return false;
        }
        return true;
    }

    public static void print(String orderDetails, String[] strings, List<String[]> rows) {
        
    }
}
