package com.ixeption.ixengine.core.eventhandling.events.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;

public class StartDialogEvent extends Event {
    public static final String tag = StartDialogEvent.class.getSimpleName();

    public StartDialogEvent(Actor actor, Actor target) {
        Gdx.app.log(
                tag,
                "create Dialog: " + actor.getName() + " to: "
                        + target.getName()
        );
    }

}
