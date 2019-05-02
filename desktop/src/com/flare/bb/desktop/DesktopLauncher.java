package com.flare.bb.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.flare.bb.BlockBattle;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Blocknite";
		config.width = 1920; //540x960 is 1/2 the aspect ratio of most mobile devices that are 1080x1920(reverse of desktop)
		config.height = 1080;
		config.resizable = false;
		new LwjglApplication(new BlockBattle(), config);
	}
}
