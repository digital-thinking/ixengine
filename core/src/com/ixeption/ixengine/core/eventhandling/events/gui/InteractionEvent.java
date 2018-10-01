package com.ixeption.ixengine.core.eventhandling.events.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.ixeption.ixengine.core.eventhandling.InteractionEventhandler;
import com.ixeption.ixengine.core.gui.interactionMenu.InteractionType;

import java.util.EnumSet;

public class InteractionEvent extends InputEvent {

    private InteractionType selectedInteraction;
    private EnumSet<InteractionType> possibleInteractions;

    /**
     * This event occurs if an Interactive Gameobject is clicked.
     *
     * @param data  contains the String Representation of {@link InteractionType}
     * @param event The {@link InputEvent}
     */

    public InteractionEvent(InputEvent e) {
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

    public InteractionEvent(InteractionType type) {
        selectedInteraction = type;
    }

    public InteractionType getSelectedInteraction() {
        return selectedInteraction;
    }

    public void setPossibleInteractionsFromString(String data) {
        possibleInteractions = EnumSet.noneOf(InteractionType.class);
        if (data.contains("LOOK"))
            possibleInteractions.add(InteractionType.LOOK);
        if (data.contains("TAKE"))
            possibleInteractions.add(InteractionType.TAKE);
        if (data.contains("USE"))
            possibleInteractions.add(InteractionType.USE);
        if (data.contains("SPEAK"))
            possibleInteractions.add(InteractionType.SPEAK);

        if (possibleInteractions.isEmpty()) {
            Gdx.app.log(InteractionEventhandler.tag,
                    "GUI Event - Interaction: No eventdata given, used default");

        }
    }

    /**
     * @return available Interactions as an EnumSet of {@link InteractionType}
     */
    public EnumSet<InteractionType> getPossibleInteractions() {
        return possibleInteractions;
    }

    public void setPossibleInteractions(
            EnumSet<InteractionType> possibleInteractions) {
        this.possibleInteractions = possibleInteractions;
    }

}
