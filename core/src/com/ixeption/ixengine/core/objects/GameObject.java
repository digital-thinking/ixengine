package com.ixeption.ixengine.core.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.ixeption.ixengine.core.objects.components.*;
import com.ixeption.ixengine.ixgame.GameStage;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * The Gameobject extends Actor and maps XML Data.
 *
 * @author Chris
 */
@Root(name = "gameobject")
public class GameObject extends Actor {

    public static final String tag = GameObject.class.getSimpleName();
    @Attribute(name = "touchable", required = false)
    public String touchable = "enabled";
    private boolean isPlayer;
    private int layer;
    @ElementList(name = "components")
    private List<IComponent> components;


    public GameObject() {
        this.components = new LinkedList<IComponent>();
    }

    public void initialize(GameStage stage) {
        // set Origin
        this.setOrigin(this.getWidth() / 2, this.getHeight() / 2);

        // Do Thinge like register Listeners etc
        for (IComponent com : components) {
            com.initialize(this, stage);

            if (com instanceof PlayerComponent) {
                isPlayer = true;


            }
        }
        if (touchable != null)
            setTouchable(Touchable.valueOf(touchable));
    }

    // Mapped Components

    @Override
    public void act(float delta) {

        // Update all Components
        for (IComponent component : components) {
            component.update(delta, this);

        }
        for (Action action : this.getActions()) {
            action.act(delta);
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        for (IComponent com : components) {
            if (com instanceof RenderComponent) {
                ((RenderComponent) com).draw(batch, parentAlpha, this);
            }
            if (com instanceof SpriterComponent) {
                ((SpriterComponent) com).draw(batch, parentAlpha, this);
            }

        }
    }

    public List<IComponent> getComponents() {
        return components;
    }

    // Returns the first physic component found in components
    // Returns null if no physic component was defined
    public PhysicComponent getPhysicComponent() {
        List<IComponent> components = getComponents();
        for (IComponent component : components) {
            if (component instanceof PhysicComponent) {
                return (PhysicComponent) component;
            }
        }

        return null;
    }

    // Mapped Getters
    @Override
    @Attribute(name = "name")
    public String getName() {
        return super.getName();
    }

    @Override
    @Attribute(name = "name")
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    @Attribute(name = "height")
    public float getHeight() {
        return super.getHeight();
    }

    @Override
    @Attribute(name = "height")
    public void setHeight(float height) {
        super.setHeight(height);
    }

    @Override
    @Attribute(name = "width")
    public float getWidth() {
        return super.getWidth();
    }

    @Override
    @Attribute(name = "width")
    public void setWidth(float width) {
        super.setWidth(width);
    }

    @Override
    @Attribute(name = "pos_x")
    public float getX() {
        return super.getX();
    }

    @Override
    @Attribute(name = "pos_x")
    public void setX(float x) {
        super.setX(x);
    }

    @Override
    @Attribute(name = "pos_y")
    public float getY() {
        return super.getY();
    }

    @Override
    @Attribute(name = "pos_y")
    public void setY(float y) {
        super.setY(y);
    }

    @Override
    @Attribute(name = "scale_x")
    public float getScaleX() {
        return super.getScaleX();
    }

    @Override
    @Attribute(name = "scale_x")
    public void setScaleX(float scaleX) {
        super.setScaleX(scaleX);
    }

    @Override
    @Attribute(name = "scale_y")
    public float getScaleY() {
        return super.getScaleY();
    }

    @Override
    @Attribute(name = "scale_y")
    public void setScaleY(float scaleY) {
        super.setScaleY(scaleY);
    }

    @Override
    @Attribute(name = "visible")
    public boolean isVisible() {
        return super.isVisible();
    }

    @Override
    @Attribute(name = "visible")
    public void setVisible(boolean visible) {
        super.setVisible(visible);
    }

    @Override
    @Attribute(name = "rotation")
    public float getRotation() {
        return super.getRotation();
    }

    @Override
    @Attribute(name = "rotation")
    public void setRotation(float degrees) {
        super.setRotation(degrees);
    }

    public boolean isPlayer() {

        return isPlayer;
    }

    @Attribute(name = "layer")
    public int getLayer() {
        return layer;
    }

    @Attribute(name = "layer")
    public void setLayer(int layer) {
        this.layer = layer;
    }

    public boolean destroy() {

        Iterator<IComponent> it = components.iterator();
        while (it.hasNext()) {
            it.next().destroy(this);
            it.remove();
        }
        return super.remove();
    }

    public void setAnimation(String string) {
        for (IComponent comp : components) {
            if (comp instanceof RenderComponent) {
                if (((RenderComponent) comp).setAnimation(string))
                    return;
                else {
                    Gdx.app.error(tag, "Cant set Animation: invalid Animation!");
                    return;
                }

            }

        }
        Gdx.app.error(tag, "Cant set Animation: no RenderComponet!");

    }
}