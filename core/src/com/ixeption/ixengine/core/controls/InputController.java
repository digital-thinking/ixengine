package com.ixeption.ixengine.core.controls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.ixeption.ixengine.core.eventhandling.EventDispatcher;
import com.ixeption.ixengine.core.eventhandling.events.LevelEvent;
import com.ixeption.ixengine.core.eventhandling.events.LevelEvent.LevelEventType;
import com.ixeption.ixengine.core.eventhandling.events.gui.ChangeScreenEvent;

public class InputController implements InputProcessor {
    private final Stage stage;
    public int backcounter;

    public InputController(Stage stage) {
        this.stage = stage;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.BACK) {
            Gdx.app.exit();
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {

        return true;
    }

    @Override
    public boolean keyTyped(char character) {

        return stage.keyTyped(character);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        stage.touchDown(screenX, screenY, pointer, button);
        if (button == Buttons.RIGHT) {
            LevelEvent e = new LevelEvent(LevelEventType.REMOVE, screenX,
                    screenY);
            e.setActorToRemove("Box");
            e.setStage(stage);
            EventDispatcher.instance.handleEvent(e);

            return true;
        } else if (button == Buttons.LEFT) {
            LevelEvent e = new LevelEvent(LevelEventType.CREATE, screenX,
                    screenY);
            e.setStage(stage);
            EventDispatcher.instance.handleEvent(e);
            return true;
        } else if (button == Buttons.MIDDLE) {
            ChangeScreenEvent e = new ChangeScreenEvent(new InputEvent());
            e.setOptions("level_1");
            EventDispatcher.getInstance().handleEvent(e);
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        return stage.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        return stage.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {

        return stage.mouseMoved(screenX, screenY);
    }

    @Override
    public boolean scrolled(int amount) {

        return stage.scrolled(amount);
    }

}
