package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.sprites.Enemy;
import com.mygdx.game.sprites.Grid;
import com.mygdx.game.sprites.Player;

import static com.mygdx.game.Application.HEIGHT;
import static com.mygdx.game.Application.WIDTH;
import static com.mygdx.game.sprites.Grid.COLUMS;
import static com.mygdx.game.sprites.Grid.ROWS;


public class PlayState extends State {

    private Grid grid;
    private Player player;
    private Enemy enemy;


    public PlayState(GameStateManager gsm) {
        super(gsm);

        grid = new Grid("#", Color.WHITE);
        player = new Player("@", Color.WHITE, grid,40, 40);
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
        player.draw(sb, 1);
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
