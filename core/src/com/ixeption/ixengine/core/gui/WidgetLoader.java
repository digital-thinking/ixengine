package com.ixeption.ixengine.core.gui;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.ixeption.ixengine.core.eventhandling.EventDispatcher;
import com.ixeption.ixengine.core.eventhandling.events.gui.ChangeScreenEvent;
import com.ixeption.ixengine.core.eventhandling.events.gui.InteractionEvent;
import com.ixeption.ixengine.core.gui.interactionMenu.InteractionType;
import com.ixeption.ixengine.core.screen.EngineAccessor;
import com.ixeption.ixengine.core.util.tweens.ActorTween;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Widget Loader parses XML Files and builds Widgets and Images.
 *
 * @author Chris
 */
public class WidgetLoader {
    public static final String tag = WidgetLoader.class.getSimpleName();

    private XmlReader m_parser;
    private EngineAccessor engineAccessor;

    /**
     * Dont ever use Tween ,when useing this contructor!
     */
    public WidgetLoader() {

        m_parser = new XmlReader();

    }

    public WidgetLoader(EngineAccessor engineAccessor) {

        this.engineAccessor = engineAccessor;
        m_parser = new XmlReader();

    }

    public Element validateFile(String filename) throws IOException {

        Element file;

        file = m_parser.parse(Gdx.files.internal(filename));
        if (file == null) {
            Gdx.app.error(tag, "Empty File: " + filename);
            return null;

        }

        if (!file.getName().equals("Menu")) {
            Gdx.app.error(tag, "Wrong root Element for Menu " + filename);
            return null;
        }

        return file;
    }

    public WidgetGroup parseFileAsWidgetGroup(String filename) {
        WidgetGroup group = new WidgetGroup();
        group.setName(new String(filename.substring(0, filename.length() - 4)));

        try {
            WidgetGroup grouptoad = parseForWidgets(filename);

            LinkedList<Image> pics = parseForImages(filename);
            for (int i = 0; i < pics.size(); i++) {
                group.addActor(pics.get(i));
            }
            if (grouptoad != null) {
                group.addActor(grouptoad);
            }
            ArrayList<WidgetGroup> textfields = parseForTextfields(filename);
            for (int i = 0; i < textfields.size(); i++) {
                group.addActor(textfields.get(i));
                textfields.get(i).toFront();
            }
            ArrayList<WidgetGroup> groups = parseforContainer(filename);

            for (int i = 0; i < groups.size(); i++) {
                Gdx.app.debug(tag, groups.get(i).getName() + "CONTAINER NAME");
                group.addActor(groups.get(i));
                groups.get(i).toFront();
            }

            Gdx.app.debug(tag, "" + group.getChildren().size);
            Gdx.app.debug(tag, "GUI child size");
        } catch (IOException e) {

            e.printStackTrace();
        }
        return group;
    }

    public WidgetGroup parseForWidgets(String filename) throws IOException {

        Element file = validateFile(filename);
        if (file == null)
            return null;

        WidgetGroup group = new WidgetGroup();

        Skin skin = new Skin();
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(file
                .getAttribute("texturefile")));
        skin.addRegions(atlas);

