package com.ixeption.ixengine.core.physics;

import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import net.dermetfan.gdx.physics.box2d.Breakable;

import java.util.HashSet;

/**
 * Created by Chris on 18.04.2014.
 */
public class PhysicEngine {
    private float timestep = 1 / 60f;
    private World world;
    private HashSet<Body> bodySet;
    private Matrix4 box2dTransform;
    private Breakable.Manager contactManager;
    private Box2DDebugRenderer box2DDebugRenderer;

    public PhysicEngine(Vector2 gravity) {
        world = new World(gravity, true);
        bodySet = new HashSet<Body>();
    }

    public PhysicEngine(Vector2 gravity, float timeStep) {
        world = new World(gravity, true);
        bodySet = new HashSet<Body>();
        this.timestep = timeStep;
    }

    public Body createBody(BodyDef bodyDef) {
        Body body = world.createBody(bodyDef);
        bodySet.add(body);
        return body;
    }

    public boolean deleteBody(Body body) {
        if (bodySet.contains(body)) {
            world.destroyBody(body);
            return true;
        } else
            return false;

    }

    public World getWorld() {
        return world;
    }

    public void act() {
        world.step(timestep, 6, 2);
        if (contactManager != null) {
            contactManager.destroy();
        }

    }

    public void setDestructibleBodys(boolean state) {
        if (state) {
            if (contactManager == null) {
                contactManager = new Breakable.Manager();
                world.setContactListener(contactManager);
            }
        } else {
            contactManager = null;
            world.setContactListener(null);
        }


    }

    public void drawDebug(Matrix4 matrix) {
        if (box2DDebugRenderer == null) {
            box2DDebugRenderer = new Box2DDebugRenderer();
        }
        box2DDebugRenderer.render(getWorld(), matrix);
    }

    public void drawDebug(Matrix4 matrix, FrameBuffer fbo) {
        if (box2DDebugRenderer == null) {
            box2DDebugRenderer = new Box2DDebugRenderer();
        }
        //TODO: Render in FBO
        box2DDebugRenderer.render(getWorld(), matrix);
    }


}
