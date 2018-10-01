package com.ixeption.ixengine.core.eventhandling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.ixeption.ixengine.core.eventhandling.events.gui.InteractionEvent;
import com.ixeption.ixengine.core.gui.interactionMenu.Interaction;
import com.ixeption.ixengine.core.gui.interactionMenu.InteractionType;
import com.ixeption.ixengine.core.objects.GameObject;
import com.ixeption.ixengine.core.screen.EngineAccessor;

/**
 * Interaction Eventhandler processes Events from User Input An
 * {@link InteractionEvent} happens if an Object is clicked or touched
 *
 * @author Chris
 */
public class InteractionEventhandler implements EventHandler {

    public static final String tag = InteractionEventhandler.class
            .getSimpleName();
    Interaction currentInteraction = null;
    GameObject target;
    Stage currentStage = null;
    private GUIStates currentGuiState;
    private EngineAccessor engineAccessor = (EngineAccessor) Gdx.app.getApplicationListener();

    public InteractionEventhandler() {
        currentGuiState = GUIStates.NONE;
    }

    public GUIStates getCurrentGuiState() {
        return currentGuiState;
    }

    @Override
    public boolean handleEvent(Event e) {

        InteractionEvent event = (InteractionEvent) e;
        if (isDuplicateEvent(event)) {
            event.handle();
            return true;
        }
        // Interaction Menu
        switch (currentGuiState) {
            case NONE:
                if (event.getPossibleInteractions() == null) {
                    Gdx.app.error(tag, "possible Interactions are null");
                    return true;
                }
                if (event.getPossibleInteractions().isEmpty()) {
                    Gdx.app.debug(tag, "No Possible Interaction set");
                    return true;
                }
                currentInteraction = new Interaction(event.getStageX(),
                        event.getStageY(), (GameObject) event.getListenerActor(),
                        event.getPossibleInteractions(), engineAccessor);
                currentStage = e.getStage();
                target = (GameObject) e.getListenerActor();
                currentStage.addActor(currentInteraction);
                currentGuiState = GUIStates.INTERACTION;
                return true;
            // Interaction is active
            case INTERACTION:
                currentStage.getActors().removeValue(currentInteraction, true);
                if (event.getSelectedInteraction() == InteractionType.SPEAK) {
                    // currentStage.addActor(DialogBoxFactory.makeDialogBox(Gamecore.PLAYER_NAME,
                    // target.getName()));

                    return true;
                } else {
                    //TODO Implement other

                    currentGuiState = GUIStates.NONE;
                    currentInteraction = null;
                    return false;
                }

                // Dialog is Active
            case DIALOG:
                if (event.getSelectedInteraction() == InteractionType.CLOSEDIALOG)
                    currentGuiState = GUIStates.NONE;
                return true;
            default:
                currentGuiState = GUIStates.NONE;
                return false;

        }

    }

    private boolean isDuplicateEvent(InteractionEvent event) {

        return false;
    }

    public enum GUIStates {
        INTERACTION, INVENTORY, DIALOG, NONE;

    }
}
