package com.flare.bb.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.flare.bb.BlockBattle;
import com.flare.bb.InputHandling.BetterInputProcessor;
import jdk.nashorn.internal.ir.Block;

public class TitleScreen extends FancyScreen {

    BlockBattle game;

    public TitleScreen(BlockBattle game) {
        this.game = game;
    }

    @Override
    public void draw() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.font.draw(game.batch, "Title Screen!", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .75f);
        game.font.draw(game.batch, "Click the circle to win.", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .5f);
        game.font.draw(game.batch, "Press space to play.", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .25f);
        game.batch.end();
    }

    @Override
    public void update() {
        if(((BetterInputProcessor) (Gdx.input.getInputProcessor())).keys[Input.Keys.SPACE]){
            game.setScreen(new GameScreen(game));
        }
        if(((BetterInputProcessor) (Gdx.input.getInputProcessor())).justPressed(Input.Keys.A)){
            System.out.println("A");
        }
    }

    @Override
    public void show(){
        BetterInputProcessor ia = new BetterInputProcessor();
        Gdx.input.setInputProcessor(ia);
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {

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
