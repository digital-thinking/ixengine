package com.ixeption.ixengine.bloodbrothers;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Chris on 16.05.2014.
 */
public class GUIStage extends Stage {

    private ShapeRenderer shapeRenderer_;

    public GUIStage(Viewport viewport) {
        super(viewport);
        shapeRenderer_ = new ShapeRenderer();
    }

    public void initialize() {
    }

    @Override
    public void act() {
        super.act();

    }

    @Override
    public void draw() {

//        shapeRenderer_.setProjectionMatrix(getCamera().combined);
//
//        shapeRenderer_.begin(ShapeRenderer.ShapeType.Line);
//        shapeRenderer_.setColor(1,1,1,0.1f);
//
//        shapeRenderer_.rect(0,0,200,100);
//
//        shapeRenderer_.end();

        super.draw();
    }


    @Override
    public void dispose() {
        shapeRenderer_.dispose();

        super.dispose();
    }
}
