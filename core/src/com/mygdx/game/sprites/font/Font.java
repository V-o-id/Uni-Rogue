package com.mygdx.game.sprites.font;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Array;

import java.math.BigInteger;
import java.util.Arrays;

public class Font {

    private static Font fontSingleton;
    private static BitmapFont bitmapFontSingleton;

    public static Font getFont() {
        if (fontSingleton == null) {
            fontSingleton = new Font();
        }
        return fontSingleton;
    }

    public static BitmapFont getBitmapFont() {
        if (bitmapFontSingleton == null) {
            bitmapFontSingleton = Font.setBitmapFont();
        }
        return bitmapFontSingleton;
    }

    private static BitmapFont setBitmapFont() {

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/unifont-15.0.01.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 30;
        BitmapFont font = generator.generateFont(parameter);

        EmojiSupport emojiSupport = new EmojiSupport();
        emojiSupport.Load(Gdx.files.internal("fonts/emojis/emojiAtlas.atlas"));
        emojiSupport.AddEmojisToFont(font);

        generator.dispose();

        return font;
    }

}
