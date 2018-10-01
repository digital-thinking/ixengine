package com.ixeption.ixengine.bloodbrothers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ixeption.ixengine.core.eventhandling.EventDispatcher;
import com.ixeption.ixengine.core.eventhandling.events.gui.ChangeScreenEvent;

/**
 * Created by Julian on 30.01.2016.
 */
public class StartStage extends Stage {

    private Table table_;
    private TextButton startButton_;
    private Skin buttonSkin_;

    public StartStage(Viewport viewport) {
        super(viewport);


    }

    public void initialize() {
        buttonSkin_ = new Skin();

        // Generate a 1x1 white texture and store it in the skin named "white".
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        buttonSkin_.add("white", new Texture(pixmap));

        buttonSkin_.add("default", new BitmapFont());

        // Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = buttonSkin_.newDrawable("white", Color.DARK_GRAY);
        buttonStyle.down = buttonSkin_.newDrawable("white", Color.DARK_GRAY);
//        buttonStyle.checked = buttonSkin_.newDrawable("white", Color.BLUE);
        buttonStyle.over = buttonSkin_.newDrawable("white", Color.LIGHT_GRAY);
        buttonStyle.font = buttonSkin_.getFont("default");
        buttonSkin_.add("default", buttonStyle);

        // Create a table that fills the screen. Everything else will go inside this table.
        table_ = new Table();
        table_.setFillParent(true);
        addActor(table_);

        startButton_ = new TextButton("startButton", buttonSkin_);
        table_.add(startButton_);

        startButton_.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
//                startButton_.setText("Tada. Clicked this");
                ChangeScreenEvent e = new ChangeScreenEvent(new InputEvent());
                e.setOptions("level_1");
                EventDispatcher.getInstance().handleEvent(e);
            }
        });

        // Add an image actor. Have to set the size, else it would be the size of the drawable (which is the 1x1 texture).
//        table_.add(new Image(buttonSkin_.newDrawable("white", Color.RED))).size(64);
    }
}
