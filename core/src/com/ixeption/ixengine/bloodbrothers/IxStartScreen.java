package com.ixeption.ixengine.bloodbrothers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/**
 * Created by Julian on 30.01.2016.
 */
public class IxStartScreen implements Screen {

    private StartStage startStage_;

    public IxStartScreen(int width, int height) {
        startStage_ = new StartStage(new ExtendViewport(width, height));
        Gdx.input.setInputProcessor(startStage_);

    }

    @Override
    public void show() {
        startStage_.initialize();
    }

    @Override
    public void render(float delta) {
        startStage_.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        startStage_.draw();
    }

    @Override
    public void resize(int width, int height) {
        startStage_.getViewport().update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        startStage_.dispose();
    }
}
