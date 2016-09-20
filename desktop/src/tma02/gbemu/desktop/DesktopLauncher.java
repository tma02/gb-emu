package tma02.gbemu.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import tma02.gbemu.GBEmu;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 160 * 5;
		config.height = 144 * 5;
		config.resizable = false;
		new LwjglApplication(new GBEmu(), config);
	}
}
