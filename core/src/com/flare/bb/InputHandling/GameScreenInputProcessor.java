package com.flare.bb.InputHandling;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.flare.bb.BlockBattle;
import com.flare.bb.Screens.EndScreen;
import com.flare.bb.Screens.GameScreen;

public class GameScreenInputProcessor implements InputProcessor {

    protected BlockBattle game;
    protected GameScreen gameScreen;

    public GameScreenInputProcessor(BlockBattle game, GameScreen gameScreen){
        this.game = game;
        this.gameScreen = gameScreen;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.UP){
            gameScreen.rotateCurrentPiece();
        }
        else if(keycode == Input.Keys.RIGHT){
            gameScreen.modifyCurrentCoordinates(1, 0);
        }
        else if(keycode == Input.Keys.LEFT){
            gameScreen.modifyCurrentCoordinates(-1, 0);
        }
        else if(keycode == Input.Keys.DOWN){
            gameScreen.modifyCurrentCoordinates(0, -1);
            gameScreen.setDeltaTime(0);
        }
        else if(keycode == Input.Keys.SPACE){

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
        game.setScreen(new EndScreen(game));

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
