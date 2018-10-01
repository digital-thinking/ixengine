package com.ixeption.ixengine.core.objects;

import com.badlogic.gdx.Gdx;
import com.ixeption.ixengine.bloodbrothers.GameStage;
import com.ixeption.ixengine.core.objects.components.ConeLightComponent;
import com.ixeption.ixengine.core.objects.components.PhysicComponent;
import com.ixeption.ixengine.core.objects.components.PointLightComponent;
import com.ixeption.ixengine.core.objects.components.RenderComponent;
import com.ixeption.ixengine.core.physics.PhysicMaterial;
import net.dermetfan.gdx.physics.box2d.Breakable;


public class UnfinishedGameObject {

    private static final String tag = UnfinishedGameObject.class
            .getSimpleName();

    private final GameObject gameObject;

    private RenderComponent renderComponent;
    private PhysicComponent physicComponent;
    private PointLightComponent pointLightComponent;
    private ConeLightComponent coneLightComponent;

    private Breakable breakable;

    public UnfinishedGameObject(GameObject gO) {
        gameObject = gO;

    }

    /**
     * creates a static RenderComponent with the given TexturePath
     *
     * @param texturePath
     * @return
     */
    public UnfinishedGameObject setTexture(String texturePath) {
        renderComponent = new RenderComponent(texturePath);

        return this;
    }

    /**
     * creates a PhysicComponent
     *
     * @param shape    Rect or Circle
     * @param material Physical Material {@link com.ixeption.ixengine.core.physics.PhysicMaterial}
     * @return
     */
    public UnfinishedGameObject setPhysic(String shape, String physicType, PhysicMaterial material) {
        physicComponent = new PhysicComponent(shape, physicType, material.density,
                material.friction, material.restitution);

        return this;
    }

    /**
     * @param maxForce
     * @param destructionType calllbackAction Destroy/Animation
     * @return
     */

    public UnfinishedGameObject setPhysicBreakable(float maxForce,
                                                   final String destructionType) {
        if (physicComponent == null) {
            Gdx.app.error(tag, "No PhysicComponent first call setPysics Method");
            return this;
        }
        physicComponent.setDestructable(true);
        physicComponent.setDestructionType(destructionType);
        physicComponent.setMaxForce(maxForce);

        return this;

    }

    /**
     * assembles the final GameObject
     *
     * @return
     */
    public GameObject finish(GameStage gameStage) {

        if (renderComponent != null) {
            gameObject.getComponents().add(renderComponent);
        }
        if (physicComponent != null) {
            gameObject.getComponents().add(physicComponent);

        }
        if (pointLightComponent != null) {
            gameObject.getComponents().add(pointLightComponent);
        }
        if (coneLightComponent != null) {
            gameObject.getComponents().add(coneLightComponent);
        }

        // if (renderComponent != null) {
        // gameObject.getComponents().add(renderComponent);
        // renderComponent.prepare();
        // }
        // if (physicComponent != null) {
        // gameObject.getComponents().add(physicComponent);
        // physicComponent.initialize(gameObject);
        // }
        // if (coneLightComponent != null) {
        // gameObject.getComponents().add(coneLightComponent);
        // coneLightComponent.initialize(gameObject);
        //
        // }
        // if (pointLightComponent != null) {
        // gameObject.getComponents().add(pointLightComponent);
        // pointLightComponent.initialize(gameObject);
        // }

        gameObject.initialize(gameStage);

        return gameObject;

    }
}