package com.ixeption.ixengine.core.screen;


import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.assets.AssetManager;

/**
 * Created by Chris on 18.10.2014.
 */
public interface EngineAccessor {

    public AssetManager getAssetManager();

    public TweenManager getTweenEngine();

    public IxScreenManager getScreenManager();
}
