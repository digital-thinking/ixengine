package com.ixeption.ixengine.core.controls;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.ixeption.ixengine.core.objects.GameObject;

public class PointAndClickController {
    public static final int IDLE = 0;
    public static final int MOVEING = 1;
    public static final int TALKING = 3;
    public static float WALKSPEED = 100.0f;
    private int state;
    private GameObject player;
    private SequenceAction sequenceAction;

    public void setTarget(GameObject target) {
        player = target;
    }

    public boolean moveTo(Vector2 cords) {
        return moveTo(cords.x, cords.y);
    }

    public boolean moveTo(GameObject dest) {
        return moveTo(dest.getX() + dest.getOriginX(), dest.getY() + dest.getOriginY());

    }

    public boolean moveTo(float x, float y) {
        if (state == IDLE || state == MOVEING) {

            if (sequenceAction != null) {
                sequenceAction.reset();
            }

            MoveToAction moveAction = new MoveToAction();
            float xdist = player.getX() + player.getOriginX() - x;
            float ydist = player.getY() + player.getOriginY() - y;
            float distance = (float) Math.sqrt(xdist * xdist + ydist * ydist);
            moveAction.setDuration(distance / WALKSPEED);
            moveAction.setPosition(x - player.getOriginX(), y - player.getOriginY());

            Action callback = new Action() {

                @Override
                public boolean act(float delta) {
                    //player.getRenderComponent().setAnimation("idle");
                    state = IDLE;
                    return true;
                }
            };


            sequenceAction = new SequenceAction(moveAction, callback);

            //player.getRenderComponent().setAnimation("walk");

            player.addAction(sequenceAction);
            state = MOVEING;
            return true;
        } else if (state == MOVEING) {
            sequenceAction.reset();
            //player.removeAction(sequenceAction);
        }


        return false;

    }


}
