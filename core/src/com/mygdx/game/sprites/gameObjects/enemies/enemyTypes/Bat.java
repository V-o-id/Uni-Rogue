package com.mygdx.game.sprites.gameObjects.enemies.enemyTypes;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.sprites.Constants;
import com.mygdx.game.sprites.gameObjects.enemies.EnemyLabel;
import com.mygdx.game.sprites.Grid;

public class Bat extends EnemyLabel {

    public Bat(Grid grid, int gridPosX, int gridPosY) {
        super("🦇", Constants.STYLE, grid, gridPosX, gridPosY);
        setHealth(40);
        setDamage(6);
    }
}
