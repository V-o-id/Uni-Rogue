package com.mygdx.game.sprites.gameObjects;


import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class GameObjectLabel extends Label {

  public GameObjectLabel(String text, LabelStyle style) {
    super(text, style);
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
