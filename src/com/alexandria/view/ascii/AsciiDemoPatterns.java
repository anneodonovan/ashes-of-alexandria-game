package com.alexandria.view.ascii;

import javafx.scene.paint.Color;

public class AsciiDemoPatterns {
    public static Color[][] waterTint(int cols, int rows, double[][] brightnessGrid) {
        if (brightnessGrid.length != rows || (rows > 0 && brightnessGrid[0].length != cols)) {
            throw new IllegalArgumentException(
                "brightnessGrid must be " + rows + "x" + cols + ", got " + brightnessGrid.length
                    + "x" + (brightnessGrid.length > 0 ? brightnessGrid[0].length : 0));
        }

        Color[][] grid = new Color[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = Color.rgb(20, 60, 90).interpolate(Color.rgb(180, 230, 255), brightnessGrid[r][c]);
            }
        }
        return grid;
    }
}
