package com.flare.bb;

import com.flare.bb.Math.Vector2d;

/**
 * Tetris Board. -- essentially a 2-d grid of integers representing the location of blocks and their colors.
 * Supports tetris pieces and row clearing. Has an "undo" feature that
 * allows clients to add and remove pieces efficiently. Does not do any drawing
 * or have any idea of pixels. Instead, just represents the abstract 2-d board.
 *
 * Instructions taken from the Stanford CS108 HW 2 Handout.
 */

public class Board {

    private int width;
    private int height;
    private int maxHeight;

    private int[] rowWidth;
    private int[] columnHeight;

    private int[] b_rowWidth;
    private int[] b_columnHeight;

    private int[][] grid;
    private int[][] b_grid;

    private boolean DEBUG = false;
    boolean committed;

    /**
     * Creates an empty board of the given width and height measured in blocks.
     */
    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        rowWidth = new int[height];
        b_rowWidth = new int[height];
        b_columnHeight = new int[width];
        b_columnHeight = new int[width];
        grid = new int[width][height];
        b_grid = new int[width][height];

        committed = true;

        maxHeight = 0;

        for (int x = 0; x < width; x++) {
            columnHeight[x] = 0;
        }

        for (int y = 0; y < height; y++) {
            rowWidth[y] = 0;
        }

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y] = -1;
            }
        }
    }

    /**
     * Returns the width of the board in blocks.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the board in blocks.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the max column height present in the board. For an empty board
     * this is 0.
     */

    private void updateMaxHeight() {
        maxHeight = 0;
        for (int x = 0; x < width; x++) {
            if (maxHeight < columnHeight[x]) {
                maxHeight = columnHeight[x];
            }
        }
    }

    /**
     * Returns the maximum height of the board in blocks.
     */
    public int getMaxHeight() {
        return maxHeight;
    }

    /**
     * Given a piece and an x, returns the y value where the piece would come to
     * rest if it were dropped straight down at that x.
     * Implementation: use the skirt and the col heights to compute this fast --
     * O(skirt length).
     */
    public int dropHeight(Piece piece, int x) {
        if (x < 0 | x + piece.getWidth() > width)
            return -1;
        int ret = 0;
        int[] skirt = piece.getSkirt();
        for (int i = 0; i < skirt.length; i++) {
            if (getColumnHeight(x + i) - skirt[i] > ret)
                ret = getColumnHeight(x + i) - skirt[i];
        }
        return ret; // YOUR CODE HERE
    }

    /**
     * Returns the height of the given column -- i.e. the y value of the highest
     * block + 1. The height is 0 if the column contains no blocks.
     */
    public int getColumnHeight(int x) {
        if (x < 0 | x >= width)
            return -1;
        return columnHeight[x];
    }

    private void updateColumnHeight(int start, int end) {
        for (int x = start; x < end; x++) {
            int currHeight = 0;
            for (int y = height - 1; y >= 0; y--) {
                if (grid[x][y] >= 0) {
                    currHeight = y + 1;
                    break;
                }
            }
            columnHeight[x] = currHeight;
        }
    }

    /**
     * Returns the number of filled blocks in the given row.
     */
    public int getRowWidth(int y) {
        if (y < 0 | y >= height)
            return -1;
        return rowWidth[y];
    }

    private void updateRowWidth(int start, int end) {
        for (int y = start; y < end; y++) {
            int currWidth = 0;
            for (int x = 0; x < width; x++) {
                if (grid[x][y] >= 0)
                    currWidth++;
            }
            rowWidth[y] = currWidth;
        }
    }

    /**
     * Returns true if the given block is filled in the board. Blocks outside of
     * the valid width/height area always return true.
     */
    public int getGrid(int x, int y) {
        if (x >= width || y >= height || x < 0 || y < 0)
            return 10000;
        return grid[x][y];
    }

    public static final int PLACE_OK = 0;
    public static final int PLACE_ROW_FILLED = 1;
    public static final int PLACE_OUT_BOUNDS = 2;
    public static final int PLACE_BAD = 3;

    public int place(Piece piece, int x, int y) {
        // flag !committed problem
        if (!committed)
            throw new RuntimeException("place commit problem");

        int ret = PLACE_OK;
        for (int i = 0; i < width; i++) {
            System.arraycopy(grid[i], 0, b_grid[i], 0, grid[i].length);
        }
        System.arraycopy(rowWidth, 0, b_rowWidth, 0, rowWidth.length);
        System.arraycopy(columnHeight, 0, b_columnHeight, 0, columnHeight.length);

        if (x < 0 || y < 0 || x + piece.getWidth() > width || y + piece.getHeight() > height) {
            committed = false;
            return PLACE_OUT_BOUNDS;
        } else {
            for (Vector2d point : piece.getBody()) {
                if (grid[point.getX() + x][point.getY() + y] >= 0) {
                    ret = PLACE_BAD;
                    break;
                }
            }
        }

        updateGrid(piece, x, y);

        if (ret == PLACE_OK) {
            for (int i = y; i < y + piece.getHeight(); i++) {
                if (rowWidth[i] == width)
                    ret = PLACE_ROW_FILLED;
            }
        }
        committed = false;
        return ret;
    }

    private void updateGrid(Piece piece, int x, int y) {
        for (Vector2d point : piece.getBody()) {
            grid[point.getX() + x][point.getY() + y] = piece.getType();
        }
        updateRowWidth(y, y + piece.getHeight());
        updateColumnHeight(x, x + piece.getWidth());
        if (y + piece.getHeight() > maxHeight) {
            maxHeight = y + piece.getHeight();
        }
    }


    /**
     * Returns true if the given block is filled in the board. Blocks outside of
     * the valid width/height area always return true.
     */
    public int getBlock(int x, int y) {
        if (x >= width || y >= height || x < 0 || y < 0)
            return -1;
        return grid[x][y];
    }

    /**
     * Deletes rows that are filled all the way across, moving things above
     * down. Returns the number of rows cleared.
     */
    public int clearRows() {
        int rowsCleared = 0;

        for (int y = 0; y < maxHeight; y++) {
            boolean clear = true;
            for (int x = 0; x < width; x++) {
                if (grid[x][y] < 0) {
                    clear = false;
                }
            }
            if (clear) {
                rowsCleared++;
                for (int j = y; j < maxHeight - 1; j++) {
                    for (int x = 0; x < width; x++) {
                        grid[x][j] = grid[x][j + 1];
                    }
                    rowWidth[j] =  rowWidth[j + 1];
                }
                for (int x = 0; x < width; x++) {
                    grid[x][maxHeight - 1] = -1;
                    rowWidth[maxHeight - 1] = 0;
                }
                y--;
                maxHeight--;
            }
        }
        updateColumnHeight(0, width);
        sanityCheck();
        committed = false;

        return rowsCleared;
    }

    /**
     * Reverts the board to its state before up to one place and one
     * clearRows(); If the conditions for undo() are not met, such as calling
     * undo() twice in a row, then the second undo() does nothing. See the
     * overview docs.
     */
    public void undo() {
        int[][] tempGrid;
        tempGrid = b_grid;
        b_grid = grid;
        grid = tempGrid;

        int[] tempRowWidth;
        tempRowWidth = b_rowWidth;
        b_rowWidth = rowWidth;
        rowWidth = tempRowWidth;

        int[] tempColumnHeight;
        tempColumnHeight = b_columnHeight;
        b_columnHeight = columnHeight;
        columnHeight = tempColumnHeight;

        updateMaxHeight();

        sanityCheck();
        commit();
    }

    /**
     * Puts the board in the committed state.
     */
    public void commit() {
        committed = true;
    }




    /**
     * Checks the board for internal consistency -- used for debugging.
     */
    public void sanityCheck() {
        if (DEBUG) {
            for (int x = 0; x < width; x++) {
                if (columnHeight[x] > maxHeight || columnHeight[x] > height) {
                    System.out.println("Check columnHeight at: " + x);
                }
            }
            for (int y = 0; y < height; y++) {
                if (rowWidth[y] > width) {
                    System.out.println("Check rowWidth at: " + y);
                }
            }
            System.out.println(this);
            System.out.println("Width:" + width + " Height:" + height + " MaxHeight:" + maxHeight);
        }
    }

    /*
     * Renders the board state as a big String, suitable for printing. This is
     * the sort of print-obj-state utility that can help see complex state
     * change over time. (provided debugging utility)
     */
    public String toString() {
        StringBuilder buff = new StringBuilder();
        for (int y = height - 1; y >= 0; y--) {
            buff.append('|');
            for (int x = 0; x < width; x++) {
                if (getGrid(x, y) >= 0)
                    buff.append(getGrid(x, y));
                else
                    buff.append(' ');
            }
            buff.append("|\n");
        }
        for (int x = 0; x < width + 2; x++)
            buff.append('-');
        return (buff.toString());
    }
}
