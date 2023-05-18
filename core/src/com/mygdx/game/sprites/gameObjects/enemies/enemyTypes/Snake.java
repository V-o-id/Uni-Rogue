package com.mygdx.game.sprites.gameObjects.enemies.enemyTypes;

import com.mygdx.game.sprites.gameObjects.PlayerLabel;
import com.mygdx.game.sprites.gameObjects.enemies.EnemyLabel;
import com.mygdx.game.sprites.Grid;
import com.mygdx.game.sprites.Constants;

public class Snake extends EnemyLabel {
    public Snake(Grid grid, int gridPosX, int gridPosY) {
        super("🐍", Constants.STYLE, grid, gridPosX, gridPosY);
        setHealth(30);
        setDamage(8);
    }

    @Override
    public void attack(PlayerLabel player, int damage) {
        super.damage(damage);
        player.poison(5);
    }
}
