package com.ixeption.ixengine.core.objects.components;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ixeption.ixengine.bloodbrothers.GameStage;
import com.ixeption.ixengine.core.eventhandling.EventDispatcher;
import com.ixeption.ixengine.core.eventhandling.events.gui.InteractionEvent;
import com.ixeption.ixengine.core.objects.GameObject;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "interactionComponent")
public class InteractionComponent implements IComponent {

    @Attribute(name = "event")
    private String event;
    @Attribute(name = "eventtrigger")
    private String eventtrigger;
    @Attribute(name = "eventdata")
    private String eventdata;

    @Override
    public void update(float delta, GameObject parent) {


    }

    @Override
    public void initialize(GameObject parent, GameStage gameStage) {
        if (event.equals("interactionMenu")) {
            if (eventtrigger.equals("click")) {

                parent.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {

                        InteractionEvent interactionEvent = new InteractionEvent(
                                event);
                        interactionEvent
                                .setPossibleInteractionsFromString(eventdata);
                        EventDispatcher.getInstance().handleEvent(
                                interactionEvent, x, y);
                    }
                });
            }
        }
    }

    @Override
    public void destroy(GameObject parent) {

    }

}
