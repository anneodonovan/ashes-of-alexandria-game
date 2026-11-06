package com.alexandria.view.ascii;

public class AsciiRenderer {
    private static final String RAMP = " .:-=+*#%@";

    public char brightnessToChar(double brightness) {
        if (Double.isNaN(brightness)) {
            return RAMP.charAt(0); // e.g. a 0/0 upstream ratio; render as blank instead of relying on Math.round(NaN) == 0
        }
        double clamped = Math.max(0.0, Math.min(1.0, brightness));
        int index = (int) Math.round(clamped * (RAMP.length() - 1));
        return RAMP.charAt(index);
    }

    public char[][] render(double[][] brightnessGrid) {
        int rows = brightnessGrid.length;
        char[][] out = new char[rows][];
        for (int r = 0; r < rows; r++) {
            int cols = brightnessGrid[r].length;
            out[r] = new char[cols];
            for (int c = 0; c < cols; c++) {
                out[r][c] = brightnessToChar(brightnessGrid[r][c]);
            }
        }
        return out;
    }

    public String renderToString(double[][] brightnessGrid) {
        StringBuilder sb = new StringBuilder();
        for (char[] row : render(brightnessGrid)) {
            sb.append(row).append('\n');
        }
        return sb.toString();
    }
}
