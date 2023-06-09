package com.mygdx.game.sprites.gameObjects;

public class PathLabel extends GameObjectLabel {

  /**
   * Stores the character used for paths between rooms.
   */
  public static final String PATH_CHARACTER = "#";

  public PathLabel(LabelStyle style) {
    super(PATH_CHARACTER, style);
  }
}
