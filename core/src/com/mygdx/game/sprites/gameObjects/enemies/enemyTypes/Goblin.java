package com.mygdx.game.sprites.gameObjects.enemies.enemyTypes;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.sprites.Constants;
import com.mygdx.game.sprites.gameObjects.PlayerLabel;
import com.mygdx.game.sprites.gameObjects.enemies.EnemyLabel;
import com.mygdx.game.sprites.Grid;

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
