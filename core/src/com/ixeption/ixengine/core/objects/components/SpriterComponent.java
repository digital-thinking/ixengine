package com.ixeption.ixengine.core.objects.components;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.brashmonkey.spriter.*;
import com.ixeption.ixengine.core.objects.GameObject;
import com.ixeption.ixengine.core.physics.PhysicMaterial;
import com.ixeption.ixengine.ixgame.GameStage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Simon on 14.01.2016.
 */
public class SpriterComponent implements IComponent {

    private final Player player;
    protected Map<Timeline.Key.BoneObject, GameObject> gameObjects;
    private Loader<Sprite> loader;
    private LibGdxDrawer drawer;

    public SpriterComponent(Entity entity, LibGdxLoader loader) {
        player = new com.brashmonkey.spriter.Player(entity);
        player.setAnimation(PlayingAnimation.WALK.getAnimationValue());
        this.loader = loader;

    }

    @Override
    public void initialize(GameObject parent, GameStage gameStage) {
        gameObjects = new HashMap<Timeline.Key.BoneObject, GameObject>();
        Iterator<Timeline.Key.BoneObject> boneObjectIterator = player.objectIterator();

        player.setPivot(parent.getOriginX(), parent.getOriginY());
        player.setAngle(parent.getRotation());
        player.setScale(parent.getScaleX());
        player.setPosition(parent.getX(), parent.getY());
        player.update();

        while (boneObjectIterator.hasNext()) {
            GameObject go = new GameObject();
            Timeline.Key.BoneObject next = boneObjectIterator.next();
            Sprite sprite = loader.get(next.ref);

            go.setX(next.position.x);
            go.setY(next.position.y);
            go.setScaleX(next.scale.x);
            go.setScaleY(next.scale.y);
            go.setWidth(sprite.getWidth());
            go.setHeight(sprite.getHeight());
            go.setOriginX(next.pivot.x * go.getWidth());
            go.setOriginY(next.pivot.y * go.getHeight());
            go.setRotation(next.angle);


            SpriterPhysicComponent component = new SpriterPhysicComponent("rect", "static", PhysicMaterial.METAL);
            component.initialize(go, gameStage);
            go.getComponents().add(component);
            gameObjects.put(next, go);
        }
    }

    @Override
    public void update(float delta, GameObject parent) {
        player.setPosition(parent.getX(), parent.getY());
        player.update();

        Iterator<Timeline.Key.BoneObject> boneObjectIterator = player.objectIterator();
        while (boneObjectIterator.hasNext()) {

            Timeline.Key.BoneObject next = boneObjectIterator.next();
            GameObject go = gameObjects.get(next);
            Sprite sprite = loader.get(next.ref);

            float newPivotX = (sprite.getWidth() * next.pivot.x);
            float newX = next.position.x - newPivotX;
            float newPivotY = (sprite.getHeight() * next.pivot.y);
            float newY = next.position.y - newPivotY;

            go.setX(newX);
            go.setY(newY);
            go.setScaleX(next.scale.x);
            go.setScaleY(next.scale.y);
            go.setOriginX(newPivotX);
            go.setOriginY(newPivotY);
            go.setWidth(sprite.getWidth());
            go.setHeight(sprite.getHeight());
            go.setRotation(next.angle);

        }


        for (GameObject go : gameObjects.values())
            go.act(delta);

    }

    @Override
    public void destroy(GameObject parent) {

    }

    public void setPlayingAnimation(PlayingAnimation animation) {
        player.setAnimation(animation.getAnimationValue());
    }

    public void draw(Batch batch, float parentAlpha, GameObject gameObject) {
        Iterator<Timeline.Key.BoneObject> boneObjectIterator = player.objectIterator();
        while (boneObjectIterator.hasNext()) {
            Timeline.Key.BoneObject next = boneObjectIterator.next();
            Sprite sprite = loader.get(next.ref);
            GameObject parent = gameObjects.get(next);
            batch.draw(sprite, parent.getX(), parent.getY(), parent.getOriginX(),
                    parent.getOriginY(), parent.getWidth(), parent.getHeight(),
                    parent.getScaleX(), parent.getScaleY(), parent.getRotation());

        }


    }

    public void flipCharacterX() {
        player.flipX();
    }

    public enum PlayingAnimation {
        IDLE("idle"), WALK("walk"), JUMP("jump");

        public final String value;

        PlayingAnimation(String value) {
            this.value = value;
        }

        public String getAnimationValue() {
            return value;
        }
    }
}
