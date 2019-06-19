package com.flare.bb.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.flare.bb.BlockBattle;
import com.flare.bb.DataStructures.Board;
import com.flare.bb.DataStructures.Piece;
import com.flare.bb.InputHandling.GameScreenInputProcessor;
import com.flare.bb.Math.Vector2d;

import java.util.Random;

public class GameScreen extends FancyScreen {

    protected BlockBattle game;

    // size of the board in blocks
    public static final int WIDTH = 10;
    public static final int HEIGHT = 20;
    // size of each block in pixels
    public static final int SIZE = 35;

    // Extra blocks at the top for pieces to start.
    // If a piece is sticking up into this area
    // when it has landed -- game over!
    public static final int TOP_SPACE = 4;

    // Board data structures
    protected Board board;
    protected Piece[] pieces;

    // The current piece in play or null
    protected Piece currentPiece;
    protected int currentX;
    protected int currentY;
    protected boolean moved; // did the player move the piece

    // The piece we're thinking about playing
    // -- set by computeNewPosition
    // (storing this in ivars is slightly questionable style)
    protected Piece newPiece;
    protected int newX;
    protected int newY;

    // State of the game
    protected boolean gameOn; // true if we are playing
    protected int count; // how many pieces played so far
    protected long startTime; // used to measure elapsed time
    protected Random random; // the random generator for new pieces

    protected int score;
    protected double deltaTime;
    protected double intervalBetweenTicks; //In seconds


    //DEBUG OPTIONS
    private static final boolean TEST_MODE = true;

    public GameScreen(BlockBattle game) {
        this.game = game;
        startGame();
    }

    public void startGame() {
        // cheap way to reset the board state
        board = new Board(WIDTH, HEIGHT + TOP_SPACE);
        count = 0; //Number of blocks dropped
        score = 0; //Score of the game
        gameOn = true;

        if (TEST_MODE)
            random = new Random(12346); // same seq every time
            // 0 for Square
            // 12346 for Pyramid
            //123469 for L1
        else
            random = new Random(); // diff seq each game

        startTime = System.nanoTime();
        intervalBetweenTicks = 1; //initially there should be 2 seconds before every tick
        addPiece();
    }

    public void stopGame() {
        gameOn = false;

    }

    @Override
    public void update() {
        updateTime();
        tick();
        if(deltaTime > intervalBetweenTicks){
            modifyCurrentCoordinates(0, -1);
            deltaTime = 0;
        }
    }

    public void draw(){
        int boardTopRightOriginX = (Gdx.graphics.getWidth()/2) - (WIDTH*SIZE/2);
        int boardTopRightOriginY = (Gdx.graphics.getHeight()/2) - (HEIGHT*SIZE/2);

        Gdx.gl.glClearColor(0.5294f, 0.8078f, 0.9804f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        //Draw the background of the board
        game.shapeRenderer.setColor(Color.BLACK);
        game.shapeRenderer.rect(boardTopRightOriginX, boardTopRightOriginY, WIDTH*SIZE, HEIGHT*SIZE);
        //Draw the board state
        for(int col = 0; col < board.getWidth(); col++){
            for(int row = 0; row < board.getHeight()-3; row++){
                int gridVal = board.getGrid(col, row);
                if(gridVal != -1) {
                    Color c = (gridVal >= 0) && (gridVal <= 6) ? Piece.PIECE_COLORS[gridVal] : Color.BLACK;
                    game.shapeRenderer.setColor(c);
                    game.shapeRenderer.rect(boardTopRightOriginX + (SIZE * col), boardTopRightOriginY + (SIZE * row), SIZE, SIZE);
                    //finish drawing colored blocks.
                }
            }
        }


        game.shapeRenderer.end();
    }

    //Moves the currentPiece down one.
    public void tick(){
        board.undo();
        setCurrent(currentPiece, currentX, currentY);
    }

    private void updateTime(){
        long currentTime = System.nanoTime();
        deltaTime += (currentTime-startTime)/1000000000.0;
        startTime = currentTime;
    }

    private void addPiece(){
        score++;

        // commit things the way they are
        board.commit();
        currentPiece = null;

        Piece p = Piece.getPieces()[random.nextInt(7)];

        // Center it up at the top
        int px = (board.getWidth() - p.getWidth()) / 2;
        int py = board.getHeight() - p.getHeight()-1;

        int result = setCurrent(p, px, py);

        // This probably never happens, since
        // the blocks at the top allow space
        // for new pieces to at least be added.
        if (result > Board.PLACE_ROW_FILLED) {
            stopGame();
        }
    }

    /**
     * Given a piece, tries to install that piece into the board and set it to
     * be the current piece. Does the necessary repaints. If the placement is
     * not possible, then the placement is undone, and the board is not changed.
     * The board should be in the committed state when this is called. Returns
     * the same error code as Board.place().
     */
    public int setCurrent(Piece piece, int x, int y) {
        int result = board.place(piece, x, y);

        if (result <= Board.PLACE_ROW_FILLED) { // SUCCESS
            // repaint the rect where it used to be
            if (currentPiece != null)
                draw();
            currentPiece = piece;
            currentX = x;
            currentY = y;
            // repaint the rect where it is now
            draw();
        } else {
            board.undo();
        }
        return (result);
    }

    public void modifyCurrentCoordinates(int dx, int dy){
        currentX += dx;
        currentY += dy;
    }

    public void modifyDeltaTime(double amount){
        deltaTime += amount;
    }

    public void setDeltaTime(double amt){
        deltaTime = amt;
    }

    public double getIntervalBetweenTicks() {
        return intervalBetweenTicks;
    }

    public void rotateCurrentPiece(){
        currentPiece = currentPiece.fastRotation();
    }

    @Override
    public void show() {
        GameScreenInputProcessor ia = new GameScreenInputProcessor(game, this);
        Gdx.input.setInputProcessor(ia);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}

