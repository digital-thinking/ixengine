package com.ixeption.ixengine.core.objects.components;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "sprite")
public class AnimationHolder {

    @Attribute(name = "name")
    private String name;
    @Attribute(name = "resource")
    private String resource;
    @Attribute(name = "frame_time")
    private float frame_time;
    @Attribute(name = "play_mode")
    private String play_mode;

    @SuppressWarnings("unused")
    private AnimationHolder() {

    }

    public AnimationHolder(String texturePath) {
        resource = texturePath;
        String[] structure = texturePath.split("/");
        name = structure[structure.length - 1];
        frame_time = 0.0f;
        play_mode = "static";

    }

    public String getName() {
        return name;
    }

    public String getResource() {
        return resource;
    }

    public float getFrame_time() {
        return frame_time;
    }

    public String getPlay_mode() {
        return play_mode;
    }

}