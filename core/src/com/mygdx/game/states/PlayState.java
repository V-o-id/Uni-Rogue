package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.sprites.Grid;
import com.mygdx.game.sprites.Text;
import com.mygdx.game.sprites.font.Font;
import com.mygdx.game.sprites.gameObjects.GameTimer;


import static com.mygdx.game.sprites.Grid.COLUMNS;
import static com.mygdx.game.sprites.Grid.ROWS;


public class PlayState extends State {

    private final Grid grid;

    private final Text healthText;
    private final Text attackDamageText;
    private final Text goldText;
    private final Text gameTimerText;
    private final Text informationText;
    public final Text pauseText;
    private final Text roomText;
    BitmapFont font = new Font().setBitmapFont();

    private static boolean running = true;

    private long runningSeconds = 0;
    private GameTimer gameTimer = new GameTimer(0, this);

    public PlayState(GameStateManager gsm) {
        super(gsm);
        grid = new Grid();
        healthText = new Text("Health: " + grid.getPlayer().getHealth(), 50, State.HEIGHT - 50, font, false);
        attackDamageText = new Text("Attack Damage: " + grid.getPlayer().getAttackDamage(), 50, State.HEIGHT -50 -healthText.getGlyphLayout().height - 20, font, false);
        goldText = new Text("Gold: " + grid.getPlayer().getHealth(), 50, State.HEIGHT -50 -healthText.getGlyphLayout().height -attackDamageText.getGlyphLayout().height -40, font, false);
        gameTimerText = new Text("Time: " + gameTimer.getSeconds(), 50, State.HEIGHT -50 -healthText.getGlyphLayout().height -attackDamageText.getGlyphLayout().height - goldText.getGlyphLayout().height -60, font, false);
        roomText = new Text("Room: " + (grid.getPlayer().getCurrentRoom().getRoomNumber()+1) + "/" + (grid.getRooms().length), 250, State.HEIGHT -50 -healthText.getGlyphLayout().height -attackDamageText.getGlyphLayout().height - goldText.getGlyphLayout().height -60, font, false);
        informationText = new Text("", 50, State.HEIGHT -50 -healthText.getGlyphLayout().height -attackDamageText.getGlyphLayout().height - goldText.getGlyphLayout().height -gameTimerText.getGlyphLayout().height - 80, font, false);
        pauseText = new Text("Pause", State.WIDTH-150,  State.HEIGHT-50, font, false);
    }

    @Override
    protected void handleInput() {
        grid.getPlayer().characterControl(grid, gsm, this);
    }

    @Override
    public void update(float dt) {
        handleInput();
        healthText.setText("Health: " + grid.getPlayer().getHealth());
        attackDamageText.setText("Attack Damage: " + grid.getPlayer().getAttackDamage());
        goldText.setText("Gold: " + grid.getPlayer().getGold());
        informationText.setText(grid.getPlayer().getInformation());
    }
    public void updateCurrentRoomText() {
        roomText.setText("Room: " + (grid.getPlayer().getCurrentRoom().getRoomNumber()+1) + "/" + (grid.getRooms().length));
    }

    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        sb.begin();
        drawGrid(sb);
        healthText.getFont().draw(sb, healthText.getText(), healthText.getPostiton().x, healthText.getPostiton().y + healthText.getGlyphLayout().height);
        attackDamageText.getFont().draw(sb, attackDamageText.getText(), attackDamageText.getPostiton().x, attackDamageText.getPostiton().y + attackDamageText.getGlyphLayout().height);
        goldText.getFont().draw(sb, goldText.getText(), goldText.getPostiton().x, goldText.getPostiton().y + goldText.getGlyphLayout().height);
        gameTimerText.getFont().draw(sb, gameTimerText.getText(), gameTimerText.getPostiton().x, gameTimerText.getPostiton().y + gameTimerText.getGlyphLayout().height);
        informationText.getFont().draw(sb, informationText.getText(), informationText.getPostiton().x, informationText.getPostiton().y + informationText.getGlyphLayout().height);
        pauseText.getFont().draw(sb, pauseText.getText(), pauseText.getPostiton().x, pauseText.getPostiton().y + pauseText.getGlyphLayout().height);
        roomText.getFont().draw(sb, roomText.getText(), roomText.getPostiton().x, roomText.getPostiton().y + roomText.getGlyphLayout().height);
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
