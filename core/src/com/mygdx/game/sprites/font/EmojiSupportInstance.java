package com.mygdx.game.sprites.font;

import com.badlogic.gdx.Gdx;

public class EmojiSupportInstance extends EmojiSupport {

    private static EmojiSupport emojiSupportSingleton;

    private EmojiSupportInstance() {}

    public static EmojiSupport getEmojiSupport() {
        if (emojiSupportSingleton == null) {
            emojiSupportSingleton = new EmojiSupport();
            emojiSupportSingleton.Load(Gdx.files.internal("fonts/emojis25.atlas"));
            emojiSupportSingleton.AddEmojisToFont(Font.getBitmapFont());
        }
        return emojiSupportSingleton;
    }

}
