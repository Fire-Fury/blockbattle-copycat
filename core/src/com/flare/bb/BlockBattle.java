package com.flare.bb;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.TimeUtils;

public class BlockBattle extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	ShapeRenderer sr;
	private BitmapFont font;
	private Sprite sprite;

	private static final Color[] PIECE_COLORS = {Color.CYAN, Color.ORANGE, Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.PURPLE};
	//The color at an index corresponds to the piece in the piece array. ie PIECE_COLORS[Piece.STICK] will by Cyan

	private static Piece[] pieces;
	private static final int BLOCK_SIZE = 25;
	private static Piece piece;
	private static int initx = 200;
	private static int inity = 200;
	private static boolean rot = true;
	private long startTime;


	@Override
	public void create () {
		batch = new SpriteBatch();
		sr = new ShapeRenderer();
		img = new Texture("android/assets/colors.jpg");
		sprite = new Sprite(img, 0,0, 1920, 1080);
		sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		font = new BitmapFont();
		font.setColor(Color.BLUE);

		pieces = Piece.getPieces();
		startTime = TimeUtils.nanoTime();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		{ //FUNCTIONLESS BRACKETS. They're here for organizational purposes.
			sr.begin(ShapeRenderer.ShapeType.Line);
			for(int k = 0; k < pieces.length; k++) {
				sr.setColor(PIECE_COLORS[k]);
				piece = pieces[k];

				for (int i = 0; i < piece.getBody().length; i++) {
					sr.rect(initx + (piece.getBody()[i].getX() * BLOCK_SIZE), inity + (piece.getBody()[i].getY() * BLOCK_SIZE) + (k*BLOCK_SIZE*4), BLOCK_SIZE, BLOCK_SIZE);
				}
			}
			sr.end();
		}
		batch.end();

		if (TimeUtils.timeSinceNanos(startTime) > 750000000) {
			// if time passed since the time you set startTime at is more than 1 second
			for(int i = 0; i < pieces.length; i++){
				pieces[i] = pieces[i].fastRotation();
			}
			//also you can set the new startTime
			//so this block will execute every N seconds as specified in the if statement
			startTime = TimeUtils.nanoTime();
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
