package com.ixeption.ixengine.core.eventhandling.events;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class LevelEvent extends InputEvent {
    public static final String tag = LevelEvent.class.getSimpleName();

    private final LevelEventType type;
    private String actorName;
    private Actor actor;

    public LevelEvent(LevelEventType type, float x, float y) {
        super.setStageX(x);
        super.setStageY(y);
        this.type = type;
    }

    public void setActorToRemove(Actor actor) {
        this.actor = actor;
    }

    public void setActorToRemove(String name) {
        this.actorName = name;
    }

    public LevelEventType getLevelEventType() {
        return type;
    }

    public String getActorName() {
        return actorName;
    }

    public Actor getActor() {
        return actor;
    }

    public enum LevelEventType {
        CREATE,
        REMOVE,
        MOVE_LEFT,
        MOVE_RIGHT,
        JUMP,
        KICK,
        ACTION
    }

}
