package com.mygdx.game.sprites.gameObjects.items;

import com.mygdx.game.sprites.Grid;

public class SwordLabel extends ItemLabel {

  private final int attackDamage;
  public final static String SWORD_CHARACTER = "S";

  public SwordLabel(Grid grid, LabelStyle style, int gridPosX, int gridPosY, int level) {
    super(SWORD_CHARACTER, style, grid, gridPosX, gridPosY, level);
    this.attackDamage = level;
  }

  @Override
  public int getObjectValue() {
    return attackDamage;
  }
}
