package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.data.GameInstance;
import com.mygdx.game.sprites.Grid;
import com.mygdx.game.sprites.Text;
import com.mygdx.game.sprites.font.Font;
import com.mygdx.game.sprites.gameObjects.GameTimer;

import static com.mygdx.game.sprites.Constants.music;
import static com.mygdx.game.sprites.Constants.volume;
import static com.mygdx.game.sprites.Grid.COLUMNS;
import static com.mygdx.game.sprites.Grid.ROWS;

/**
 * The actual instance of where the game takes place, representing the gameplay.
 */
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


    /**
     * Constructor for initializing the PlayState. On entering a new room, a new PlayState is constructed.
     * @param gsm Reference to the {@link GameStateManager}
     * @param level Starts at 1 and increases with every descend to the next level.
     * @param playerHealth Starts at an initial value and carries over to the next level
     * @param playerAttackDamage Starts at an initial value and carries over to the next level
     * @param gold Starts at 0 and carries over to the next level
     * @param gameTime Starts at 0 and carries over to the next level
     * @param gameInstanceData Reference to the {@link GameInstance}
     */
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

        //start sound
        Sound startSound = Gdx.audio.newSound(Gdx.files.internal("audio/StartSound.wav"));
        startSound.play(volume * 0.6f);

        //background music
        if (!music.isPlaying()) {
            music.setVolume(volume);
            music.play();
            music.setLooping(true);
        }
    }

    @Override
    protected void handleInput() {
        grid.getPlayer().characterControl(grid, gsm, this);
    }

    /**
     * Update properties that change during the game (collection gold, getting damage,...)
     * @param dt Delta for calculating frames per second
     */
    @Override
    public void update(float dt) {
        handleInput();
        healthText.setText("Health: " + grid.getPlayer().getHealth());
        attackDamageText.setText("Attack Damage: " + grid.getPlayer().getAttackDamage());
        goldText.setText("Gold: " + grid.getPlayer().getGold());
        // go to the next level (with stored properties) and set new PlayState
        if (grid.getPlayer().getInformation().equals("New Level")) {
            currentGameInstanceData.incrementLevel();
            currentGameInstanceData.incrementBeatenRooms();
            PlayState p = new PlayState(gsm, ++level, grid.getPlayer().getHealth(), grid.getPlayer().getAttackDamage(), grid.getPlayer().getGold(), gameTimer.getSeconds(), currentGameInstanceData);
            gsm.pop();
            gsm.push(p);
            gsm.set(p);
        }
        informationText.setText(grid.getPlayer().getInformation());
    }

    /**
     * Update room number
     */
    public void updateCurrentRoomText() {
        roomText.setText("Room: " + (grid.getPlayer().getCurrentRoom().getRoomNumber() + 1) + "/" + (grid.getRooms().length));
    }

    /**
     * Draws the grid and sets labels for game information
     * @param sb SpriteBatch
     */
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

    /**
     * Draws the grid to screen with contents of the array
     * @param sb SpriteBatch
     */
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

    /**
     * Pauses the game and enters {@link PauseState}
     */
    public void pause() {
        pauseMusic();
        gameTimerThread.interrupt();
        running = false;
    }

    /**
     * Resumes the game
     */
    public void resume() {
        resumeMusic();
        running = true;
        gameTimerThread = new Thread(gameTimer);
        gameTimerThread.start();
    }

    /**
     * Pauses {@link com.mygdx.game.sprites.Constants#music}
     */
    public static void pauseMusic() {
        music.pause();
    }

    /**
     * Resumes {@link com.mygdx.game.sprites.Constants#music}
     */
    public static void resumeMusic() {
        if (!music.isPlaying()) {
            music.play();
        }
    }

    /**
     * Sets color of health text to get different effects for different damage types
     * @param color Desired color
     */
    public static void setHealthTextColor(Color color) {
        healthText.getFont().setColor(color);
    }

    /**
     * Sets {@link #gameTimerText}, called for every update of game time
     * @param text Text + game time
     */
    public void setGameTimerText(String text) {
        gameTimerText.setText(text);
    }
}
