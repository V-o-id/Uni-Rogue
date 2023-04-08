package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Text {

  private final String text;
  private final BitmapFont font;
  private final GlyphLayout glyphLayout;
  private final Vector3 postiton;
  private final Rectangle rectangle;

  public Text(String text, float x, float y) {
    this.text = text;

    font = new Font().getBitmapFont();

    glyphLayout = new GlyphLayout();
    glyphLayout.setText(font, text);

    this.postiton = new Vector3(x - glyphLayout.width/2, y, 0);

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

  public Rectangle getRectangle() {
    return rectangle;
  }

  public Vector3 getPostiton() {
    return postiton;
  }

}
