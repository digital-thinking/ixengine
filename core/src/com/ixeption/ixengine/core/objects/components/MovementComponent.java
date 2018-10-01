package com.ixeption.ixengine.core.objects.components;

import com.ixeption.ixengine.core.IxGameConfig;
import com.ixeption.ixengine.core.objects.GameObject;
import com.ixeption.ixengine.ixgame.GameStage;

public class MovementComponent implements IComponent {
    @Override
    public void initialize(GameObject parent, GameStage gameStage) {

    }

    @Override
    public void update(float delta, GameObject parent) {
        parent.setX(parent.getX() + 100 * delta);
        if (parent.getX() > IxGameConfig.VIRTUAL_VIEWPORT_WIDTH + parent.getWidth() * 2) {
            parent.setX(-100);
        }
    }

    @Override
    public void destroy(GameObject parent) {

    }
}
