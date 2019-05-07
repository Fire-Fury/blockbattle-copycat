package com.flare.bb.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.TimeUtils;

public abstract class FancyScreen implements Screen {

    public static final int UPS = 60;
    public static final int FPS = 0; // set to 0 for limitless FPS

    long initialTime = System.nanoTime();
    final double timeU = 1000000000 / UPS;
    final double timeF = (FPS == 0 ? 0 : 1000000000 / FPS);
    double deltaU = 0, deltaF = 0;
    int frames = 0, ticks = 0;
    long timer = System.currentTimeMillis();

    public void update(){

    }

    public void draw(){

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        long currentTime = System.nanoTime();
        deltaU += (currentTime - initialTime) / timeU;
        deltaF += (currentTime - initialTime) / timeF;
        initialTime = currentTime;

        if (deltaU >= 1) {
            update();
            ticks++;
            deltaU--;
        }

        if (deltaF >= 1) {
            draw();
            frames++;
            deltaF--;
        }

        if (System.currentTimeMillis() - timer > 1000) {
            System.out.println(String.format("UPS: %s, FPS: %s", ticks, frames));
            frames = 0;
            ticks = 0;
            timer += 1000;
        }
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

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
