package com.alexandria.view.ascii;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class AsciiCanvasPanel extends Canvas {
    private static final double DEFAULT_CELL_SIZE = 10;

    private final AsciiRenderer renderer = new AsciiRenderer();
    private final int cols;
    private final int rows;
    private final double cellSize;

    public AsciiCanvasPanel(int cols, int rows) {
        this(cols, rows, DEFAULT_CELL_SIZE);
    }

    public AsciiCanvasPanel(int cols, int rows, double cellSize) {
        super(cols * cellSize, rows * cellSize);
        if (cellSize <= 0) {
            throw new IllegalArgumentException("cellSize must be positive, got " + cellSize);
        }
        this.cols = cols;
        this.rows = rows;
        this.cellSize = cellSize;
    }

    public void draw(double[][] brightnessGrid) {
        draw(brightnessGrid, null);
    }

    public void draw(double[][] brightnessGrid, Color[][] colorGrid) {
        if (brightnessGrid.length != rows || (rows > 0 && brightnessGrid[0].length != cols)) {
            throw new IllegalArgumentException(
                "brightnessGrid must be " + rows + "x" + cols + ", got " + brightnessGrid.length
                    + "x" + (brightnessGrid.length > 0 ? brightnessGrid[0].length : 0));
        }
        if (colorGrid != null && (colorGrid.length != rows || (rows > 0 && colorGrid[0].length != cols))) {
            throw new IllegalArgumentException(
                "colorGrid must be " + rows + "x" + cols + ", got " + colorGrid.length
                    + "x" + (colorGrid.length > 0 ? colorGrid[0].length : 0));
        }

        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, getWidth(), getHeight());
        gc.setFont(Font.font("Monospaced", cellSize));

        char[][] chars = renderer.render(brightnessGrid);
        for (int r = 0; r < chars.length; r++) {
            for (int c = 0; c < chars[r].length; c++) {
                gc.setFill(colorGrid != null ? colorGrid[r][c] : Color.LIMEGREEN);
                gc.fillText(String.valueOf(chars[r][c]), c * cellSize, (r + 1) * cellSize);
            }
        }
    }
}
