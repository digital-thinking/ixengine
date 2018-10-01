package com.ixeption.ixengine.core.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.ixeption.ixengine.core.objects.GameObject;
import com.ixeption.ixengine.core.objects.components.PhysicComponent;
import com.ixeption.ixengine.core.util.RollingAverage;
import net.dermetfan.gdx.physics.box2d.Breakable;


/**
 * Created by Christian on 31.05.2015.
 */
public class BreakableCallbackAvg implements Breakable.Callback {

    private static final String tag = PhysicComponent.class.getSimpleName();

    private final GameObject parent;
    private final PhysicComponent physicComponent;
    private final BreakableType type;
    private RollingAverage normalImpulseRollingAverage;
    private RollingAverage tangentialImpulseRollingAverage;

    public BreakableCallbackAvg(GameObject parent, PhysicComponent physicComponent, BreakableType type) {
        this.parent = parent;
        this.physicComponent = physicComponent;
        this.type = type;
        normalImpulseRollingAverage = new RollingAverage(10);
        tangentialImpulseRollingAverage = new RollingAverage(10);
    }

    @Override
    public boolean strained(Fixture fixture, Breakable breakable,
                            Contact contact, ContactImpulse impulse,
                            float normalImpulse, float tangentImpulse) {

                   /* for (int j = 0; j < impulse.getNormalImpulses().length; j++) {
                        Gdx.app.debug(tag, "NomalImpulse: " + impulse.getNormalImpulses()[j] + " n" + j);
                    }
                    for (int j = 0; j < impulse.getTangentImpulses().length; j++) {
                        Gdx.app.debug(tag, "TangentImpulse: " + impulse.getTangentImpulses()[j] + " n" + j);
                    }
                    */
        //Filter invalid
        if (normalImpulse > 1000.0f || tangentImpulse > 1000.0f)
            return true;

        normalImpulseRollingAverage.add(normalImpulse);
        tangentialImpulseRollingAverage.add(tangentImpulse);


        Gdx.app.debug(tag, parent.getName() + " strained with NormalImpuse: " + normalImpulse + " TangetImpulse: " + tangentImpulse);
        Gdx.app.debug(tag, parent.getName() + " AVG NormalImpuse: " + normalImpulseRollingAverage.getAverage() + " AVG TangetImpulse: " + tangentialImpulseRollingAverage.getAverage());

        return false;
    }

    @Override
    public boolean strained(Joint joint, Breakable breakable,
                            Vector2 reactionForce, float reactionTorque) {

        return false;
    }

    @Override
    public boolean destroyed(Joint joint, Breakable breakable) {

        return false;
    }

    @Override
    public boolean destroyed(Fixture fixture, Breakable breakable) {

        return false;
    }

    @Override
    public boolean destroyed(Body body, Breakable breakable) {
        Gdx.app.debug(tag, "Critical Collision destroying Body with max normal resistance: " + breakable.getNormalResistance());
        Gdx.app.debug(tag, "Critical Collision destroying Body with max tangent resistance: " + breakable.getTangentResistance());


        parent.getComponents().remove(physicComponent);
        if (type.equals(BreakableType.DESTROY)) {
            parent.destroy();
            return false;
        }

        return true;
    }

    public enum BreakableType {
        NONE,
        DESTROY

    }
}
