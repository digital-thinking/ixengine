package com.ixeption.ixengine.core.eventhandling;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.ixeption.ixengine.core.IxGameConfig;
import com.ixeption.ixengine.core.eventhandling.events.gui.ChangeScreenEvent;
import com.ixeption.ixengine.ixgame.IxGameScreen;

public class ChangeScreenEventHandler implements EventHandler {

    @Override
    public boolean handleEvent(Event e) {
        ChangeScreenEvent event = (ChangeScreenEvent) e;
        if (event.getOptions().equals("level_1")) {
            EventDispatcher.getInstance().getEngine().getScreenManager().setScreen(new IxGameScreen(IxGameConfig.VIRTUAL_VIEWPORT_WIDTH, IxGameConfig.VIRTUAL_VIEWPORT_HEIGHT));
            return true;
        }

        return false;
    }

}
