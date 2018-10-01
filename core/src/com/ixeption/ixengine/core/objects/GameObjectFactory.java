package com.ixeption.ixengine.core.objects;


public class GameObjectFactory {


    /**
     * Start to create a new GameObject, you can chain creationMethodts and finish your Creation with  {@link UnfinishedGameObject.finish()}
     *
     * @param name
     * @param x
     * @param y
     * @param width
     * @param height
     * @return layer
     */

    public static UnfinishedGameObject create(String name, float x, float y,
                                              float width, float height, int layer) {

        GameObject current = new GameObject();
        current.setName(name);
        current.setX(x);
        current.setY(y);
        current.setWidth(width);
        current.setHeight(height);
        current.setLayer(layer);


        return new UnfinishedGameObject(current);

    }


}
