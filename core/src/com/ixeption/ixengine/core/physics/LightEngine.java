package com.ixeption.ixengine.core.physics;

import box2dLight.RayHandler;
import com.badlogic.gdx.math.Matrix4;

/**
 * Created by Chris on 18.04.2014.
 */
public class LightEngine {


    private RayHandler rayHandler;

    public LightEngine(PhysicEngine physicEngine) {

        this.rayHandler = new RayHandler(physicEngine.getWorld());
        this.rayHandler.setAmbientLight(0.3f, 0.3f, 0.3f, 1);
        this.rayHandler.setGammaCorrection(true);     // enable or disable gamma correction
        this.rayHandler.useDiffuseLight(true);       // enable or disable diffused lighting
        this.rayHandler.setBlur(true);           // enabled or disable blur
        this.rayHandler.setBlurNum(1);           // set number of gaussian blur passes
        this.rayHandler.setShadows(true);        // enable or disable shadow
        this.rayHandler.setCulling(true);        // enable or disable culling


    }

    public RayHandler getRayHandler() {
        return rayHandler;
    }

    /**
     * renders light with the given matrix
     *
     * @param transformMatrix
     */
    public void draw(Matrix4 transformMatrix) {
        rayHandler.setCombinedMatrix(transformMatrix);
        rayHandler.updateAndRender();
    }


}
