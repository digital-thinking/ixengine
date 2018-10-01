package com.ixeption.ixengine.core.objects;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;

public class PLAYER {
    public GameObject playerObject;
    private SequenceAction sequenceAction;
    private boolean jumping;


    public PLAYER(GameObject playerObject) {
        this.playerObject = playerObject;

    }

    public GameObject getPlayerObject() {
        return playerObject;
    }

    public void jump() {

        if (jumping)
            return;


        //playerObject.getRenderComponent().setAnimation("one");
        TemporalAction physicjumpAction = new TemporalAction() {

            @Override
            protected void update(float percent) {
                if (percent < 0.3f) {
                    //playerObject.getPhysicComponent().getBody().applyLinearImpulse(new Vector2(0,80000f), playerObject.getPhysicComponent().getBody().getWorldCenter(), false);
                }

            }
        };
        physicjumpAction.setDuration(1.0f);


        Action completeAction = new Action() {
            @Override
            public boolean act(float delta) {
                //playerObject.getRenderComponent().setAnimation("two");
                jumping = false;
                return true;
            }
        };

        sequenceAction = new SequenceAction(physicjumpAction, completeAction);
        playerObject.addAction(sequenceAction);

        jumping = true;

    }

    public void act(float delta) {

//		Body body = playerObject.getPhysicComponent().getBody();
//		body.setAngularVelocity(0);
//		// Camera
//		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
//			body.applyLinearImpulse(new Vector2(0, 80000f),
//					body.getWorldCenter(), false);
//
//		}
//		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
//			body.applyLinearImpulse(new Vector2(0, -80000f),
//					body.getWorldCenter(), false);
//		}
//		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
//			body.applyLinearImpulse(new Vector2(80000f, 0),
//					body.getWorldCenter(), false);
//
//		}
//		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
//			body.applyLinearImpulse(new Vector2(-80000f, 0),
//					body.getWorldCenter(), false);
//
//		}
//		if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
//			body.applyAngularImpulse(200000f, false);
//		}
//		if (Gdx.input.isKeyPressed(Input.Keys.E)) {
//			body.applyAngularImpulse(-200000f, false);
//		}
//		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
//			jump();
//		}


    }

    public void lookAt(float x, float y, float offset) {
//		Body body = this.getPlayerObject().getPhysicComponent().getBody();
//		float newScreenX = x
//				+ (offset - Gamecore.VIEWPORT_RESOLUTION_WIDTH / 2);
//		float newScreenY = Gamecore.VIEWPORT_RESOLUTION_HEIGHT - y;
//		body.setAngularVelocity(0);
//
//		float angle = (float) CameraHelper.getAngleBetweenPoints(newScreenX,
//				newScreenY, body.getPosition().x, body.getPosition().y);
//		if (angle < 0.5 && angle > -0.5)
//			body.setTransform(body.getPosition(), angle);

    }

}
