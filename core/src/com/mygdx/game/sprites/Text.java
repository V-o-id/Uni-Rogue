package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Text {

  private final String text;
  private final BitmapFont font;
  private final BitmapFont emojiFont;
  private final GlyphLayout glyphLayout;
  private final Vector3 position;
  private final Rectangle rectangle;

  public Text(String text, float x, float y) {
    this.text = text;

    font = new Font().setBitmapFont();
    emojiFont = new Font().setEmojiFont();

    glyphLayout = new GlyphLayout();
    glyphLayout.setText(font, text);

    this.position = new Vector3(x - glyphLayout.width/2, y, 0);

    rectangle = new Rectangle();
    rectangle.set(getPostiton().x, getPostiton().y, getGlyphLayout().width, getGlyphLayout().height);
  }

  public String getText() {
    return text;
  }

  public GlyphLayout getGlyphLayout() {
    return glyphLayout;
  }

  public BitmapFont getFont() {
    return font;
  }

  public BitmapFont getEmojiFont() {
    return emojiFont;
  }

  public Rectangle getRectangle() {
    return rectangle;
  }

  public Vector3 getPostiton() {
    return position;
  }

}
