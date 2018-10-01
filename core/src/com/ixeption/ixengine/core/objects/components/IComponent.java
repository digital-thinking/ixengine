package com.ixeption.ixengine.core.objects.components;

import com.ixeption.ixengine.bloodbrothers.GameStage;
import com.ixeption.ixengine.core.objects.GameObject;

public interface IComponent {


    public void initialize(GameObject parent, GameStage gameStage);

    public void update(float delta, GameObject parent);

    public void destroy(GameObject parent);

}
