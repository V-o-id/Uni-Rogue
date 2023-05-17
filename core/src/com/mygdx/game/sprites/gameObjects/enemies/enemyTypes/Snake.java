package com.mygdx.game.sprites.gameObjects.enemies.enemyTypes;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.sprites.gameObjects.enemies.EnemyLabel;
import com.mygdx.game.sprites.Grid;
import com.mygdx.game.sprites.Constants;

public class Snake extends EnemyLabel {
    public Snake(Grid grid, int gridPosX, int gridPosY) {
        super("S", Constants.STYLE, grid, gridPosX, gridPosY);
        setHealth(30);
        setDamage(8);
    }

}
