package com.mygdx.game.sprites.gameObjects;

public class LevelLabel extends GameObjectLabel{
  /**
   * Stores the character used for enter new level
   */
  public final static String LEVEL_CHARACTER = "🚪";

  public LevelLabel(LabelStyle style) {
    super(LEVEL_CHARACTER, style);
  }
}
