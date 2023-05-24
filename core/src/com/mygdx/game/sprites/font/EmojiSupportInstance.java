package com.mygdx.game.sprites.font;

import com.badlogic.gdx.Gdx;

public class EmojiSupportInstance extends EmojiSupport {

    private static EmojiSupport emojiSupportSingleton;

    private EmojiSupportInstance() {}

    /**
     * Creates on actual instance of EmojiSupport.
     * @return EmojiSupport instance
     */
    public static EmojiSupport getEmojiSupport() {
        if (emojiSupportSingleton == null) {
            emojiSupportSingleton = new EmojiSupport();
            emojiSupportSingleton.Load(Gdx.files.internal("fonts/emojis/emojiAtlas.atlas"));
            emojiSupportSingleton.AddEmojisToFont(Font.getBitmapFont());
        }
        return emojiSupportSingleton;
    }
}
