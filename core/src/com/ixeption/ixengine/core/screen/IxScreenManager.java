package com.ixeption.ixengine.core.screen;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.ixeption.ixengine.core.IxGameConfig;
import com.ixeption.libgdx.transitions.FadingGame;

/**
 * Created by Chris on 18.10.2014.
 */
public class IxScreenManager extends FadingGame {

    public IxScreenManager() {
        super(new SpriteBatch());
    }

    @Override
    public void create() {
        this.currentScreenFBO = new FrameBuffer(Pixmap.Format.RGBA8888, IxGameConfig.VIRTUAL_VIEWPORT_WIDTH, IxGameConfig.VIRTUAL_VIEWPORT_HEIGHT, false);
        this.nextScreenFBO = new FrameBuffer(Pixmap.Format.RGBA8888, IxGameConfig.VIRTUAL_VIEWPORT_WIDTH, IxGameConfig.VIRTUAL_VIEWPORT_HEIGHT, false);
    }
}
