package com.ixeption.ixengine.core.eventhandling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.ixeption.ixengine.core.eventhandling.events.GlobalEvent;
import com.ixeption.ixengine.core.eventhandling.events.LevelEvent;
import com.ixeption.ixengine.core.eventhandling.events.StoryEvent;
import com.ixeption.ixengine.core.eventhandling.events.gui.ChangeScreenEvent;
import com.ixeption.ixengine.core.eventhandling.events.gui.InteractionEvent;
import com.ixeption.ixengine.core.screen.EngineAccessor;

import java.util.ArrayList;

/**
 * The Eventdispatcher delegates Events to Eventhandlers. Thread-safe Singleton
 *
 * @author Chris
 */
public class EventDispatcher {
    public static final int EVENT_GLOBAL = 0;
    public static final int EVENT_LEVEL = 0;
    public static final int EVENT_STORY = 0;
    public static EventDispatcher instance = new EventDispatcher();
    private static String tag = EventDispatcher.class.getSimpleName();
    private static GlobalEventHandler globalEventHandler = new GlobalEventHandler();
    private static LevelEventHandler levelEventHandler = new LevelEventHandler();
    private static StoryEventHandler storyEventHandler = new StoryEventHandler();
    private static InteractionEventhandler interactionEventHandler = new InteractionEventhandler();
    private static ChangeScreenEventHandler changeScreenHandler = new ChangeScreenEventHandler();
    public InteractionEvent lastInteractionEvent;
    private ArrayList<EventListener> listenerList;
    private EngineAccessor engine;

    private EventDispatcher() {
    }

    /**
     * @return Instance of Eventdispatcher (thread-safe)
     */
    public static EventDispatcher getInstance() {
        return instance;
    }


    public void registerListener(EventListener listener) {
        if (listenerList == null)
            listenerList = new ArrayList<EventListener>();
        listenerList.add(listener);
    }

    public void unregisterListener(EventListener listener) {
        if (listenerList != null) {
            if (listenerList.contains(listener)) {
                listenerList.remove(listener);
                Gdx.app.debug(tag, "Listener removed");
            }
        }
    }

    public void handleEvent(Event e) {
        if (e instanceof GlobalEvent)
            globalEventHandler.handleEvent(e);
        else if (e instanceof LevelEvent)
            levelEventHandler.handleEvent(e);
        else if (e instanceof StoryEvent)
            storyEventHandler.handleEvent(e);
        else if (e instanceof InteractionEvent)
            interactionEventHandler.handleEvent(e);
        else if (e instanceof ChangeScreenEvent)
            changeScreenHandler.handleEvent(e);

        else
            Gdx.app.error(tag, "Wrong EventType");
    }

    public void handleEvent(Event e, float x, float y) {
        if (e instanceof GlobalEvent)
            globalEventHandler.handleEvent(e);
        else if (e instanceof LevelEvent)
            levelEventHandler.handleEvent(e);
        else if (e instanceof StoryEvent)
            storyEventHandler.handleEvent(e);
        else if (e instanceof ChangeScreenEvent)
            changeScreenHandler.handleEvent(e);
        else if (e instanceof InteractionEvent)
            interactionEventHandler.handleEvent(e);

        else
            Gdx.app.error(tag, "Wrong EventType");

    }


    public EngineAccessor getEngine() {
        return engine;
    }

    public void setEngine(EngineAccessor engine) {
        this.engine = engine;
    }
}