        for (int i = 0; i < file.getChildCount(); i++) {

            if (file.getChild(i).getName().equals("button")) {

                // Button
                ButtonStyle style = new ButtonStyle();

                style.up = skin.getDrawable(file.getChild(i).getAttribute(
                        "state_normal"));
                style.down = skin.getDrawable(file.getChild(i).getAttribute(
                        "state_clicked"));
                style.over = skin.getDrawable(file.getChild(i).getAttribute(
                        "state_mouseover"));
                if (file.getChild(i).getName().equals("state_checked")) {
                    style.checked = skin.getDrawable(file.getChild(i)
                            .getAttribute("state_checked"));
                }
                Button button = new Button(style);
                button.setName(getName(file.getChild(i)));
                Rectangle bounds = getDimensions(file.getChild(i));
                button.setBounds(bounds.x, bounds.y, bounds.width,
                        bounds.height);
                button.setOrigin(button.getWidth() / 2, button.getHeight() / 2);

                // Tween
                loadTween(file.getChild(i), button);
                // Event
                initEvents(file.getChild(i), button);

                if (file.getChild(i).getAttribute("visible").equals("true")) {
                    button.setVisible(true);
                } else {
                    button.setVisible(false);
                }
                group.addActor(button);

            } else if (file.getChild(i).getName().equals("textinputfield")) {
                TextFieldStyle style = new TextFieldStyle();
                style.background = skin.getDrawable(file.getChild(i)
                        .getAttribute("background"));
                style.cursor = skin.getDrawable(file.getChild(i).getAttribute(
                        "cursor"));
                style.font = engineAccessor.getAssetManager()
                        .get("font/rmp.fnt", BitmapFont.class);
                style.fontColor = Color.WHITE;
                style.selection = skin.getDrawable(file.getChild(i)
                        .getAttribute("selection"));
                // new TextFieldStyle(font, fontColor, cursor, selection,
                // background)

                TextField field = new TextField(file.getChild(i).getAttribute(
                        "text"), style);
                field.setName(getName(file.getChild(i)));
                Rectangle bounds = getDimensions(file.getChild(i));
                field.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
                field.setOrigin(field.getWidth() / 2, field.getHeight() / 2);
                group.addActor(field);
            } else if (file.getChild(i).getName().equals("textbutton")) {
                TextButtonStyle style = new TextButtonStyle();
                style.up = skin.getDrawable(file.getChild(i).getAttribute(
                        "state_normal"));
                style.down = skin.getDrawable(file.getChild(i).getAttribute(
                        "state_clicked"));
                style.over = skin.getDrawable(file.getChild(i).getAttribute(
                        "state_mouseover"));
                style.font = engineAccessor.getAssetManager()
                        .get("font/rmp.fnt", BitmapFont.class);
                if (file.getChild(i).getName().equals("state_checked")) {
                    style.checked = skin.getDrawable(file.getChild(i)
                            .getAttribute("state_checked"));
                }

                TextButton butt = new TextButton(file.getChild(i).getAttribute(
                        "text"), style);
                butt.setName(getName(file.getChild(i)));
                Rectangle bounds = getDimensions(file.getChild(i));
                butt.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
                butt.setOrigin(butt.getWidth() / 2, butt.getHeight() / 2);
                butt.setVisible(file.getChild(i).getBooleanAttribute(
                        ("visible")));
                group.addActor(butt);
                loadTween(file.getChild(i), butt);
                initEvents(file.getChild(i), butt);

            }
        }

