package com.flare.bb.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.flare.bb.BlockBattle;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Blocknite";
		config.width = 540;
		config.height = 960;
		config.resizable = false;
		new LwjglApplication(new BlockBattle(), config);
	}
}
