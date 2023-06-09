package com.mygdx.game.sprites.gameObjects.enemies.enemyTypes;

import com.mygdx.game.sprites.Constants;
import com.mygdx.game.sprites.Grid;
import com.mygdx.game.sprites.gameObjects.PlayerLabel;
import com.mygdx.game.sprites.gameObjects.enemies.EnemyLabel;

/**
 * Enemy type Goblin
 * 40% chance to steal player's gold.
 * <p>
 * Health: 70
 * Damage: 8
 */
public class Goblin extends EnemyLabel {
    public Goblin(Grid grid, int gridPosX, int gridPosY) {
        super("👺", Constants.STYLE, grid, gridPosX, gridPosY);
        setHealth(70);
        setDamage(8);
    }

    @Override
    public void attack(PlayerLabel player, int damage) {
        super.attack(player, damage);
        if((int)(Math.random() * 5) <= 2) { //40% chance for the goblin to steal 0-10 gold
            player.loseGold((int)(Math.random() * 10));
        }
    }
}
