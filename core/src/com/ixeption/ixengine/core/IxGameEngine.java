package com.ixeption.ixengine.core;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.ixeption.ixengine.core.eventhandling.EventDispatcher;
import com.ixeption.ixengine.core.screen.EngineAccessor;
import com.ixeption.ixengine.core.screen.IxScreenManager;
import com.ixeption.ixengine.ixgame.IxStartScreen;

/**
 * Created by Chris on 16.05.2014.
 */
public class IxGameEngine implements ApplicationListener, EngineAccessor {

    public static IxGameConfig CONFIG;
    private AssetManager assetManager;
    private IxScreenManager screenManager;
    private TweenManager tweenEngine;


    public IxGameEngine() {
        CONFIG = new IxGameConfig();
        tweenEngine = new TweenManager();
    }


    @Override
    public void create() {
        assetManager = new AssetManager();
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        EventDispatcher.getInstance().setEngine(this);
        screenManager = new IxScreenManager();
        screenManager.create();

        // ScreenTransition has no lightning...
//        ScreenTransition transition = new AlphaFadingTransition();
//        screenManager.setTransition(transition, 1.0f);

        screenManager.setScreen(new IxStartScreen(IxGameConfig.VIRTUAL_VIEWPORT_WIDTH, IxGameConfig.VIRTUAL_VIEWPORT_HEIGHT));
//        screenManager.setScreen(new IxGameScreen(IxGameConfig.VIRTUAL_VIEWPORT_WIDTH, IxGameConfig.VIRTUAL_VIEWPORT_HEIGHT));
    }

    @Override
    public void resize(int width, int height) {
        screenManager.resize(width, height);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        screenManager.render();
    }

    @Override
    public void pause() {
        screenManager.pause();

    }

    @Override
    public void resume() {
        screenManager.resume();

    }

    @Override
    public void dispose() {
        screenManager.dispose();
    }

    @Override
    public AssetManager getAssetManager() {
        return assetManager;
    }

    @Override
    public TweenManager getTweenEngine() {
        return tweenEngine;
    }


    @Override
    public IxScreenManager getScreenManager() {
        return screenManager;
    }
}
