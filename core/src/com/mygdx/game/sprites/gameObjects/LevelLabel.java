package com.mygdx.game.sprites.gameObjects;

public class LevelLabel extends GameObjectLabel{

  public final static String LEVEL_CHARACTER = "L";

  public LevelLabel(LabelStyle style) {
    super(LEVEL_CHARACTER, style);
  }

  public LevelLabel(LabelStyle style, boolean isEmoji) {
    super(LEVEL_CHARACTER, style, isEmoji);
  }
}
