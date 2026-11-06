package com.alexandria.view.ascii;

import javafx.scene.paint.Color;

public class AsciiDemoPatterns {
    public static double[][] radialGradient(int cols, int rows) {
        double[][] grid = new double[rows][cols];
        double cx = cols / 2.0;
        double cy = rows / 2.0;
        double maxDist = Math.sqrt(cx * cx + cy * cy);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                double dist = Math.sqrt((c - cx) * (c - cx) + (r - cy) * (r - cy));
                grid[r][c] = 1.0 - Math.min(1.0, dist / maxDist);
            }
        }
        return grid;
    }

    public static Color[][] waterTint(int cols, int rows, double[][] brightnessGrid) {
        Color[][] grid = new Color[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = Color.rgb(20, 60, 90).interpolate(Color.rgb(180, 230, 255), brightnessGrid[r][c]);
            }
        }
        return grid;
    }
}
