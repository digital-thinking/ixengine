package com.ixeption.ixengine.core.objects.components;

import box2dLight.PointLight;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.ixeption.ixengine.core.IxGameConfig;
import com.ixeption.ixengine.core.objects.GameObject;
import com.ixeption.ixengine.core.physics.LightEngine;
import com.ixeption.ixengine.ixgame.GameStage;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "pointLightComponent")
public class PointLightComponent implements IComponent {

    @Attribute(name = "rays")
    private int rays;
    @Attribute(name = "distance")
    private float distance;
    @Attribute(name = "colorHex")
    private String colorHex;
    @Attribute(name = "xrayLight", required = false)
    private boolean xrayLight;
    @Attribute(name = "staticLight", required = false)
    private boolean staticLight;
    @Attribute(name = "softLight", required = false)
    private boolean softLight = false;
    @Attribute(name = "softLenght", required = false)
    private float softLenght = 2.5f;

    private PointLight pointLight;
    private LightEngine engine;

    @Override
    public void initialize(GameObject parent, GameStage gameStage) {
        this.engine = gameStage.getLightEngine();
        pointLight = new PointLight(engine.getRayHandler(), rays, Color.valueOf(colorHex), distance / IxGameConfig.PHYSICS_SCALING,
                parent.getOriginX(), parent.getOriginY());
        if (!softLight) {
            pointLight.setSoft(false);
        } else {
            pointLight.setSoftnessLength(softLenght);

        }

        if (staticLight)
            pointLight.setStaticLight(true);
        if (xrayLight)
            pointLight.setXray(true);
    }

    @Override
    public void update(float delta, GameObject parent) {
        pointLight.setActive(parent.isVisible());
        Vector2 libgdxVec = new Vector2(parent.getX() + parent.getOriginX(),
                parent.getY() + parent.getOriginY());

        pointLight.setPosition(libgdxVec.scl(1 / IxGameConfig.PHYSICS_SCALING));
    }

    @Override
    public void destroy(GameObject parent) {
        pointLight.remove();

    }

}
