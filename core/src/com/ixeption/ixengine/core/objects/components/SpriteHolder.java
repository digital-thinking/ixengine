package com.ixeption.ixengine.core.objects.components;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "sprite")
public class SpriteHolder {
    @Attribute(name = "name")
    private String name;
    @Attribute(name = "ressource")
    private String ressource;

    public String getName() {
        return name;
    }

    public String getResource() {
        return ressource;
    }

}