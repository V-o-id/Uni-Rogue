package com.mygdx.game.sprites.font;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Font {

    private static Font fontSingleton;
    private static BitmapFont bitmapFontSingleton;

    /**
     * Font getter method.
     * @return the font
     */
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

    /**
     * Generates font with FreeFontTypeGenerator.
     * Makes use of the EmojiSupport class to add emojis to the font by using an atlas.
     * @return generated BitMapFont with emoji support
     */
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
