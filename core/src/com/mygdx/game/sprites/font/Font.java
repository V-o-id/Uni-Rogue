package com.mygdx.game.sprites.font;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import java.util.Arrays;


public class Font {

    public BitmapFont setFont() {

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(new FileHandle("C:/Windows/Fonts/seguiemj.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();


        return font;
    }
}
