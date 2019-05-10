package com.flare.bb.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.flare.bb.BlockBattle;
import com.flare.bb.InputHandling.BetterInputProcessor;

public class EndScreen extends FancyScreen {

    BlockBattle game;

    public EndScreen(BlockBattle game) {
        this.game = game;
    }

    @Override
    public void update(){
        if(((BetterInputProcessor) (Gdx.input.getInputProcessor())).keys[Input.Keys.ENTER]){
            game.setScreen(new TitleScreen(game));
        }

    }

    @Override
    public void draw() {
        Gdx.gl.glClearColor(.25f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.font.draw(game.batch, "You win!", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .75f);
        game.font.draw(game.batch, "Press enter to restart.", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .25f);
        game.batch.end();
    }

    @Override
    public void show() {
        BetterInputProcessor ia = new BetterInputProcessor();
        Gdx.input.setInputProcessor(ia);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}
