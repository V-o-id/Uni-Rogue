package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.data.GameInstance;
import com.mygdx.game.sprites.Grid;
import com.mygdx.game.sprites.Text;
import com.mygdx.game.sprites.font.Font;
import com.mygdx.game.sprites.gameObjects.GameTimer;

import static com.mygdx.game.Application.getVolume;
import static com.mygdx.game.sprites.Grid.COLUMNS;
import static com.mygdx.game.sprites.Grid.ROWS;



public class PlayState extends State {

    private final Grid grid;
    private static Text healthText;
    private final Text attackDamageText;
    private final Text goldText;
    private final Text gameTimerText;
    private final Text informationText;
    private final Text levelText;
    public final Text pauseText;
    private final Text roomText;
    BitmapFont font = Font.getBitmapFont();

    private final GameInstance currentGameInstanceData;

    private int level;
    private static boolean running = false;

    private final GameTimer gameTimer;
    private volatile Thread gameTimerThread;
    private static Music music;


    public PlayState(GameStateManager gsm, int level, int playerHealth, int playerAttackDamage, int gold, long gameTime, GameInstance gameInstanceData) {
        super(gsm);
        grid = new Grid(playerHealth, playerAttackDamage, gold, level, gameInstanceData);
        this.level = level;
        this.gameTimer = new GameTimer(gameTime, this, gameInstanceData);
        this.gameTimerThread = new Thread(gameTimer);
        this.currentGameInstanceData = gameInstanceData;
        healthText = new Text("Health: " + grid.getPlayer().getHealth(), 50, State.HEIGHT - 50, font, false);
        attackDamageText = new Text("Attack Damage: " + grid.getPlayer().getAttackDamage(), 50, State.HEIGHT - 50 - healthText.getGlyphLayout().height - 20, font, false);
        goldText = new Text("Gold: " + grid.getPlayer().getHealth(), 50, State.HEIGHT - 50 - healthText.getGlyphLayout().height - attackDamageText.getGlyphLayout().height - 40, font, false);
        levelText = new Text("Level: " + level, 50, State.HEIGHT - 50 - healthText.getGlyphLayout().height - attackDamageText.getGlyphLayout().height - goldText.getGlyphLayout().height - 60, font, false);
        gameTimerText = new Text("Time: " + gameTimer.getSeconds(), 50, State.HEIGHT - 50 - healthText.getGlyphLayout().height - attackDamageText.getGlyphLayout().height - goldText.getGlyphLayout().height - levelText.getGlyphLayout().height - 80, font, false);
        roomText = new Text("Room: " + (grid.getPlayer().getCurrentRoom().getRoomNumber() + 1) + "/" + (grid.getRooms().length), 250, State.HEIGHT - 50 - healthText.getGlyphLayout().height - attackDamageText.getGlyphLayout().height - goldText.getGlyphLayout().height - levelText.getGlyphLayout().height - 80, font, false);
        informationText = new Text("", 50, State.HEIGHT - 50 - healthText.getGlyphLayout().height - attackDamageText.getGlyphLayout().height - goldText.getGlyphLayout().height - levelText.getGlyphLayout().height - gameTimerText.getGlyphLayout().height - 100, font, false);
        pauseText = new Text("Pause", State.WIDTH - 150, State.HEIGHT - 50, font, false);
        running = true;
        gameTimerThread.start();

        Sound startSound = Gdx.audio.newSound(Gdx.files.internal("audio/StartSound.wav"));
        startSound.play(getVolume() * 0.6f);

        music = Gdx.audio.newMusic(Gdx.files.internal("audio/Rogue.wav"));
        music.setVolume(getVolume());
        music.play();
        music.setLooping(true);
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
        if (grid.getPlayer().getInformation().equals("New Level")) {
            gsm.pop();
            gsm.push(new PlayState(gsm, level++, grid.getPlayer().getHealth(), grid.getPlayer().getAttackDamage(), grid.getPlayer().getGold(), gameTimer.getSeconds(), currentGameInstanceData));
            gsm.set(new PlayState(gsm, level++, grid.getPlayer().getHealth(), grid.getPlayer().getAttackDamage(), grid.getPlayer().getGold(), gameTimer.getSeconds(), currentGameInstanceData));
        }
        informationText.setText(grid.getPlayer().getInformation());
    }

    public void updateCurrentRoomText() {
        roomText.setText("Room: " + (grid.getPlayer().getCurrentRoom().getRoomNumber() + 1) + "/" + (grid.getRooms().length));
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
        pauseMusic();
        gameTimerThread.interrupt();
        running = false;
    }

    public void resume() {
        resumeMusic();
        running = true;
        gameTimerThread = new Thread(gameTimer);
        gameTimerThread.start();
    }

    public static void setHealthTextColor(Color color) {
        healthText.getFont().setColor(color);
    }

    public void setGameTimerText(String text) {
        gameTimerText.setText(text);
    }

    public static void setVolume(float musicVolume) {
        music.setVolume(musicVolume);
    }

    public static void pauseMusic() {
        music.pause();
    }

    public static void resumeMusic() {
        music.play();
    }
}
