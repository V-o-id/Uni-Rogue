package com.mygdx.game.sprites.gameObjects;


import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.sprites.font.EmojiSupport;
import com.mygdx.game.sprites.font.EmojiSupportInstance;

/**
 * Class for explicit labels to extend. Does part for handling Emojis.
 */
public class GameObjectLabel extends Label {


    /**
     * if isEmoji is unknown use this constructor
     */
    public GameObjectLabel(String text, LabelStyle style) {
        super(text, style);
        if (isEmoji(text)) {
            handleEmojiInit(text, style);
        }
    }

    /**
     * if isEmoji is known (false or true) use this constructor
     */
    public GameObjectLabel(String text, LabelStyle style, boolean isEmoji) {
        super(text, style);
        if (isEmoji && isEmoji(text)) {
            handleEmojiInit(text, style);
        }
    }

    private void handleEmojiInit(String text, LabelStyle style) {
        EmojiSupport emojiSupport = EmojiSupportInstance.getEmojiSupport();

        String filteredCharacter = emojiSupport.FilterEmojis(text);

        super.setStyle(style);
        super.setText(filteredCharacter);
    }

    public void setText(String newText, boolean isEmoji) {
        if (isEmoji && isEmoji(newText)) {
            handleEmojiInit(newText, this.getStyle());
        } else {
            super.setText(newText);
        }
    }

    //zero means that the object has no specific value (e.g. PathLabel)
    //is designed for Items/Enemy to get their data (e.g. health value, attack damage value, ...)
    public int getObjectValue() {
        return 0;
    }

    /**
     * Checks, if given String is an Emoji
     * @param message String to check
     * @return true if message is an Emoji, false otherwise
     */
    @SuppressWarnings({"UnnecessaryUnicodeEscape", "RegExpDuplicateAlternationBranch"})
    static boolean isEmoji(String message) {
        return message.matches("(?:[\uD83C\uDF00-\uD83D\uDDFF]|[\uD83E\uDD00-\uD83E\uDDFF]|" +
                "[\uD83D\uDE00-\uD83D\uDE4F]|[\uD83D\uDE80-\uD83D\uDEFF]|" +
                "[\u2600-\u26FF]\uFE0F?|[\u2700-\u27BF]\uFE0F?|\u24C2\uFE0F?|" +
                "[\uD83C\uDDE6-\uD83C\uDDFF]{1,2}|" +
                "[\uD83C\uDD70\uD83C\uDD71\uD83C\uDD7E\uD83C\uDD7F\uD83C\uDD8E\uD83C\uDD91-\uD83C\uDD9A]\uFE0F?|" +
                "[\u0023\u002A\u0030-\u0039]\uFE0F?\u20E3|[\u2194-\u2199\u21A9-\u21AA]\uFE0F?|[\u2B05-\u2B07\u2B1B\u2B1C\u2B50\u2B55]\uFE0F?|" +
                "[\u2934\u2935]\uFE0F?|[\u3030\u303D]\uFE0F?|[\u3297\u3299]\uFE0F?|" +
                "[\uD83C\uDE01\uD83C\uDE02\uD83C\uDE1A\uD83C\uDE2F\uD83C\uDE32-\uD83C\uDE3A\uD83C\uDE50\uD83C\uDE51]\uFE0F?|" +
                "[\u203C\u2049]\uFE0F?|[\u25AA\u25AB\u25B6\u25C0\u25FB-\u25FE]\uFE0F?|" +
                "[\u00A9\u00AE]\uFE0F?|[\u2122\u2139]\uFE0F?|\uD83C\uDC04\uFE0F?|\uD83C\uDCCF\uFE0F?|" +
                "[\u231A\u231B\u2328\u23CF\u23E9-\u23F3\u23F8-\u23FA]\uFE0F?)+");
    }
}