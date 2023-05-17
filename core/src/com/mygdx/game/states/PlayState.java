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

    private Grid grid;

    private final Text healthText;
    private final Text attackDamageText;
    private final Text goldText;
    private final Text gameTimerText;
    private final Text informationText;
    private final Text levelText;
    public final Text pauseText;
    private final Text roomText;
    BitmapFont font = Font.getBitmapFont();

    private int level;
    private static boolean running = false;

    private final GameTimer gameTimer;
    private final Thread gameTimerThread;


    public PlayState(GameStateManager gsm, int level, int playerHealth, int playerAttackDamage, int gold, long gameTime) {
        super(gsm);
        grid = new Grid(playerHealth, playerAttackDamage, gold, level);
        this.level = level;
        this.gameTimer = new GameTimer(gameTime, this);
        this.gameTimerThread = new Thread(gameTimer);
        healthText = new Text("Health: " + grid.getPlayer().getHealth(), 50, State.HEIGHT - 50, font, false);
        attackDamageText = new Text("Attack Damage: " + grid.getPlayer().getAttackDamage(), 50, State.HEIGHT -50 -healthText.getGlyphLayout().height - 20, font, false);
        goldText = new Text("Gold: " + grid.getPlayer().getHealth(), 50, State.HEIGHT -50 -healthText.getGlyphLayout().height -attackDamageText.getGlyphLayout().height -40, font, false);
        levelText = new Text("Level: " + level, 50, State.HEIGHT -50 -healthText.getGlyphLayout().height -attackDamageText.getGlyphLayout().height - goldText.getGlyphLayout().height -60, font, false);
        gameTimerText = new Text("Time: " + gameTimer.getSeconds(), 50, State.HEIGHT -50 -healthText.getGlyphLayout().height -attackDamageText.getGlyphLayout().height - goldText.getGlyphLayout().height -levelText.getGlyphLayout().height -80, font, false);
        roomText = new Text("Room: " + (grid.getPlayer().getCurrentRoom().getRoomNumber()+1) + "/" + (grid.getRooms().length), 250, State.HEIGHT -50 -healthText.getGlyphLayout().height -attackDamageText.getGlyphLayout().height - goldText.getGlyphLayout().height -levelText.getGlyphLayout().height -80, font, false);
        informationText = new Text("", 50, State.HEIGHT -50 -healthText.getGlyphLayout().height -attackDamageText.getGlyphLayout().height - goldText.getGlyphLayout().height -levelText.getGlyphLayout().height -gameTimerText.getGlyphLayout().height - 100, font, false);
        pauseText = new Text("Pause", State.WIDTH-150,  State.HEIGHT-50, font, false);
        running = true;
        gameTimerThread.start();
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
        if(grid.getPlayer().getInformation().equals("New Level")) {
            gsm.pop();
            gsm.push(new PlayState(gsm, level++, grid.getPlayer().getHealth(), grid.getPlayer().getAttackDamage(), grid.getPlayer().getGold(), gameTimer.getSeconds()));
            gsm.set(new PlayState(gsm, level++, grid.getPlayer().getHealth(), grid.getPlayer().getAttackDamage(), grid.getPlayer().getGold(), gameTimer.getSeconds()));
        }
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
        healthText.getFont().draw(sb, healthText.getText(), healthText.getPosition().x, healthText.getPosition().y + healthText.getGlyphLayout().height);
        attackDamageText.getFont().draw(sb, attackDamageText.getText(), attackDamageText.getPosition().x, attackDamageText.getPosition().y + attackDamageText.getGlyphLayout().height);
        goldText.getFont().draw(sb, goldText.getText(), goldText.getPosition().x, goldText.getPosition().y + goldText.getGlyphLayout().height);
        levelText.getFont().draw(sb, levelText.getText(), levelText.getPosition().x, levelText.getPosition().y + levelText.getGlyphLayout().height);
        gameTimerText.getFont().draw(sb, gameTimerText.getText(), gameTimerText.getPosition().x, gameTimerText.getPosition().y + gameTimerText.getGlyphLayout().height);
        informationText.getFont().draw(sb, informationText.getText(), informationText.getPosition().x, informationText.getPosition().y + informationText.getGlyphLayout().height);
        pauseText.getFont().draw(sb, pauseText.getText(), pauseText.getPosition().x, pauseText.getPosition().y + pauseText.getGlyphLayout().height);
        roomText.getFont().draw(sb, roomText.getText(), roomText.getPosition().x, roomText.getPosition().y + roomText.getGlyphLayout().height);
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

    public boolean isRunning() {
        return running;
    }

    public void pause() {
        gameTimerThread.interrupt();
        running = false;
    }

    public void resume() {
        running = true;
        synchronized (gameTimerThread) {
            gameTimerThread.notify();
        }
    }

    public void setGameTimerText(String text) {
        gameTimerText.setText(text);
    }
}
