package com.ixeption.ixengine.core.util;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class CameraHelper {

    /**
     * Returns the Angle in Radians between Point A (x1,y1) and PointB (x2,y2)
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static double getAngleBetweenPoints(float x1, float y1, float x2, float y2) {
        return Math.atan2(y1 - y2, x1 - x2);
    }

    public static double getAngleBetweenActors(Actor a, Actor b) {
        return Math.atan2(a.getY() - b.getY(), a.getX() - b.getX());
    }


}
