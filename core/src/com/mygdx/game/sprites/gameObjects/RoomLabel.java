package com.mygdx.game.sprites.gameObjects;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.sprites.font.Font;

public class RoomLabel extends GameObjectLabel{

  public static final String ROOM_CHARACTER = ".";

  public RoomLabel(LabelStyle style) {
    super(ROOM_CHARACTER, style);
  }
}
