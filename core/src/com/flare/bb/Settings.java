package com.flare.bb;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Settings {

    public static boolean soundEnabled = true;
    public static int[] highscores = new int[] {100, 80, 50, 30, 10};
    public final static String file = ".superjumper";

    public static void load () {
        try {

        } catch (Throwable e) {
            //This will result in default settings.
        }
    }

    public static void save () {
        try {

        } catch (Throwable e) {

        }
    }

}
