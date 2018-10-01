package com.ixeption.ixengine.core.eventhandling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.ixeption.ixengine.core.eventhandling.events.LevelEvent;
import com.ixeption.ixengine.core.objects.GameObject;
import com.ixeption.ixengine.core.objects.GameObjectFactory;
import com.ixeption.ixengine.core.physics.PhysicMaterial;
import com.ixeption.ixengine.ixgame.GameStage;

public class LevelEventHandler implements EventHandler {

    public static final String tag = EventHandler.class.getSimpleName();

    @Override
    public boolean handleEvent(Event e) {
        GameStage stage = (GameStage) e.getStage();
        LevelEvent ev = (LevelEvent) e;
        switch (ev.getLevelEventType()) {
            case CREATE: {

                Vector2 stageCords = stage.screenToStageCoordinates(
                        new Vector2(ev.getStageX(), ev.getStageY()));


                stage.addActor(
                        GameObjectFactory
                                .create("Box", stageCords.x - 25, stageCords.y - 25,
                                        50, 50, 10)
                                .setTexture("test/boxtexture")
                                .setPhysic("rect", "dynamic", PhysicMaterial.GLASS)
                                .setPhysicBreakable(5.0f, "destroy")
                                .finish(stage)
                );
                break;
            }
            case REMOVE: {
                if (ev.getActor() == null) {
                    Vector2 stageCords = stage.screenToStageCoordinates(
                            new Vector2(ev.getStageX(), ev.getStageY()));

                    Actor hittedActor = stage.hit(stageCords.x, stageCords.y, true);
                    if (hittedActor instanceof GameObject) {
                        GameObject hittedGameObject = (GameObject) hittedActor;
                        hittedGameObject.destroy();
                        hittedActor.remove();

                        return true;
                    }


                    Gdx.app.error(tag, "Removing failed: Actor not found");
                    return false;

                } else
                    ev.getActor().remove();
                return true;
            }
        }

        return false;
    }

}
