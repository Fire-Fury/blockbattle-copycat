package com.flare.bb;
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
     *
     * <p>
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
     * Returns true if the given block is filled in the board. Blocks outside of
     * the valid width/height area always return true.
     */
    public int getBlock(int x, int y) {
        if (x >= width || y >= height || x < 0 || y < 0)
            return -1;
        return grid[x][y]; // YOUR CODE HERE
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
}
