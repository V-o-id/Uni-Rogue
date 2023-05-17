package com.mygdx.game.sprites.gameObjects.enemies.enemyTypes;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.sprites.Constants;
import com.mygdx.game.sprites.gameObjects.enemies.EnemyLabel;
import com.mygdx.game.sprites.Grid;

public class Goblin extends EnemyLabel {
    public Goblin(Grid grid, int gridPosX, int gridPosY) {
        super("G", Constants.STYLE, grid, gridPosX, gridPosY);
        setHealth(70);
        setDamage(8);
    }
}