package com.ixeption.ixengine.core.objects.components;

import com.ixeption.ixengine.bloodbrothers.GameStage;
import com.ixeption.ixengine.core.objects.GameObject;
import org.simpleframework.xml.Attribute;

public class PlayerComponent implements IComponent {

    @Attribute(name = "value")
    String value;

    @Override
    public void initialize(GameObject parent, GameStage gameStage) {

    }

    @Override
    public void update(float delta, GameObject parent) {
        return;

    }

    @Override
    public void destroy(GameObject parent) {


    }

}
