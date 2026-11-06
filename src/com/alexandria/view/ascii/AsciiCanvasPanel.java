package com.alexandria.view.ascii;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class AsciiCanvasPanel extends Canvas {
    private static final double CELL_SIZE = 10;

    private final AsciiRenderer renderer = new AsciiRenderer();

    public AsciiCanvasPanel(int cols, int rows) {
        super(cols * CELL_SIZE, rows * CELL_SIZE);
    }

    public void draw(double[][] brightnessGrid) {
        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, getWidth(), getHeight());
        gc.setFill(Color.LIMEGREEN);
        gc.setFont(Font.font("Monospaced", CELL_SIZE));

        char[][] chars = renderer.render(brightnessGrid);
        for (int r = 0; r < chars.length; r++) {
            for (int c = 0; c < chars[r].length; c++) {
                gc.fillText(String.valueOf(chars[r][c]), c * CELL_SIZE, (r + 1) * CELL_SIZE);
            }
        }
    }
}
