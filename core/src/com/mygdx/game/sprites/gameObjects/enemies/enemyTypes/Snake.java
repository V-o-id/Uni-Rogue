package com.mygdx.game.sprites.gameObjects.enemies.enemyTypes;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.sprites.gameObjects.PlayerLabel;
import com.mygdx.game.sprites.gameObjects.enemies.EnemyLabel;
import com.mygdx.game.sprites.Grid;
import com.mygdx.game.sprites.Constants;
import com.mygdx.game.states.PlayState;

public class Snake extends EnemyLabel {
    public Snake(Grid grid, int gridPosX, int gridPosY) {
        super("🐍", Constants.STYLE, grid, gridPosX, gridPosY);
        setHealth(30);
        setDamage(8);
    }

    @Override
    public void attack(PlayerLabel player, int damage) {
        super.damage(damage);
        PlayState.setHealthTextColor(Color.PURPLE);
        player.poison(5);
    }
}
