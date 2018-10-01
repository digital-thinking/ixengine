package com.ixeption.ixengine.core;

/**
 * Created by Chris on 16.05.2014.
 */
public class IxGameConfig {

    //Render Vars
    public static int VIRTUAL_VIEWPORT_WIDTH = 1280;
    public static int VIRTUAL_VIEWPORT_HEIGHT = 720;


    //Physic Vars
    public static float PHYSICS_GRAVITY_HORIZONTAL = 0f;
    public static float PHYSICS_GRAVITY_VERTICAL = -9.81f;
    public static float PHYSICS_SCALING = 100;
    // Dialogs
    public static String PATH_TO_DIALOGS = "DialogSystemXML//";


    //Acting
    public static boolean GAMESTAGE_ACTING_ENABLED = true;
    public static boolean GAMESTAGE_INPUT_ENABLED = true;
    public static boolean UI_ENABLED = true;

    //Rendering
    public static boolean PHYSIC_ENABLED = true;
    public static boolean PHYSIC_DESTRUCTION_ENABLED = true;
    public static boolean GAME_RENDERING_ENABLED = true;
    public static boolean UI_RENDERING_ENABLED = true;

    public static boolean LIGHTS_ENABLED = true;

    // Debugging
    public static boolean PHYSIC_DEBUG_RENDERING_ENABLED = false;

}
