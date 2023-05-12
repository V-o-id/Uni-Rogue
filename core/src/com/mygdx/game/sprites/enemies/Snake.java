package com.mygdx.game.sprites.enemies;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.sprites.Enemy;
import com.mygdx.game.sprites.Grid;

public class Snake extends Enemy {
    public Snake(Grid grid, int gridPosX, int gridPosY) {
        super("S", grid, gridPosX, gridPosY, Color.PURPLE);
        setHealth(30);
        setDamage(8);
    }
}
