package com.flare.bb;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.flare.bb.DataStructures.Board;
import com.flare.bb.DataStructures.Piece;

public class TetrisSim {
    private static final Color[] PIECE_COLORS = {Color.CYAN, Color.ORANGE, Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.PURPLE};
    //The color at an index corresponds to the piece in the piece array. ie PIECE_COLORS[Piece.STICK] will by Cyan

    ShapeRenderer sr;

    private static final int BOARD_HEIGHT = 20; //height and width of tetris board.
    private static final int BOARD_WIDTH = 10;

    // Extra blocks at the top for pieces to start.
    // If a piece is sticking up into this area
    // when it has landed -- game over!
    public static final int TOP_SPACE = 4;

    // Board data structures
    protected Board board;
    protected Piece[] pieces;
    private static final int BLOCK_SIZE = 25;


    public void render(){

    }
}
