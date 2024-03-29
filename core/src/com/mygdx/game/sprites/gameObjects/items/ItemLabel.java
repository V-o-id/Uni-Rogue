package com.mygdx.game.sprites.gameObjects.items;

import com.mygdx.game.sprites.Grid;
import com.mygdx.game.sprites.gameObjects.GameObjectLabel;

/**
 * Class that represents the label of an item.
 */
public abstract class ItemLabel extends GameObjectLabel {
  /**
   * Abstract class of Items.
   */
  public ItemLabel(String itemCharacter, LabelStyle style, Grid grid, int gridPosX, int gridPosY, int level){
    super(itemCharacter, style);
    grid.setGridCharacter(gridPosY, gridPosX, this);
  }
}
