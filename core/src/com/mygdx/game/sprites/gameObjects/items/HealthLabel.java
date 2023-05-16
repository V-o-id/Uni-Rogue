package com.mygdx.game.sprites.gameObjects.items;

import com.mygdx.game.sprites.Grid;

public class HealthLabel extends ItemLabel{

  private final int healthValue;
  public final static String HEALTH_CHARACTER = "H";
  public HealthLabel(Grid grid, LabelStyle style, int gridPosX, int gridPosY, int level) {
    super(HEALTH_CHARACTER, style, grid, gridPosX, gridPosY, level);
    this.healthValue = level;
  }

  @Override
  public int getObjectValue() {
    return healthValue;
  }
}

