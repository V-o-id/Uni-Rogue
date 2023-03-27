package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Text {

  private final CharSequence text;
  private final BitmapFont font;
  private final GlyphLayout glyphLayout;
  private final Rectangle rectangle;
  private Vector3 postiton;


  public Text(CharSequence text, float x, float y) {
    this.text = text;

    font = new BitmapFont();

    glyphLayout = new GlyphLayout();
    glyphLayout.setText(font, text);

    this.postiton = new Vector3(x - glyphLayout.width/2, y, 0);

    rectangle = new Rectangle();
    rectangle.set(postiton.x, postiton.y, glyphLayout.width, glyphLayout.height);
  }

  public void update(float dt){
    rectangle.setPosition(postiton.x, postiton.y);
  }


  public CharSequence getText() {
    return text;
  }

  public Rectangle getRectangle() {
    return rectangle;
  }

  public GlyphLayout getGlyphLayout() {
    return glyphLayout;
  }

  public BitmapFont getFont() {
    return font;
  }

  public Vector3 getPostiton() {
    return postiton;
  }

  public void setPostiton(Vector3 postiton) {
    this.postiton = postiton;
  }
}
