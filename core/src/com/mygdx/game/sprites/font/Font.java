package com.mygdx.game.sprites.font;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

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
        //FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/JetBrainsMono-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        //parameter.characters =  "\u1F40D";


        parameter.characters += "☻" + "ダ";
        parameter.characters += "₿";
        parameter.characters += "\uD83D\uDE00";
        parameter.characters += "\uD83D\uDC0D";
        parameter.characters += "\uD83C\uDF1F";
        parameter.characters += new String(Character.toChars(0x1F349));


        parameter.size = 30;
        BitmapFont font = generator.generateFont(parameter);


        EmojiSupport emojiSupport = new EmojiSupport();
        emojiSupport.Load(Gdx.files.internal("fonts/emojis25.atlas"));
        emojiSupport.AddEmojisToFont(font);

        generator.dispose();
        return font;
    }

}
