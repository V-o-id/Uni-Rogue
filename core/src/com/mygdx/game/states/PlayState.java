package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.sprites.Grid;
import com.mygdx.game.sprites.Player;

import static com.mygdx.game.sprites.Grid.COLUMNS;
import static com.mygdx.game.sprites.Grid.ROWS;


public class PlayState extends State {

    private final Grid grid;
    private final Player player;


    public PlayState(GameStateManager gsm) {
        super(gsm);
        grid = new Grid(".", Color.WHITE);
        player = new Player("@", Color.WHITE, grid, COLUMNS / 2, ROWS / 2);
    }

    @Override
    protected void handleInput() {
        player.characterControl(grid);
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        sb.begin();
        drawGrid(sb);
        player.draw(sb, 1);
        sb.end();
    }

    @Override
    public void dispose() {
        player.dispose();
    }

    private void drawGrid(SpriteBatch sb) {
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
                grid.getGrid()[y][x].draw(sb, 1);
            }
        }
    }
}
