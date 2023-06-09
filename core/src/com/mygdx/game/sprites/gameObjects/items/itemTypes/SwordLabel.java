package com.mygdx.game.sprites.gameObjects.items.itemTypes;

import com.mygdx.game.sprites.Grid;
import com.mygdx.game.sprites.gameObjects.items.ItemLabel;

public class SwordLabel extends ItemLabel {
  /**
   * Contains information of a sword in the grid
   */
  private final int attackDamage;
  public final static String SWORD_CHARACTER = "🗡";

  public SwordLabel(Grid grid, LabelStyle style, int gridPosX, int gridPosY, int level) {
    super(SWORD_CHARACTER, style, grid, gridPosX, gridPosY, level);
    this.attackDamage = level;
  }
  /**
   * Return value (attackDamange) of the sword label
   */
  @Override
  public int getObjectValue() {
    return attackDamage;
  }
}
