package com.flare.bb.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.flare.bb.BlockBattle;
import com.flare.bb.InputHandling.BetterInputProcessor;
import com.flare.bb.Math.Vector2d;

public class GameScreen extends FancyScreen {

    BlockBattle game;

    float circleX = 300;
    float circleY = 150;
    float circleRadius = 50;

    float xSpeed = 4;
    float ySpeed = 3;

    public GameScreen(BlockBattle game) {
        this.game = game;
    }

    @Override
    public void update() {
        //Try to calculate the distance between the touched point and the circle. check if it is less than R
        if(!(((BetterInputProcessor) (Gdx.input.getInputProcessor())).touchPoint.equals(new Vector2d(-1, -1)))) {
            System.out.printf("Touched at: (%d, %d)\n",
                    ((BetterInputProcessor) (Gdx.input.getInputProcessor())).touchPoint.getX(),
                    ((BetterInputProcessor) (Gdx.input.getInputProcessor())).touchPoint.getY());

            int renderY = Gdx.graphics.getHeight() - ((BetterInputProcessor) (Gdx.input.getInputProcessor())).touchPoint.getY();
            if (Vector2.dst(circleX, circleY,
                    ((BetterInputProcessor) (Gdx.input.getInputProcessor())).touchPoint.getX(), renderY) < circleRadius) {
                game.setScreen(new EndScreen(game));
            }
        }

        circleX += xSpeed;
        circleY += ySpeed;

        if (circleX < 0 || circleX > Gdx.graphics.getWidth()) {
            xSpeed *= -1;
        }

        if (circleY < 0 || circleY > Gdx.graphics.getHeight()) {
            ySpeed *= -1;
        }
    }

    public void draw(){

        Gdx.gl.glClearColor(0, 0, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.setColor(0, 1, 0, 1);
        game.shapeRenderer.circle(circleX, circleY, 75);
        game.shapeRenderer.end();

    }

    @Override
    public void show() {
        BetterInputProcessor ia = new BetterInputProcessor();
        Gdx.input.setInputProcessor(ia);
//        Gdx.input.setInputProcessor(new InputAdapter() {
//            @Override
//            public boolean touchDown(int x, int y, int pointer, int button) {
//                int renderY = Gdx.graphics.getHeight() - y;
//                if (Vector2.dst(circleX, circleY, x, renderY) < circleRadius) {
//                    game.setScreen(new EndScreen(game));
//                }
//                return true;
//            }
//        });
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}

