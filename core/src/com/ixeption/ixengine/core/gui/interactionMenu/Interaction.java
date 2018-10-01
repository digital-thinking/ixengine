package com.ixeption.ixengine.core.gui.interactionMenu;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ixeption.ixengine.core.IxGameConfig;
import com.ixeption.ixengine.core.gui.WidgetLoader;
import com.ixeption.ixengine.core.objects.GameObject;
import com.ixeption.ixengine.core.screen.EngineAccessor;
import com.ixeption.ixengine.core.util.tweens.ActorTween;

import java.io.IOException;
import java.util.EnumSet;

public class Interaction extends WidgetGroup {
    public static final String tag = Interaction.class.getSimpleName();
    public final static float INTERACTION_WIDTH = 124 + 64;
    public final static float INTERACTION_HEIGHT = 120 + 64;
    EnumSet<InteractionType> _type;
    GameObject targetActor;
    EngineAccessor engineAccessor;
    private Button look;
    private Button take;
    private Button use;
    private Button speak;

    public Interaction(float x, float y, GameObject actor,
                       EnumSet<InteractionType> type, EngineAccessor engineAccessor) {
        this.engineAccessor = engineAccessor;
        targetActor = actor;
        this.setName("Interaction: " + targetActor.getName());
        _type = type;

        Gdx.app.debug(tag,
                "X: " + x + " Y: " + y + " Actor: " + actor.getName()
                        + " Options: " + type.toString()
        );

        WidgetLoader loader = new WidgetLoader(engineAccessor);
        WidgetGroup parsed = null;
        try {
            parsed = loader.parseForWidgets("InteractionMenu/interaction.xml");
        } catch (IOException e) {
            Gdx.app.error(tag, "Failed to create Interactionmenu from Actor: "
                    + actor.getName());
        }

        look = (Button) parsed.getChildren().get(0);
        take = (Button) parsed.getChildren().get(1);
        use = (Button) parsed.getChildren().get(2);
        speak = (Button) parsed.getChildren().get(3);

        look.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Haha. Catched this");
                return false;
            }
        });

        if (_type.contains(InteractionType.LOOK))
            look.setVisible(true);
        if (_type.contains(InteractionType.TAKE))
            take.setVisible(true);
        if (_type.contains(InteractionType.USE))
            use.setVisible(true);
        if (_type.contains(InteractionType.SPEAK))
            speak.setVisible(true);

        this.setSize(INTERACTION_WIDTH, INTERACTION_HEIGHT);
        // Check if Interaction Menu is out of the Screen
        if (x - this.getWidth() / 2 < 0)
            x = INTERACTION_WIDTH / 2;
        if (x + (INTERACTION_WIDTH / 2) > IxGameConfig.VIRTUAL_VIEWPORT_WIDTH)
            x = IxGameConfig.VIRTUAL_VIEWPORT_HEIGHT - (INTERACTION_WIDTH / 2);
        if (y - INTERACTION_HEIGHT / 2 < 0)
            y = INTERACTION_HEIGHT / 2;
        if (y + INTERACTION_HEIGHT / 2 > IxGameConfig.VIRTUAL_VIEWPORT_HEIGHT)
            y = IxGameConfig.VIRTUAL_VIEWPORT_HEIGHT - (INTERACTION_HEIGHT / 2);

        this.addActor(parsed);
        this.moveBy(x - this.getWidth() / 2, y - this.getHeight() / 2);

        this.setOriginX(this.getWidth() / 2);
        this.setOriginY(this.getHeight() / 2);

        applyTween();

    }

    public GameObject getTargetActor() {
        return targetActor;
    }

    @Override
    public void act(float delta) {

    }

    public void createDialog(Actor actor, Actor target) {

    }

    public void applyTween() {
        if (Tween.getRegisteredAccessor(Actor.class) == null) {
            Tween.registerAccessor(Actor.class, new ActorTween());
        }

        this.setRotation(180);
        Tween.to(this, ActorTween.ROTATE, 0.9f).target(0)
                .ease(TweenEquations.easeNone)
                .start(engineAccessor.getTweenEngine());

        this.setScale(0);
        Tween.to(this, ActorTween.SCALE, 1).target(1)
                .ease(TweenEquations.easeInOutElastic)
                .start(engineAccessor.getTweenEngine());

        this.setColor(1, 1, 1, 0);
        Tween.to(this, ActorTween.ALPHA, 1).target(1)
                .ease(TweenEquations.easeInCubic)
                .start(engineAccessor.getTweenEngine());

    }

}
