package com.flare.bb.InputHandling;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.flare.bb.BlockBattle;
import com.flare.bb.Screens.TitleScreen;

public class EndScreenInputProcessor implements InputProcessor {

    protected BlockBattle game;

    public EndScreenInputProcessor(BlockBattle game){
        this.game = game;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.ENTER){
            game.setScreen(new TitleScreen(game));
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
