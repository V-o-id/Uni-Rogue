package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class Entity extends Label {
    public final Grid grid;
    public int gridPosX;
    public int gridPosY;
    public Entity(String character, Color color, Grid grid, int gridPosX, int gridPosY) {
        super(character, new Label.LabelStyle(new Font().setBitmapFont(), color));

        BitmapFont font = new Font().setBitmapFont();
        EmojiSupport emojiSupport = new EmojiSupport();
        emojiSupport.Load(Gdx.files.internal("fonts/emojis25.atlas"));
        emojiSupport.AddEmojisToFont(font);

        String filteredCharacter = emojiSupport.FilterEmojis(character);
        LabelStyle style = new LabelStyle(font, color);
        super.setStyle(style);
        super.setText(filteredCharacter);

        this.grid = grid;
        this.gridPosX = gridPosX;
        this.gridPosY = gridPosY;
        grid.setGridCharacter(gridPosY, gridPosX, this);
    }
}
