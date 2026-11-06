package com.alexandria.view.ascii;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class AsciiCanvasPanel extends Canvas {
    private static final double DEFAULT_CELL_SIZE = 10;

    private final AsciiRenderer renderer = new AsciiRenderer();
    private final double cellSize;

    public AsciiCanvasPanel(int cols, int rows) {
        this(cols, rows, DEFAULT_CELL_SIZE);
    }

    public AsciiCanvasPanel(int cols, int rows, double cellSize) {
        super(cols * cellSize, rows * cellSize);
        this.cellSize = cellSize;
    }

    public void draw(double[][] brightnessGrid) {
        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, getWidth(), getHeight());
        gc.setFill(Color.LIMEGREEN);
        gc.setFont(Font.font("Monospaced", cellSize));

        char[][] chars = renderer.render(brightnessGrid);
        for (int r = 0; r < chars.length; r++) {
            for (int c = 0; c < chars[r].length; c++) {
                gc.fillText(String.valueOf(chars[r][c]), c * cellSize, (r + 1) * cellSize);
            }
        }
    }
}
