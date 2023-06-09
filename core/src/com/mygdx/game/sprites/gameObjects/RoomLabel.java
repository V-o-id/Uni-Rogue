package com.mygdx.game.sprites.gameObjects;

public class RoomLabel extends GameObjectLabel{
  /**
   * Stores the character used for rooms
   */
  public static final String ROOM_CHARACTER = ".";

  public RoomLabel(LabelStyle style) {
    super(ROOM_CHARACTER, style);
  }
}
