package com.ixeption.ixengine.ixgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.ixeption.ixengine.core.IxGameConfig;
import com.ixeption.ixengine.core.controls.InputController;

/**
 * Created by Chris on 18.10.2014.
 */
public class IxGameScreen implements Screen {

    private GameStage gameStage;
    private GUIStage guiStage;
    private InputController inputControllerGameStage_;
    private InputMultiplexer inputMultiplexer_;

    public IxGameScreen(int width, int height) {
        guiStage = new GUIStage(new ExtendViewport(width, height));
        gameStage = new GameStage(new ExtendViewport(width, height));


        inputControllerGameStage_ = new InputController(gameStage);
        inputMultiplexer_ = new InputMultiplexer(guiStage, inputControllerGameStage_);
        Gdx.input.setInputProcessor(inputMultiplexer_);
    }

    @Override
    public void render(float delta) {
        if (IxGameConfig.UI_ENABLED) guiStage.act(delta);
        if (IxGameConfig.GAMESTAGE_ACTING_ENABLED) gameStage.act(delta);
        if (IxGameConfig.GAME_RENDERING_ENABLED) gameStage.draw();
        if (IxGameConfig.UI_RENDERING_ENABLED) guiStage.draw();
        if (IxGameConfig.PHYSIC_DEBUG_RENDERING_ENABLED) gameStage.drawWithDebug();
    }

    @Override
    public void resize(int width, int height) {
        gameStage.getViewport().update(width, height);
        guiStage.getViewport().update(width, height);
    }

    @Override
    public void show() {
        gameStage.initialize();
        guiStage.initialize();
        Gdx.input.setInputProcessor(inputMultiplexer_);
    }

    @Override
    public void hide() {
        // If registered inputProcessor is inputMultiplexer, set it null
        // Else it was already changed to a different inputProcessor
        if (Gdx.input.getInputProcessor() == inputMultiplexer_)
            Gdx.input.setInputProcessor(null);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {

    }

}
