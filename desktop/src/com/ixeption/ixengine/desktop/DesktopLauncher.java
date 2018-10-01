package com.ixeption.ixengine.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ixeption.ixengine.core.IxGameConfig;
import com.ixeption.ixengine.core.IxGameEngine;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.vSyncEnabled = true;
        config.height = (int) IxGameConfig.VIRTUAL_VIEWPORT_HEIGHT;
        config.width = (int) IxGameConfig.VIRTUAL_VIEWPORT_WIDTH;
        config.resizable = true;

        new LwjglApplication(new IxGameEngine(), config);
    }
}
