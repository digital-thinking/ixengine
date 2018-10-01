package com.ixeption.ixengine.core.gui.inventory;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ixeption.ixengine.core.screen.EngineAccessor;
import com.ixeption.ixengine.core.util.tweens.ActorTween;

public class InventoryImpl extends Actor implements Inventory {


    private boolean isOpen;
    private String textureFilePath = "Inventar/Inventar.png";
    private EngineAccessor engineAccessor;

    public InventoryImpl(EngineAccessor engineAccessor) {
        this.engineAccessor = engineAccessor;


        engineAccessor.getAssetManager().load(textureFilePath, Texture.class);
        this.setName("Invetory");
        this.setX(-900);
        this.setY(0);
        this.setWidth(964);
        this.setHeight(167);

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (isOpen) {
                    closeInventory();
                } else
                    openInventory();

            }
        });

    }

    @Override
    public void openInventory() {
        if (Tween.getRegisteredAccessor(Actor.class) == null) {
            Tween.registerAccessor(Actor.class, new ActorTween());
        }

        Tween.to(this, ActorTween.MOVEX, 0.5f).target(0)
                .ease(TweenEquations.easeInBack)
                .start(engineAccessor.getTweenEngine());

        isOpen = true;
    }

    @Override
    public void closeInventory() {
        if (Tween.getRegisteredAccessor(Actor.class) == null) {
            Tween.registerAccessor(Actor.class, new ActorTween());
        }

        Tween.to(this, ActorTween.MOVEX, 0.5f).target(-900)
                .ease(TweenEquations.easeInBack)
                .start(engineAccessor.getTweenEngine());
        isOpen = false;

    }

    @Override
    public void addItem(Item item, int quantity) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean removeItem(Item item, int quantity) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        this.setZIndex(255);
        Color color = this.getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

        Texture tex = engineAccessor.getAssetManager()
                .get(textureFilePath, Texture.class);
        // Monster Call :)
        batch.draw(tex, this.getX(), this.getY(), this.getOriginX(),
                this.getOriginY(), this.getWidth(), this.getHeight(),
                this.getScaleX(), this.getScaleY(), this.getRotation(), 0, 0,
                tex.getWidth(), tex.getHeight(), false, false);
    }

}
