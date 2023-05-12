package com.mygdx.game.sprites.enemies;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.sprites.Enemy;
import com.mygdx.game.sprites.Grid;

public class Bat extends Enemy {

    public Bat(Grid grid, int gridPosX, int gridPosY) {
        super("B", grid, gridPosX, gridPosY, Color.GRAY);
        setHealth(40);
        setDamage(6);
    }
}
