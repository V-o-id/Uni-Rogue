package com.mygdx.game.sprites.gameObjects.items;

import com.mygdx.game.sprites.Grid;
import com.mygdx.game.sprites.gameObjects.GameObjectLabel;

public abstract class ItemLabel extends GameObjectLabel {
  /**
   * Abstract Class of Items
   */
  public ItemLabel(String itemCharacter, LabelStyle style, Grid grid, int gridPosX, int gridPosY, int level){
    super(itemCharacter, style);
    grid.setGridCharacter(gridPosY, gridPosX, this);
  }
}
