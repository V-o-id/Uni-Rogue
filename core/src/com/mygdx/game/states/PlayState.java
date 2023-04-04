package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.sprites.Enemy;
import com.mygdx.game.sprites.Player;
import com.mygdx.game.sprites.Text;

import static com.mygdx.game.Application.HEIGHT;
import static com.mygdx.game.Application.WIDTH;


public class PlayState extends State {

    private Player player;
    private Enemy enemy;
    private Text grid;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        player = new Player("@", WIDTH / 2, HEIGHT / 2);
        enemy = new Enemy("G", WIDTH / 4, HEIGHT / 4);
    }

    @Override
    protected void handleInput() {
        player.characterControl();
    }

    @Override
    public void update(float dt) {
        handleInput();
        player.update(dt);

        if (player.getRectangle().overlaps(enemy.getRectangle())) {
            Gdx.app.exit();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        sb.begin();
        player.getFont().draw(sb, player.getText(), player.getPostiton().x, player.getPostiton().y + player.getGlyphLayout().height);
        enemy.getFont().draw(sb, enemy.getText(), enemy.getPostiton().x, enemy.getPostiton().y + enemy.getGlyphLayout().height);

        new Text(".", 20, 20).getFont().draw(sb, ".", 20, 20);
        new Text(".", 40, 40).getFont().draw(sb, ".", 40, 40);

        int y;
        int x;

        for (y = 0; y < 100; y++) {
            for (x = 0; x < 100; x++) {
                new Text(".", x * 10, y * 10).getFont().draw(sb, ".", x * 10, y * 10);
            }
        }

  /*
        int width = (int) new Text(".", x, y).getRectangle().width;
        int height = (int) new Text(".", x, y).getRectangle().height;

        while(y < 100) {
            while (x < 100) {
                new Text(".", x, y).getFont().draw(sb, ".", x, y);
                x += 10;
            }
            y += 10;
        }

             */

/*

        for (int y = 0; y < 50; y++) {
            for (int x = 0; x < 100; x++) {
                new Text(".", x, y);

            }

        }

 */
        //new Text("@", 20, 20);
        sb.end();
    }

    @Override
    public void dispose() {
        player.dispose();
    }

    private void drawGrid() {

    }
}
