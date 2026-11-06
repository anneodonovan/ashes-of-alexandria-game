package com.alexandria.view.ascii;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AsciiRoomPatterns {
    private interface Cell {
        double at(int row, int col, int rows);
    }

    private static final Map<String, Cell> PATTERNS = new LinkedHashMap<>();

    static {
        PATTERNS.put("hall", (r, c, rows) ->
            (c % 12) < 3 ? 0.9 - 0.4 * r / rows : 0.15 + 0.5 * r / rows);
        PATTERNS.put("stacks", (r, c, rows) ->
            (r % 6) < 2 ? ((c % 4) != 3 ? 0.8 : 0.4) : 0.1);
        PATTERNS.put("garden", (r, c, rows) ->
            0.5 + 0.4 * Math.sin(c * 0.5) * Math.cos(r * 0.7));
    }

    public static final List<String> NAMES = List.copyOf(PATTERNS.keySet());

    public static double[][] forName(String name, int cols, int rows) {
        Cell cell = name == null ? null : PATTERNS.get(name);
        if (cell == null) {
            throw new IllegalArgumentException("Unknown room pattern: " + name);
        }
        if (cols < 0 || rows < 0) {
            throw new IllegalArgumentException("Grid size must not be negative, got " + rows + "x" + cols);
        }

        double[][] grid = new double[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = cell.at(r, c, rows);
            }
        }
        return grid;
    }
}
