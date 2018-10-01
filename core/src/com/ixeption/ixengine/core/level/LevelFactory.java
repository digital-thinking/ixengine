package com.ixeption.ixengine.core.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.ixeption.ixengine.bloodbrothers.GameStage;
import com.ixeption.ixengine.core.level.xml.GameObjectContainer;
import com.ixeption.ixengine.core.objects.GameObject;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;

public class LevelFactory {
    public static final String tag = LevelFactory.class.getSimpleName();

    public static ArrayList<GameObject> parseActorsInternal(String filepath, GameStage stage) {
        Gdx.app.debug(tag, "Parse Internal");
        Serializer serializer = new Persister();
        GameObjectContainer level = null;
        try {
            level = serializer.read(GameObjectContainer.class, Gdx.files
                    .internal(filepath).read());

        } catch (Exception e) {
            e.printStackTrace();
            Gdx.app.error(tag, "Error while parsing Level XML" + e);
        }

        for (GameObject obj : level.getGameobjects()) {
            obj.initialize(stage);
        }
        return level.getGameobjects();

    }

    public static ArrayList<GameObject> parseActorsLocal(String filepath) {
        Gdx.app.debug(tag, "Parse Local");
        Serializer serializer = new Persister();
        GameObjectContainer level = null;
        try {
            level = serializer.read(GameObjectContainer.class, Gdx.files
                    .local(filepath).read());

        } catch (Exception e) {
            e.printStackTrace();
            Gdx.app.error(tag, "Error while parsing Level XML" + e);
        }
        return level.getGameobjects();

    }

    public static void serializeGameObjects(ArrayList<GameObject> objs,
                                            String filePath) {
        Gdx.app.debug(tag, "Save Local");
        boolean isLocAvailable = Gdx.files.isLocalStorageAvailable();
        if (!isLocAvailable) {
            Gdx.app.error(tag, "Local Storage not avaialble");
            return;
        }
        Serializer serializer = new Persister();
        GameObjectContainer container = new GameObjectContainer();
        container.setGameobjects(objs);

        FileHandle file = Gdx.files.local(filePath);

        try {
            serializer.write(container, file.file());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
