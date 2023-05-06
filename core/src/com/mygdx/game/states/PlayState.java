package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.sprites.Grid;
import com.mygdx.game.sprites.Text;

import static com.mygdx.game.sprites.Grid.COLUMNS;
import static com.mygdx.game.sprites.Grid.ROWS;


public class PlayState extends State {

    private final Grid grid;

    private final Text healthbar;
    private final Text attackDamage;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        grid = new Grid();
        healthbar = new Text("Health: " + grid.getPlayer().getHealth(), State.WIDTH / 2F, State.HEIGHT - 50);
        attackDamage = new Text("Attack Damage: " + grid.getPlayer().getAttackDamage(), State.WIDTH / 2F, State.HEIGHT - healthbar.getGlyphLayout().height - 70);
    }

    @Override
    protected void handleInput() {
        grid.getPlayer().characterControl(grid);
    }

    @Override
    public void update(float dt) {
        handleInput();
        healthbar.setText("Health: " + grid.getPlayer().getHealth());
        attackDamage.setText("Attack Damage: " + grid.getPlayer().getAttackDamage());
    }

    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        sb.begin();
        drawGrid(sb);
        healthbar.getFont().draw(sb, healthbar.getText(), healthbar.getPostiton().x, healthbar.getPostiton().y + healthbar.getGlyphLayout().height);
        attackDamage.getFont().draw(sb, attackDamage.getText(), attackDamage.getPostiton().x, attackDamage.getPostiton().y + attackDamage.getGlyphLayout().height);
        sb.end();
    }

    @Override
    public void dispose() {

    }

    private void drawGrid(SpriteBatch sb) {
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
                grid.getGrid()[y][x].draw(sb, 1);
            }
        }
    }
}
