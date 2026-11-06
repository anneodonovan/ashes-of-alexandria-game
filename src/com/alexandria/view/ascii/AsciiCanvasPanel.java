package com.alexandria.view.ascii;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class AsciiCanvasPanel extends Canvas {
    private static final double CELL_SIZE = 10;

    private final AsciiRenderer renderer = new AsciiRenderer();
    private int cols;
    private int rows;
    private Runnable onResize;

    public AsciiCanvasPanel(int initialCols, int initialRows) {
        super(initialCols * CELL_SIZE, initialRows * CELL_SIZE);
        this.cols = initialCols;
        this.rows = initialRows;
    }

    public void setOnResize(Runnable onResize) {
        this.onResize = onResize;
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double minWidth(double height) {
        return 0;
    }

    @Override
    public double minHeight(double width) {
        return 0;
    }

    @Override
    public double maxWidth(double height) {
        return Double.MAX_VALUE;
    }

    @Override
    public double maxHeight(double width) {
        return Double.MAX_VALUE;
    }

    @Override
    public void resize(double width, double height) {
        super.setWidth(width);
        super.setHeight(height);
        cols = Math.max(1, (int) (width / CELL_SIZE));
        rows = Math.max(1, (int) (height / CELL_SIZE));
        if (onResize != null) {
            onResize.run();
        }
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

        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, getWidth(), getHeight());
        gc.setFont(Font.font("Monospaced", CELL_SIZE));

        char[][] chars = renderer.render(brightnessGrid);
        for (int r = 0; r < chars.length; r++) {
            for (int c = 0; c < chars[r].length; c++) {
                gc.setFill(colorGrid != null ? colorGrid[r][c] : Color.LIMEGREEN);
                gc.fillText(String.valueOf(chars[r][c]), c * CELL_SIZE, (r + 1) * CELL_SIZE);
            }
        }
    }
}
