package com.mygdx.game.sprites.gameObjects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.sprites.EmojiSupport;
import com.mygdx.game.sprites.font.Font;

public class GameObjectLabel extends Label {

  public GameObjectLabel(String text, LabelStyle style) {
    super(text, style);
    EmojiSupport emojiSupport = new EmojiSupport();
    emojiSupport.Load(Gdx.files.internal("fonts/emojis25.atlas"));
    emojiSupport.AddEmojisToFont(style.font);

    String filteredCharacter = emojiSupport.FilterEmojis(text);
    super.setStyle(style);
    super.setText(filteredCharacter);
  }

  public String getLabelString() {
    return this.getText().toString();
  }

  //zero means that the object has no specific value (e.g. PathLabel)
  //is designed for Items/Enemy to get their data (e.g. health value, attack damage value, ...)
  public int getObjectValue(){
    return 0;
  }
}
