package com.ixeption.ixengine.core.level.xml;

import com.ixeption.ixengine.core.objects.GameObject;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

@Root(name = "level")
public class GameObjectContainer {

    @ElementList(name = "gameobject", inline = true)
    private ArrayList<GameObject> gameobjects;


    @ElementList(name = "templates", inline = true, required = false)
    private ArrayList<String> gameobjects1;

    public ArrayList<GameObject> getGameobjects() {
        return gameobjects;
    }

    public void setGameobjects(ArrayList<GameObject> gameobjects) {
        this.gameobjects = gameobjects;
    }

}
