package com.brashmonkey.spriter;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.brashmonkey.spriter.Timeline.Key.BoneObject;

public class LibGdxDrawer extends Drawer<Sprite> {

    Batch batch;
    ShapeRenderer renderer;

    public LibGdxDrawer(Loader<Sprite> loader, Batch batch, ShapeRenderer renderer) {
        super(loader);
        this.batch = batch;
        this.renderer = renderer;
    }

    @Override
    public void setColor(float r, float g, float b, float a) {
        renderer.setColor(r, g, b, a);
    }

    @Override
    public void rectangle(float x, float y, float width, float height) {
        renderer.rect(x, y, width, height);
    }

    @Override
    public void line(float x1, float y1, float x2, float y2) {
        renderer.line(x1, y1, x2, y2);
    }

    @Override
    public void circle(float x, float y, float radius) {
        renderer.circle(x, y, radius);
    }

    @Override
    public void draw(BoneObject boneObject) {
        Sprite sprite = loader.get(boneObject.ref);
        float newPivotX = (sprite.getWidth() * boneObject.pivot.x);
        float newX = boneObject.position.x - newPivotX;
        float newPivotY = (sprite.getHeight() * boneObject.pivot.y);
        float newY = boneObject.position.y - newPivotY;

        sprite.setX(newX);
        sprite.setY(newY);

        sprite.setOrigin(newPivotX, newPivotY);
        sprite.setRotation(boneObject.angle);

        sprite.setColor(1f, 1f, 1f, boneObject.alpha);
        sprite.setScale(boneObject.scale.x, boneObject.scale.y);
        sprite.draw(batch);
    }
}
