package com.mygdx.game.sprites.gameObjects.items;

import com.mygdx.game.sprites.gameObjects.GameObjectLabel;
import com.mygdx.game.sprites.Grid;

public abstract class ItemLabel extends GameObjectLabel {

  public ItemLabel(String itemCharacter, LabelStyle style, Grid grid, int gridPosX, int gridPosY){
    super(itemCharacter, style);
    grid.setGridCharacter(gridPosY, gridPosX, this);
  }
}
