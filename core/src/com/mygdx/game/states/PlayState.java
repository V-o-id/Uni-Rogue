package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.sprites.Grid;
import com.mygdx.game.sprites.Player;

import static com.mygdx.game.sprites.Grid.COLUMS;
import static com.mygdx.game.sprites.Grid.ROWS;


public class PlayState extends State {

    private final Grid grid;
    private final Player player;


    public PlayState(GameStateManager gsm) {
        super(gsm);
        //create grid
        grid = new Grid(".", Color.WHITE);
        //create player and place it into grid on specific position
        player = new Player("@", Color.WHITE, grid, COLUMS/2, ROWS/2);
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
        //draw grid with all elements in it (and does update the grid automatically)
        drawGrid(sb);
        sb.end();
    }

    @Override
    public void dispose() {
        player.dispose();
    }

    private void drawGrid(SpriteBatch sb) {
        for(int y = 0; y < ROWS; y++){
            for(int x = 0; x < COLUMS; x++){
                grid.getGrid()[y][x].draw(sb, 1);
            }
        }
    }
}
