package com.ixeption.ixengine.core.util.tweens;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorTween implements TweenAccessor<Actor> {

    public static final int ALPHA = 1;
    public static final int SCALE = 2;
    public static final int ROTATE = 3;
    public static final int MOVEX = 4;

    @Override
    public int getValues(Actor target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case ALPHA:
                returnValues[0] = target.getColor().a;
                return 1;

            case SCALE:
                returnValues[0] = target.getScaleX();

                return 1;

            case ROTATE:
                returnValues[0] = target.getRotation();
                return 1;

            case MOVEX:
                returnValues[0] = target.getX();

                return 1;

            default:
                return 0;
        }
    }

    @Override
    public void setValues(Actor target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case ALPHA:
                target.setColor(1, 1, 1, newValues[0]);
                break;

            case SCALE:
                target.setScale(newValues[0], newValues[0]);
                break;

            case ROTATE:
                target.setRotation(newValues[0]);
                break;

            case MOVEX:
                target.setX(newValues[0]);

                break;

            default:
                break;
        }
    }

}
