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

public class BlockBattle extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	ShapeRenderer sr;
	private BitmapFont font;
	private Sprite sprite;

	private static final Color C_STICK = Color.CYAN;
	private static final Color C_L1 = Color.BLUE;
	private static final Color C_L2 = Color.ORANGE;
	private static final Color C_S1 = Color.RED;
	private static final Color C_S2 = Color.GREEN;
	private static final Color C_SQUARE = Color.YELLOW;
	private static final Color C_PYRAMID = Color.PURPLE;

	private static Piece[] pieces;
	private static final int BLOCK_SIZE = 20;
	private static Piece stick = pieces[Piece.STICK];


	@Override
	public void create () {
		batch = new SpriteBatch();
		sr = new ShapeRenderer()
		img = new Texture("android/assets/colors.jpg");
		sprite = new Sprite(img, 0,0, 1920, 1080);
		sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		font = new BitmapFont();
		font.setColor(Color.BLUE);

		pieces = Piece.getPieces();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		{ //FUNCTIONLESS BRACKETS. They're here for organizational purposes.
			sr.begin();
			sr.setColor(C_STICK);
			sr.end();
		}
		batch.end();
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
