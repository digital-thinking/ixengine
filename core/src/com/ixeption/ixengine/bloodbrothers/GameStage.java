package com.ixeption.ixengine.bloodbrothers;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.brashmonkey.spriter.Data;
import com.brashmonkey.spriter.Entity;
import com.brashmonkey.spriter.LibGdxLoader;
import com.brashmonkey.spriter.SCMLReader;
import com.ixeption.ixengine.core.IxGameConfig;
import com.ixeption.ixengine.core.level.LevelFactory;
import com.ixeption.ixengine.core.objects.EntityManager;
import com.ixeption.ixengine.core.objects.GameObject;
import com.ixeption.ixengine.core.objects.components.MovementComponent;
import com.ixeption.ixengine.core.objects.components.SpriterComponent;
import com.ixeption.ixengine.core.physics.LightEngine;
import com.ixeption.ixengine.core.physics.PhysicEngine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Chris on 16.05.2014.
 */
public class GameStage extends Stage {

    Matrix4 mvpMatrix;
    private EntityManager entityManager;
    private PhysicEngine physicEngine;
    private LightEngine lightEngine;
    private TweenManager tweenManager;

    public GameStage(Viewport viewport) {
        super(viewport);
        physicEngine = new PhysicEngine(new Vector2(IxGameConfig.PHYSICS_GRAVITY_HORIZONTAL, IxGameConfig.PHYSICS_GRAVITY_VERTICAL));
        physicEngine.setDestructibleBodys(true);
        lightEngine = new LightEngine(physicEngine);
        tweenManager = new TweenManager();
    }

    @Override
    public void act(float delta) {
        if (delta != 0) physicEngine.act();
        super.act(delta);
        tweenManager.update(delta);


    }

    @Override
    public void act() {
        physicEngine.act();
        tweenManager.update(Gdx.graphics.getDeltaTime());
        super.act();
    }

    @Override
    public void draw() {
        super.draw();
        mvpMatrix = getCamera().combined.scl(IxGameConfig.PHYSICS_SCALING, IxGameConfig.PHYSICS_SCALING, 1);
        if (IxGameConfig.LIGHTS_ENABLED) lightEngine.draw(mvpMatrix);
    }

    public void drawWithDebug() {

        physicEngine.drawDebug(mvpMatrix);
    }

    private void importActors(String file) {
        ArrayList<GameObject> list;
        list = LevelFactory.parseActorsInternal(file, this);

        Collections.sort(list, new Comparator<GameObject>() {
            @Override
            public int compare(GameObject o1, GameObject o2) {

                return Integer.compare(o1.getLayer(), o2.getLayer());
            }
        });

        for (GameObject gameObject : list) {
            this.addActor(gameObject);
            if (gameObject.isPlayer()) {
                //
            }

        }

    }

    public void initialize() {

        importActors("levels/test.xml");
        FileHandle scmlHandle = Gdx.files.internal("spriterAnimation/character.scml");
        SCMLReader reader = new SCMLReader(scmlHandle.read());
        Data data = reader.getData();
        LibGdxLoader loader = new LibGdxLoader(data);
        loader.load(scmlHandle.file());
        Entity entity = data.getEntity("character");

        SpriterComponent spriterComponent = new SpriterComponent(entity, loader);
        spriterComponent.setPlayingAnimation(SpriterComponent.PlayingAnimation.WALK);

        GameObject gameObject = new GameObject();
        gameObject.getComponents().add(spriterComponent);
        gameObject.setX(0);
        gameObject.setWidth(80);
        gameObject.setY(0);
        gameObject.setHeight(200);
        gameObject.getComponents().add(new MovementComponent());
        gameObject.initialize(this);

        this.addActor(gameObject);
//        gameObject.getPhysicComponent().getBody().applyLinearImpulse(0.5f, 0, gameObject.getX(), gameObject.getY(), true);

    }


    public PhysicEngine getPhysicEngine() {
        return physicEngine;
    }


    public LightEngine getLightEngine() {
        return lightEngine;
    }


}
