package com.ixeption.ixengine.core.objects.components;

import com.badlogic.gdx.math.MathUtils;
import com.ixeption.ixengine.core.IxGameConfig;
import com.ixeption.ixengine.core.objects.GameObject;
import com.ixeption.ixengine.core.physics.PhysicMaterial;

/**
 * Created by Christian on 30.01.2016.
 */
public class SpriterPhysicComponent extends PhysicComponent {
    public SpriterPhysicComponent(String shapeType, String physicType, PhysicMaterial material) {
        super(shapeType, physicType, material.density, material.friction, material.restitution);
    }

    @Override
    public void update(float delta, GameObject parent) {

        getBody().setTransform(parent.getX() / IxGameConfig.PHYSICS_SCALING + parent.getOriginX() / IxGameConfig.PHYSICS_SCALING,
                parent.getY() / IxGameConfig.PHYSICS_SCALING + parent.getOriginY() / IxGameConfig.PHYSICS_SCALING,
                parent.getRotation() * MathUtils.degreesToRadians);

        /*getBody().setTransform(parent.getX() / IxGameConfig.PHYSICS_SCALING + parent.getWidth() / (2* IxGameConfig.PHYSICS_SCALING),
                parent.getY() / IxGameConfig.PHYSICS_SCALING + parent.getHeight() / (2* IxGameConfig.PHYSICS_SCALING),
                MathUtils.degreesToRadians * parent.getRotation());*/
        // getBody().setTransform(parent.getX(), parent.getY(), parent.getRotation());
    }

//    @Override
//    public void initialize(GameObject parent, GameStage gameStage) {
//
//        PolygonShape shape = new PolygonShape();
//        FixtureDef fixture = new FixtureDef();
//        fixture.friction = PhysicMaterial.GLASS.friction;
//        fixture.restitution = PhysicMaterial.GLASS.restitution;
//        fixture.density = PhysicMaterial.GLASS.density;
//        fixture.shape = shape;
//        BodyDef bodyDefinition = new BodyDef();
//
//
//        bodyDefinition.type = BodyDef.BodyType.KinematicBody;
//        bodyDefinition.gravityScale = 01.0f;
//
//        shape.setAsBox(parent.getWidth() / (2 * IxGameConfig.PHYSICS_SCALING), parent.getHeight() / (2 * IxGameConfig.PHYSICS_SCALING),
//                new Vector2(parent.getOriginX() / IxGameConfig.PHYSICS_SCALING, (parent.getOriginY() / IxGameConfig.PHYSICS_SCALING)), 0);
//        body = gameStage.getPhysicEngine().getWorld().createBody(bodyDefinition);
//
//        bodyDefinition.position.set(parent.getX() / IxGameConfig.PHYSICS_SCALING, parent.getY() / IxGameConfig.PHYSICS_SCALING);
//        bodyDefinition.angle = parent.getRotation() * MathUtils.degreesToRadians;
//
//        body.createFixture(fixture);
//        shape.dispose();
//    }

}
