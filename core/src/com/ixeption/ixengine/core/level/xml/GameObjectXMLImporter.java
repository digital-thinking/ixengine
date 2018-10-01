package com.ixeption.ixengine.core.level.xml;

import com.ixeption.ixengine.core.objects.GameObject;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

@Root(name = "level")
public class GameObjectXMLImporter {

    @ElementList(name = "gameobject", inline = true)
    private ArrayList<GameObject> gameobjects;

    public ArrayList<GameObject> getGameobjects() {
        return gameobjects;
    }

}
