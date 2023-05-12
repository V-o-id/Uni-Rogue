package com.mygdx.game.sprites.enemies;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.sprites.Enemy;
import com.mygdx.game.sprites.Grid;

public class Goblin extends Enemy {
    public Goblin(Grid grid, int gridPosX, int gridPosY) {
        super("G", grid, gridPosX, gridPosY, Color.GREEN);
        setHealth(70);
        setDamage(8);
    }
}
