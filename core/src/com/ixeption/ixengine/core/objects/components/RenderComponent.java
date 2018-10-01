package com.ixeption.ixengine.core.objects.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.ixeption.ixengine.bloodbrothers.GameStage;
import com.ixeption.ixengine.core.objects.GameObject;
import com.ixeption.ixengine.core.screen.EngineAccessor;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.LinkedList;
import java.util.List;

//TODO multible Rendercomponents with own Positions relative to parent
@Root(name = "renderComponent")
public class RenderComponent implements IComponent {
    public static final String tag = RenderComponent.class
            .getSimpleName();
    @ElementList(name = "animations")
    List<AnimationHolder> animationList;
    private Animation<TextureRegion> currentAnimation;
    @Attribute(name = "default")
    private String defaultState;

    private String textureFilePath;
    private boolean looping;
    private String activeState;
    private float stateTime = 0f;
    private TextureAtlas atlas;
    private float frameTime = 1.0f / 15.0f;
    private TextureRegion currentTextureRegion;
    private AssetManager assetManager;

    @SuppressWarnings("unused")
    private RenderComponent() {
        // SimpleXML Constructor
    }

    /**
     * Creates an RenderComponent as a static Texture, using the given texture and atlas.
     * Use Xml to create Animations
     *
     * @param texturePath
     */
    public RenderComponent(String texturePath) {

        animationList = new LinkedList<AnimationHolder>();
        animationList.add(new AnimationHolder(texturePath));
        // Only trim Folder
        String[] structure = texturePath.split("/");
        defaultState = structure[structure.length - 1];


    }

    @Override
    public void initialize(GameObject parent, GameStage gameStage) {
        this.assetManager = ((EngineAccessor) Gdx.app.getApplicationListener()).getAssetManager();

        for (AnimationHolder element : animationList) {

            assetManager
                    .load(element.getResource() + ".png", Texture.class);
            assetManager
                    .load(element.getResource() + ".atlas",
                            TextureAtlas.class);


        }
        assetManager.finishLoading();
        if (!setAnimation(defaultState)) {
            Gdx.app.error(tag, "Error setting Animation: " + defaultState);

        }

    }

    /**
     * Sets the Animation
     *
     * @param animationOrSpriteName
     * @return true if Animation is valid
     */
    public boolean setAnimation(String animationOrSpriteName) {
        PlayMode type = PlayMode.NORMAL;
        String matchedFile = null;
        for (AnimationHolder a : animationList) {
            if (a.getName().equals(animationOrSpriteName)) {
                matchedFile = a.getResource();
                frameTime = a.getFrame_time();
                if (a.getPlay_mode().equals("once")) {
                    type = Animation.PlayMode.NORMAL;
                    looping = false;
                } else if (a.getPlay_mode().equals("loop")) {
                    type = Animation.PlayMode.LOOP;
                    looping = true;
                } else if (a.getPlay_mode().equals("random")) {
                    type = Animation.PlayMode.LOOP_RANDOM;
                    looping = true;
                } else if (a.getPlay_mode().equals("static")) {
                    frameTime = 0.0f;
                    looping = false;

                } else if (a.getPlay_mode().equals("pingpong")) {
                    looping = true;
                    type = Animation.PlayMode.LOOP_PINGPONG;

                }

                break;
            }
        }

        if (matchedFile == null)
            return false;
        else {
            textureFilePath = matchedFile;
            activeState = animationOrSpriteName;
            atlas = assetManager.get(textureFilePath + ".atlas");
            Array<AtlasRegion> allFrames = atlas.findRegions(activeState);
            if (allFrames.size == 0) {
                Gdx.app.error(tag, "No matching Keyframes for given Animation: " + activeState);
                return false;
            }
            currentAnimation = new Animation<TextureRegion>(frameTime, allFrames, type);
            stateTime = 0;
            currentTextureRegion = currentAnimation.getKeyFrame(stateTime, looping);
            return true;
        }

    }

    @Override
    public void update(float delta, GameObject parent) {
        if (frameTime > 0.0f) {
            stateTime += delta;
            currentTextureRegion = currentAnimation.getKeyFrame(stateTime, looping);
        }
    }

    public void draw(Batch batch, float parentAlpha, GameObject gameObject) {
        Color color = gameObject.getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

        batch.draw(currentTextureRegion, gameObject.getX(), gameObject.getY(),
                gameObject.getOriginX(), gameObject.getOriginY(),
                gameObject.getWidth(), gameObject.getHeight(),
                gameObject.getScaleX(), gameObject.getScaleY(),
                gameObject.getRotation());

    }

    @Override
    public void destroy(GameObject parent) {


    }

}
