package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label.*;

public class Label {

    private String character;
    private Color color;

    public Label(String character, Color color) {

        //LabelStyle style = new LabelStyle(new BitmapFont(), color);
        Label label = new Label(character, color);
    }
}
