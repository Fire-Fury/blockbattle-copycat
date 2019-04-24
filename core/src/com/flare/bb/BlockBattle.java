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

public class BlockBattle extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	private BitmapFont font;
	private Sprite sprite;

	private static final Color C_STICK = Color.CYAN;
	private static final Color C_L1 = Color.BLUE;
	private static final Color C_L2 = Color.ORANGE;
	private static final Color C_S1 = Color.RED;
	private static final Color C_S2 = Color.GREEN;
	private static final Color C_SQUARE = Color.YELLOW;
	private static final Color C_PYRAMID = Color.PURPLE;




	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("android/assets/colors.jpg");
		sprite = new Sprite(img, 0,0, 1920, 1080);
		sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		font = new BitmapFont();
		font.setColor(Color.BLUE);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		{ //FUNCTIONLESS BRACKETS. They're here for organizational purposes.

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
