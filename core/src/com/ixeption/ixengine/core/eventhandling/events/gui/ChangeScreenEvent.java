package com.ixeption.ixengine.core.eventhandling.events.gui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class ChangeScreenEvent extends InputEvent {

    private String options;

    public ChangeScreenEvent(InputEvent e) {
        super.setListenerActor(e.getListenerActor());
        super.setBubbles(e.getBubbles());
        super.setButton(e.getButton());
        super.setCharacter(e.getCharacter());
        super.setKeyCode(e.getKeyCode());
        super.setPointer(e.getPointer());
        super.setRelatedActor(e.getRelatedActor());
        super.setScrollAmount(e.getScrollAmount());
        super.setStage(e.getStage());
        super.setStageX(e.getStageX());
        super.setStageY(e.getStageY());
        super.setTarget(e.getTarget());
        super.setType(e.getType());

    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String attribute) {
        options = attribute;

    }

}
