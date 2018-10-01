package com.ixeption.ixengine.core.physics;

/**
 * Materials Density, Friction, Restitution
 *
 * @author Chris
 */
public enum PhysicMaterial {
    GLASS(5.4f, 0.9f, 0.4f),
    WOOD(0.13f, 0.6f, 0.603f),
    METAL(8.7f, 0.21f, 0.203f);


    public final float density, friction, restitution;

    private PhysicMaterial(float density, float friction, float restitution) {
        this.density = density;
        this.friction = friction;
        this.restitution = restitution;

    }

}
