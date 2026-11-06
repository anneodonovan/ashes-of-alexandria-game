package com.alexandria.view.ascii;

public class AsciiRoomPatterns {
    public static final String[] NAMES = {"hall", "stacks", "garden"};

    public static double[][] forName(String name, int cols, int rows) {
        switch (name) {
            case "hall":
                return hall(cols, rows);
            case "stacks":
                return stacks(cols, rows);
            case "garden":
                return garden(cols, rows);
            default:
                throw new IllegalArgumentException("Unknown room pattern: " + name);
        }
    }

    private static double[][] hall(int cols, int rows) {
        double[][] grid = new double[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                boolean column = (c % 12) < 3;
                grid[r][c] = column ? 0.9 - 0.4 * r / rows : 0.15 + 0.5 * r / rows;
            }
        }
        return grid;
    }

    private static double[][] stacks(int cols, int rows) {
        double[][] grid = new double[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                boolean shelf = (r % 6) < 2;
                boolean book = (c % 4) != 3;
                grid[r][c] = shelf ? (book ? 0.8 : 0.4) : 0.1;
            }
        }
        return grid;
    }

    private static double[][] garden(int cols, int rows) {
        double[][] grid = new double[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                double wave = Math.sin(c * 0.5) * Math.cos(r * 0.7);
                grid[r][c] = 0.5 + 0.4 * wave;
            }
        }
        return grid;
    }
}
