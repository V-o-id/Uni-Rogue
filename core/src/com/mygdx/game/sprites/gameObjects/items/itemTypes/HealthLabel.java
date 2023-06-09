package com.mygdx.game.sprites.gameObjects.items.itemTypes;

import com.mygdx.game.sprites.Grid;
import com.mygdx.game.sprites.gameObjects.items.ItemLabel;
import com.mygdx.game.sprites.roomstrategy.RoomStrategyException;

public class HealthLabel extends ItemLabel {
  /**
   * Contains information of healthPotion in the grid.
   */
  private final int healthValue;
  public final static String HEALTH_CHARACTER = "❤";
  public HealthLabel(Grid grid, LabelStyle style, int gridPosX, int gridPosY, int level) {
    super(HEALTH_CHARACTER, style, grid, gridPosX, gridPosY, level);
    this.healthValue = level;
  }
  /**
   * Return value (healtValue) of the health label.
   */
  @Override
  public int getObjectValue() {
    return healthValue;
  }
}

