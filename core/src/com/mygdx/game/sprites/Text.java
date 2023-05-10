package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.sprites.font.Font;

public class Text {

  private String text;
  private final BitmapFont font;
  private final GlyphLayout glyphLayout;
  private final Vector3 position;
  private final Rectangle rectangle;

  public Text(String text, float x, float y, BitmapFont font, boolean centered) {
    this.text = text;
    this.font = font;

    glyphLayout = new GlyphLayout();
    glyphLayout.setText(font, text);

    if(centered) {
      this.position = new Vector3(x - glyphLayout.width/2, y, 0);
    } else {
      this.position = new Vector3(x, y, 0);
    }

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
    return position;
  }

  public void setText(String text) {
    this.text = text;
  }

  public boolean isClicked(int x, int y){
    return rectangle.contains(x, y);
  }

}
