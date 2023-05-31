package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.sprites.font.Font;

/**
 * Contains static resources to be able to get them from everywhere.
 */
public class Constants {
    public final static Label.LabelStyle STYLE = new Label.LabelStyle(new Font().getBitmapFont(), Color.WHITE);
    public static float volume = 0.9f;
    public static Music music = Gdx.audio.newMusic(Gdx.files.internal("audio/Rogue.wav"));
}