        return group;

    }

    public LinkedList<Image> parseForImages(String filename) throws IOException {

        Element file = validateFile(filename);
        if (file == null)
            return null;

        LinkedList<Image> imageList = new LinkedList<Image>();
        Skin skin = new Skin();
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(file
                .getAttribute("texturefile")));
        skin.addRegions(atlas);
        TextureRegion region = null;
        for (int i = 0; i < file.getChildCount(); i++) {
            if (file.getChild(i).getName().equals("image")) {
                if (file.getChild(i).getAttribute("path").contains(".")) {
                    Texture texture = new Texture(file.getChild(i)
                            .getAttribute("path"));
                    region = new TextureRegion(texture);
                } else {
                    region = skin.getRegion(file.getChild(i).getAttribute(
                            "path"));
                }

                Image image = new Image(region);

                image.setName(getName(file.getChild(i)));
                Rectangle bounds = getDimensions(file.getChild(i));
                image.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);

                image.setTouchable(Touchable.disabled);
                // set up Origin for Rotation
                image.setOrigin(image.getWidth() / 2, image.getHeight() / 2);
                image.setRotation(file.getChild(i)
                        .getFloatAttribute("rotation"));

                loadTween(file.getChild(i), image);
                if (file.getChild(i).getAttribute("visible").equals("true")) {
                    image.setVisible(true);
                } else {
                    image.setVisible(false);
                }
                imageList.add(image);
            }

        }

        return imageList;
    }

    public String parseForAudio(String filename) throws IOException {
        Element file = validateFile(filename);
        if (file == null)
            return null;

        String s = file.getAttribute("backgroundmusic");

        if (s.contains("mp3") | s.contains("wav") | s.contains("ogg")) {
            return s;
        }

        return null;

    }

    /**
     * Parse the File For Textfieds in a widgetgroup (not visible)
     *
     * @param filename
     * @return Widgetgroup of textfields
     * @throws IOException
     */
    public ArrayList<WidgetGroup> parseForTextfields(String filename)
            throws IOException {
        Element file = validateFile(filename);

        ArrayList<WidgetGroup> group = new ArrayList<WidgetGroup>();
        LabelStyle style = null;
        BitmapFont font = null;
        if (file == null)
            return null;
        if (file.getChildByNameRecursive("textfield") == null) {
            return group;
        }

        if (file.getAttribute("textfont").equals("default")) {
            font = engineAccessor.getAssetManager()
                    .get("font/rmp.fnt", BitmapFont.class);
            style = new LabelStyle(font, null);
        } else {
            font = new BitmapFont(Gdx.files.internal(file
                    .getAttribute("textfont")), false);
            style = new LabelStyle(font, null);
        }

        for (int i = 0; i < file.getChildCount(); i++) {
            if (file.getChild(i).getName().equals("textfield")) {
                WidgetGroup widgets = new WidgetGroup();
                Label textfield = new Label(file.getChild(i).getAttribute(
                        "text"), style);
                textfield.setName(getName(file.getChild(i)));
                Rectangle bounds = getDimensions(file.getChild(i));
                textfield.setBounds(bounds.x, bounds.y, bounds.width,
                        bounds.height);
                textfield.setOrigin(textfield.getWidth() / 2,
                        textfield.getHeight() / 2);
                textfield.setVisible(file.getChild(i).getBooleanAttribute(
                        "visible"));
                widgets.addActor(textfield);

                // Tween
                loadTween(file.getChild(i), textfield);

                // Event
                initEvents(file.getChild(i), textfield);
                // push into group
                widgets.addActor(textfield);
                group.add(widgets);

            }
        }

        return group;
    }

    public ArrayList<WidgetGroup> parseforContainer(String filename)
            throws IOException {
        Element file = validateFile(filename);
        if (file == null)
            return null;

        ArrayList<WidgetGroup> groups = new ArrayList<WidgetGroup>();
        WidgetGroup widgets;
        Skin skin = new Skin();
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(file
                .getAttribute("texturefile")));
        skin.addRegions(atlas);

        for (int i = 0; i < file.getChildCount(); i++) {
            if (file.getChild(i).getName().equals("container")) {
                widgets = new WidgetGroup();
                widgets.setName(getName(file.getChild(i)));

                Rectangle bounds = getDimensions(file.getChild(i));
                widgets.setBounds(bounds.x, bounds.y, bounds.width,
                        bounds.height);
                widgets.setOrigin(widgets.getWidth() / 2,
                        widgets.getHeight() / 2);
                widgets.setVisible(file.getChild(i).getBooleanAttribute(
                        "visible"));
                TextureRegion region = skin.getRegion(file.getChild(i)
                        .getAttribute("background"));
                Image img = new Image(region);
                img.setName("background");
                img.setBounds(0, 0, bounds.width, bounds.height);

                img.setTouchable(Touchable.disabled);
                // set up Origin for Rotation
                img.setOrigin(img.getWidth() / 2, img.getHeight() / 2);
                widgets.addActor(img);
                img.toFront();
                Gdx.app.debug(tag, "shitebelt");

                groups.add(widgets);
            }
        }
        return groups;
    }

    private void loadTween(Element element, Actor widget) {
        if (!element.getAttribute("tween").equals("")) {

            // Andere Tweens

            if (Tween.getRegisteredAccessor(Actor.class) == null) {
                Tween.registerAccessor(Actor.class, new ActorTween());
            }

            if (element.getAttribute("tween").equals("alphafadein")) {
                widget.setColor(1, 1, 1, 0);
                Tween.to(widget, ActorTween.ALPHA,
                        element.getFloatAttribute("fadetime")).target(1)
                        .ease(TweenEquations.easeInCubic).start(engineAccessor.getTweenEngine());

            } else if (element.getAttribute("tween").equals("alphafadeiandnout")) {
                widget.setColor(1, 1, 1, 0);
                Tween.to(widget, ActorTween.ALPHA,
                        element.getFloatAttribute("fadetime")).target(1)
                        .ease(TweenEquations.easeInCubic).start(engineAccessor.getTweenEngine());
                Tween.to(widget, ActorTween.ALPHA,
                        element.getFloatAttribute("fadetime"))
                        .target(1)
                        .ease(TweenEquations.easeInCubic)
                        .repeatYoyo(element.getIntAttribute("repeats"),
                                element.getFloatAttribute("delaytime"))
                        .start(engineAccessor.getTweenEngine());
            }

        }
    }

    private String getName(Element element) {
        return new String(element.getAttribute("name"));
    }

    private Rectangle getDimensions(Element element) {
        Rectangle rect = new Rectangle();

        rect.width = element.getFloatAttribute("width");
        rect.height = element.getFloatAttribute("height");
        rect.x = element.getFloatAttribute("Xpos");
        rect.y = element.getFloatAttribute("Ypos");
        return rect;
    }

    private void initEvents(final Element element, Actor actor) {

        if (!element.getAttribute("event").equals("")) {

            if (element.getAttribute("eventtrigger").equals("click")) {

                // Object wich triggers the Interaction Menu
                if (element.getAttribute("event").equals("interaction")) {
                    actor.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {

                            InteractionEvent interactionEvent = new InteractionEvent(
                                    event);
                            interactionEvent
                                    .setPossibleInteractionsFromString(element
                                            .getAttribute("eventdata"));
                            EventDispatcher.getInstance().handleEvent(
                                    interactionEvent, x, y);
                        }
                    });

                    // InteractionMenu Elements
                } else if (element.getAttribute("event").equals("look")) {
                    actor.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            InteractionEvent sel = new InteractionEvent(
                                    InteractionType.LOOK);
                            EventDispatcher.getInstance().handleEvent(sel);
                        }
                    });

                } else if (element.getAttribute("event").equals("take")) {
                    actor.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            InteractionEvent sel = new InteractionEvent(
                                    InteractionType.TAKE);
                            EventDispatcher.getInstance().handleEvent(sel);
                        }
                    });

                } else if (element.getAttribute("event").equals("use")) {
                    actor.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            InteractionEvent sel = new InteractionEvent(
                                    InteractionType.USE);
                            EventDispatcher.getInstance().handleEvent(sel);
                        }
                    });

                } else if (element.getAttribute("event").equals("speak")) {
                    actor.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            InteractionEvent sel = new InteractionEvent(
                                    InteractionType.SPEAK);
                            EventDispatcher.getInstance().handleEvent(sel);
                        }
                    });

                }
                // ChangeScreens
                else if (element.getAttribute("event").equals("changescreen")) {
                    actor.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            ChangeScreenEvent csevent = new ChangeScreenEvent(
                                    event);
                            csevent.setOptions(element
                                    .getAttribute("eventdata"));
                            EventDispatcher.getInstance().handleEvent(csevent);
                        }
                    });
                }
            }

        }
    }

}
