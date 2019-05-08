package com.flare.bb.InputHandling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.flare.bb.Math.Vector2d;

public class BetterInputProcessor implements InputProcessor {

    public boolean[] keys;
    public Vector2d touchPoint;

    public BetterInputProcessor(){
        keys = new boolean[65535]; //length is the maximum number an unsigned short can represent
        touchPoint = new Vector2d(-1, -1);
    }

    @Override
    public boolean keyDown(int keycode) {
        keys[keycode] = true;
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        keys[keycode] = false;
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (Gdx.input.justTouched()) {
            touchPoint.setX(Gdx.input.getX());
            touchPoint.setY(Gdx.input.getY());
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touchPoint.setX(-1);
        touchPoint.setY(-1);
        return true;
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
