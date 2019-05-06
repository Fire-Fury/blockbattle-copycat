package com.flare.bb;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {

//    public static Texture background;
//    public static TextureRegion backgroundRegion;
//
//    public static Texture items;
//    public static TextureRegion mainMenu;
//
//    public static BitmapFont font;
//
//    public static Music music;
//    public static Sound jumpSound;

    public static Texture loadTexture (String file) {
        return new Texture(Gdx.files.internal(file));
    }

    //These are all just placeholders which demonstrate usage. copied from super jumper library.

    public static void load () {
//        background = loadTexture("data/background.png");
//        backgroundRegion = new TextureRegion(background, 0, 0, 320, 480);
//
//        items = loadTexture("data/items.png");
//        mainMenu = new TextureRegion(items, 0, 224, 300, 110);
//
//        font = new BitmapFont(Gdx.files.internal("data/font.fnt"), Gdx.files.internal("data/font.png"), false);
//
//        music = Gdx.audio.newMusic(Gdx.files.internal("data/music.mp3"));
//        music.setLooping(true);
//        music.setVolume(0.5f);
//
//        if (Settings.soundEnabled) music.play();
//        jumpSound = Gdx.audio.newSound(Gdx.files.internal("data/jump.wav"));

    }

    public static void playSound (Sound sound) {
        //if (Settings.soundEnabled) sound.play(1);
    }

}
