package com.ixeption.ixengine.core.objects.components;

import box2dLight.ConeLight;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.ixeption.ixengine.bloodbrothers.GameStage;
import com.ixeption.ixengine.core.IxGameConfig;
import com.ixeption.ixengine.core.objects.GameObject;
import com.ixeption.ixengine.core.physics.LightEngine;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "pointLightComponent")
public class ConeLightComponent implements IComponent {

    @Attribute(name = "rays")
    private int rays;
    @Attribute(name = "distance")
    private float distance;
    @Attribute(name = "colorHex")
    private String colorHex;

    @Attribute(name = "coneDegree")
    private float coneDegree;
    @Attribute(name = "directionDegree")
    private float directionDegree;

    @Attribute(name = "parentRotation", required = false)
    private boolean parentRotation = true;

    @Attribute(name = "xrayLight", required = false)
    private boolean xrayLight;
    @Attribute(name = "staticLight", required = false)
    private boolean staticLight;
    @Attribute(name = "softLight", required = false)
    private boolean softLight = true;
    @Attribute(name = "softLenght", required = false)
    private float softLenght = 2.5f;

    private ConeLight coneLight;
    private LightEngine engine;

    @Override
    public void initialize(GameObject parent, GameStage gameStage) {

        engine = gameStage.getLightEngine();
        coneLight = new ConeLight(engine.getRayHandler(), rays, Color.valueOf(colorHex), distance / IxGameConfig.PHYSICS_SCALING,
                parent.getOriginX(), parent.getOriginY(), directionDegree,
                coneDegree);
        if (!softLight) {
            coneLight.setSoft(false);
        } else {
            coneLight.setSoftnessLength(softLenght);

        }
        if (staticLight)
            coneLight.setStaticLight(true);
        if (xrayLight)
            coneLight.setXray(true);
    }

    @Override
    public void update(float delta, GameObject parent) {
        coneLight.setActive(parent.isVisible());
        Vector2 libgdxVec = new Vector2(parent.getX() + parent.getOriginX(),
                parent.getY() + parent.getOriginY());
        coneLight.setPosition(libgdxVec.scl(1 / IxGameConfig.PHYSICS_SCALING));
        if (parentRotation)
            coneLight.setDirection(parent.getRotation() + directionDegree);


    }

    @Override
    public void destroy(GameObject parent) {
        coneLight.remove();

    }

}
