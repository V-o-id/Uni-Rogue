package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.sprites.Grid;
import com.mygdx.game.sprites.Text;
import com.mygdx.game.sprites.gameObjects.GameTimer;

import static com.mygdx.game.sprites.Grid.COLUMNS;
import static com.mygdx.game.sprites.Grid.ROWS;


public class PlayState extends State {

    private final Grid grid;

    private final Text healthbar;
    private final Text attackDamage;
    private final Text gameTimerText;
    public final Text pauseText;
    private final Text roomText;

    private static boolean running = true;

    private long runningSeconds = 0;
    private GameTimer gameTimer = new GameTimer(0, this);

    public PlayState(GameStateManager gsm) {
        super(gsm);
        grid = new Grid();
        healthbar = new Text("Health: " + grid.getPlayer().getHealth(), State.WIDTH / 2F, State.HEIGHT - 50);
        attackDamage = new Text("Attack Damage: " + grid.getPlayer().getAttackDamage(), State.WIDTH / 2F, State.HEIGHT - healthbar.getGlyphLayout().height - 70);
        gameTimerText = new Text("Time: " + gameTimer.getSeconds(), State.WIDTH / 2F, State.HEIGHT - healthbar.getGlyphLayout().height - attackDamage.getGlyphLayout().height - 90);
        roomText = new Text("Room: " + (grid.getPlayer().getCurrentRoom().getRoomNumber()+1) + "/" + (grid.getRooms().length), State.WIDTH / 2F, State.HEIGHT - healthbar.getGlyphLayout().height - attackDamage.getGlyphLayout().height - gameTimerText.getGlyphLayout().height - 110);
        pauseText = new Text("Pause", State.WIDTH-150,  State.HEIGHT-50);
    }

    @Override
    protected void handleInput() {
        grid.getPlayer().characterControl(grid, gsm, this);
    }

    public void updateCurrentRoomText() {
        roomText.setText("Room: " + (grid.getPlayer().getCurrentRoom().getRoomNumber()+1) + "/" + (grid.getRooms().length));
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
        gameTimerText.getFont().draw(sb, gameTimerText.getText(), gameTimerText.getPostiton().x, gameTimerText.getPostiton().y + gameTimerText.getGlyphLayout().height);
        roomText.getFont().draw(sb, roomText.getText(), roomText.getPostiton().x, roomText.getPostiton().y + roomText.getGlyphLayout().height);
        pauseText.getFont().draw(sb, pauseText.getText(), pauseText.getPostiton().x, pauseText.getPostiton().y + pauseText.getGlyphLayout().height);
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


    public void pause() {
        runningSeconds = gameTimer.getSeconds();
        gameTimer.pause();
        running = false;
    }
    public boolean isRunning() {
        return running;
    }
    public void resume() {
        running = true;
        gameTimer = new GameTimer(runningSeconds, this); // resume doesn't work ? (would crash)
    }

    public void setGameTimerText(String text) {
        gameTimerText.setText(text);
    }


}
