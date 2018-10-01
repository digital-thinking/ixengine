package com.ixeption.ixengine.core.objects.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.ixeption.ixengine.bloodbrothers.GameStage;
import com.ixeption.ixengine.core.IxGameConfig;
import com.ixeption.ixengine.core.objects.GameObject;
import com.ixeption.ixengine.core.physics.BreakableCallbackAvg;
import com.ixeption.ixengine.core.physics.PhysicEngine;
import net.dermetfan.gdx.physics.box2d.Breakable;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.Root;

/**
 * A PhysicsComponent enables a {@link GameObject} to be handled by the Box2D
 * World All movement should be done across the Body of an PhysicComponent.
 */

@Root(name = "physicComponent")
public class PhysicComponent implements IComponent {

    private static final String tag = PhysicComponent.class.getSimpleName();

    @Attribute(name = "radius")
    private float radius;
    @Attribute(name = "density")
    private float density;
    @Attribute(name = "friction")
    private float friction;
    @Attribute(name = "restitution")
    private float restitution;
    @Attribute(name = "shapeType")
    private String shapeType;

    @Attribute(name = "destructable", required = false)
    private boolean destructable = false;

    @Attribute(name = "destruction_type", required = false)
    private String destructionType = "destroy";

    @Attribute(name = "normal_max_force", required = false)
    private float normal_maxForce = 0.0f;

    @Attribute(name = "tangent_max_force", required = false)
    private float tangent_maxForce = 0.0f;

    @Attribute(name = "type", required = false)
    private String type = "dynamic";

    @ElementArray(name = "vertices", required = false)
    private float[] vertices;

    private PhysicEngine engine;
    private Shape shape;
    private FixtureDef fixture;
    private Body body;

    public PhysicComponent(String shapeType, String physicType, float density, float friction, float restitution) {
        this.density = density;
        this.friction = friction;
        this.restitution = restitution;
        this.type = physicType;

        this.shapeType = shapeType;
        if (shapeType.equals("cycle")) {
            shape = new CircleShape();
        } else if (shapeType.equals("rect") || shapeType.equals("polygon")) {
            shape = new PolygonShape();
        } else Gdx.app.error(tag, "Wrong Shape Type");

        fixture = new FixtureDef();

    }

    @SuppressWarnings("unused")
    private PhysicComponent(@Attribute(name = "shapeType") String shapeType) {
        this.shapeType = shapeType;
        if (shapeType.equals("cycle")) {
            shape = new CircleShape();
        } else if (shapeType.equals("rect") || shapeType.equals("polygon")) {
            shape = new PolygonShape();

        } else Gdx.app.error(tag, "Wrong Shape Type");

        fixture = new FixtureDef();

    }

    public Body getBody() {
        return body;
    }

    @Override
    public void update(float delta, GameObject parent) {
        body.setActive(parent.isVisible());
        parent.setX((body.getPosition().x * IxGameConfig.PHYSICS_SCALING - parent.getOriginX()));
        parent.setY((body.getPosition().y * IxGameConfig.PHYSICS_SCALING - parent.getOriginY()));
        parent.setRotation(MathUtils.radiansToDegrees * body.getAngle());

    }

    @Override
    public void initialize(final GameObject parent, GameStage gameStage) {
        this.engine = gameStage.getPhysicEngine();
        BodyDef bodyDefinition = new BodyDef();
        if (type.equals("dynamic")) bodyDefinition.type = BodyType.DynamicBody;
        else if (type.equals("static")) {
            bodyDefinition.type = BodyType.StaticBody;
        } else bodyDefinition.type = BodyType.KinematicBody;

        bodyDefinition.position.set(parent.getX() / IxGameConfig.PHYSICS_SCALING + parent.getWidth() / (2 * IxGameConfig.PHYSICS_SCALING), parent.getY() / IxGameConfig.PHYSICS_SCALING + parent.getHeight() / (2 * IxGameConfig.PHYSICS_SCALING));
        bodyDefinition.angle = MathUtils.degreesToRadians * parent.getRotation();
        bodyDefinition.gravityScale = 01.0f;

        body = engine.getWorld().createBody(bodyDefinition);


        if (shapeType.equals("rect")) {
            ((PolygonShape) shape).setAsBox(parent.getWidth() / (2 * IxGameConfig.PHYSICS_SCALING), parent.getHeight() / (2 * IxGameConfig.PHYSICS_SCALING));
        }
        if (shapeType.equals("polygon")) {

            ((PolygonShape) shape).set(vertices);
        }

        shape.setRadius(radius);
        fixture.shape = shape;
        fixture.density = density;
        fixture.friction = friction;
        fixture.restitution = restitution;
        body.createFixture(fixture);
        shape.dispose();

        if (destructable) {

            if (normal_maxForce == 0f || tangent_maxForce == 0f) {
                float resistance = body.getMass() * 100;
                Gdx.app.debug(tag, "Zero force given, auto calculated maxForce: " + resistance);

                normal_maxForce = resistance;
                tangent_maxForce = resistance;

            }
            Breakable breakable = new Breakable(normal_maxForce, tangent_maxForce);
            body.setUserData(breakable);
            BreakableCallbackAvg.BreakableType breakType = BreakableCallbackAvg.BreakableType.NONE;

            if (destructionType.equals("destroy")) {
                breakType = BreakableCallbackAvg.BreakableType.DESTROY;
            }

            BreakableCallbackAvg callback = new BreakableCallbackAvg(parent, this, breakType);
            breakable.setCallback(callback);

        }

    }

    public void setDestructable(boolean destructable) {
        this.destructable = destructable;
    }

    public void setDestructionType(String destructionType) {
        this.destructionType = destructionType;
    }

    public void setMaxForce(float maxForce) {
        this.normal_maxForce = maxForce;
        this.tangent_maxForce = maxForce;

    }


    @Override
    public void destroy(GameObject parent) {
        engine.getWorld().destroyBody(body);

    }


}
