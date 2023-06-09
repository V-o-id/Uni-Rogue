package com.mygdx.game.sprites.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.data.CurrentPlayer;
import com.mygdx.game.data.GameInstance;
import com.mygdx.game.sprites.Constants;
import com.mygdx.game.sprites.Grid;
import com.mygdx.game.sprites.Room;
import com.mygdx.game.sprites.gameObjects.enemies.EnemyLabel;
import com.mygdx.game.sprites.gameObjects.items.ItemLabel;
import com.mygdx.game.sprites.gameObjects.items.itemTypes.HealthLabel;
import com.mygdx.game.sprites.gameObjects.items.itemTypes.SwordLabel;
import com.mygdx.game.states.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mygdx.game.sprites.Constants.volume;
import static com.mygdx.game.sprites.gameObjects.PathLabel.PATH_CHARACTER;
import static com.mygdx.game.sprites.gameObjects.RoomLabel.ROOM_CHARACTER;

/**
 * Contains information of the current player
 */
public class PlayerLabel extends GameObjectLabel {

	private int gridPosX;
	private int gridPosY;
	private static String playerCharacter = Gdx.files.local("selectedCharacter.txt").readString();
	private String previousCharacter;
	private int health;
	private int attackDamage;
	private int poisonDuration = 0;
	private int gold;
	private String information = "";
	private Room currentRoom;
	private static final String DEFAULT_PLAYER_CHARACTER = "*";
	private boolean onPath = false; // to check if it's possible that player is in a new room
	private final Sound stepSound;
	private int maxRoom = 0; // to check if player was in a specific room

	private final GameInstance gameInstance;

	public void setCurrentRoom(Room currentRoom) {
		this.currentRoom = currentRoom;
	}
	public Room getCurrentRoom() {
		return currentRoom;
	}

	public PlayerLabel(Grid grid, LabelStyle style, int gridPosX, int gridPosY, Room currentRoom, int health, int attackDamage, int gold, GameInstance gameInstance) {
		super(playerCharacter, style);
		playerCharacter = CurrentPlayer.getCurrentPlayer().getPlayerCharacter();
		this.gameInstance = gameInstance;
		gameInstance.setGold(gold);
		if(playerCharacter.equals("")){
			playerCharacter = DEFAULT_PLAYER_CHARACTER;
		}

		String filteredCharacter = convertUnicodeToEmoji(playerCharacter);
		this.setText(filteredCharacter, GameObjectLabel.isEmoji(filteredCharacter));

		this.gridPosX = gridPosX;
		this.gridPosY = gridPosY;
		this.currentRoom = currentRoom;
		grid.setGridCharacter(gridPosY, gridPosX, this);
		this.previousCharacter = ROOM_CHARACTER;
		this.health = health;
		this.attackDamage = attackDamage;
		this.gold = gold;

		this.stepSound = Gdx.audio.newSound(Gdx.files.internal("audio/Step.wav"));
	}

	private static String convertUnicodeToEmoji(String input) {
		Pattern pattern = Pattern.compile("\\\\u([0-9A-Fa-f]{4})");
		Matcher matcher = pattern.matcher(input);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			int codePoint = Integer.parseInt(matcher.group(1), 16);
			matcher.appendReplacement(sb, new String(Character.toChars(codePoint)));
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/**
	 * Handles character movement
	 */
	public void characterControl(Grid grid, GameStateManager gsm, PlayState playState) {
		if (poisonDuration == 0) PlayState.setHealthTextColor(Color.WHITE);
		Label direction;
		int dirDeltaX = 0;
		int dirDeltaY = 0;

		{
			if ((Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) && (gridPosY + 1 < Grid.ROWS)) {
				dirDeltaY = 1;
			} else if ((Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || Gdx.input.isKeyJustPressed(Input.Keys.S)) && (gridPosY > 0)) {
				dirDeltaY = -1;
			} else if ((Gdx.input.isKeyJustPressed(Input.Keys.LEFT) || Gdx.input.isKeyJustPressed(Input.Keys.A)) && (gridPosX > 0)) {
				dirDeltaX = -1;
			} else if ((Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || Gdx.input.isKeyJustPressed(Input.Keys.D)) && (gridPosX + 1 < Grid.COLUMNS)) {
				dirDeltaX = 1;
			}
			direction = grid.getGrid()[gridPosY + dirDeltaY][gridPosX + dirDeltaX];
		}

		if(isWalkable(direction)) {
			damage(0);

			stepSound.play(volume / 2);
			if(direction instanceof PathLabel) onPath = true;

			grid.setGridCharacter(gridPosY, gridPosX, labelFromCharacter(previousCharacter));
			gridPosX += dirDeltaX;
			gridPosY += dirDeltaY;
			collectItems(direction, grid);
			grid.setGridCharacter(gridPosY, gridPosX, this);
			grid.updateEnemies();
			if(onPath && !(direction instanceof PathLabel)){
				checkNewRoom(grid, playState, grid.getPlayer().getCurrentRoom().getRoomNumber());
			}
			if(!(direction instanceof PathLabel)) onPath = false;
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			attackEnemies(grid, gridPosX, gridPosY);
			grid.updateEnemies();
		}

		if(Gdx.input.isTouched()) {
			if (playState.pauseText.isClicked(Gdx.input.getX(), State.HEIGHT - Gdx.input.getY())) {
				playState.pause();
				gsm.push(new PauseState(gsm, playState));
			}
		}
		//open pause menu
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			playState.pause();
			gsm.push(new PauseState(gsm, playState));
		}

		if(amIDeadYet()) {
			playState.pause();
			gsm.push(new GameOverState(gsm, playState));
		}
	}

	private void checkNewRoom(Grid grid, PlayState playState, int lastRoomNumber) {
		//check room of new position
		Room[] rooms = grid.getRooms();
		Room lastRoom = rooms[lastRoomNumber];
		if(lastRoomNumber > 0) {
			lastRoomNumber--;
		}
		for (int i=lastRoomNumber; i<lastRoomNumber+3; i++) {
			if (rooms[i].isIn(gridPosX, gridPosY)) {
				currentRoom = rooms[i];
				break;
			}
		}
		if(currentRoom.getRoomNumber() > maxRoom) {
			maxRoom = currentRoom.getRoomNumber();
			gameInstance.incrementBeatenRooms();
		}
		grid.getPlayer().setCurrentRoom(currentRoom);
		playState.updateCurrentRoomText();

		// Set EnemyState to AWAKE/IDLE when entering/leaving a room
		for(EnemyLabel enemy: lastRoom.getEnemies()) {
			enemy.setState(EnemyLabel.EnemyState.IDLE);
		}
		for(EnemyLabel enemy: currentRoom.getEnemies()) {
			enemy.setState(EnemyLabel.EnemyState.AWAKE);
		}
	}

	private void collectItems(Label label, Grid grid) {

		if(label instanceof SwordLabel) {
			previousCharacter = ROOM_CHARACTER;
			setAttackDamage(grid.getGrid()[gridPosY][gridPosX].getObjectValue());
		} else if(label instanceof HealthLabel) {
			previousCharacter = ROOM_CHARACTER;
			setHealth(grid.getGrid()[gridPosY][gridPosX].getObjectValue());
		} else if(label instanceof LevelLabel) {
			setInformation("New Level");
		} else {
			previousCharacter = label.getText().toString();
		}

	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health += health;
		setInformation("Health Potion collected with value: " + health);
	}

	public int getAttackDamage() {
		return attackDamage;
	}


	// Handles attack damage, when a new weapon is taken and converts weapon to gold, if it is weaker
	public void setAttackDamage(int attackDamage) {
		if(this.attackDamage < attackDamage) {
			this.attackDamage = attackDamage;
			setInformation("Sword collected with value: " + attackDamage);
		} else {
			this.gold += attackDamage;
			setInformation("Sword was converted into gold");
		}
	}

	public int getGold() {
		return gold;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
		clearInformation();
	}

	public void clearInformation() {
		if(!information.isEmpty()) {
			Timer.schedule(new Timer.Task() {
				@Override
				public void run() {
					information = "";
				}
			}, 3);
		}
	}

	private void attackEnemies(Grid grid, int gridPosX, int gridPosY) {
		EnemyLabel enemy;
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				if(gridPosX + j >= 0 && gridPosY + i >= 0) {
					if (grid.getGrid()[i + gridPosY][j + gridPosX] instanceof EnemyLabel) {
						enemy = (EnemyLabel) grid.getGrid()[i + gridPosY][j + gridPosX];
						attack(attackDamage, enemy, grid);
						enemy.attack(this, enemy.getDamage());
					}
				}
			}
		}
	}

	/**
	 * Handle damage taken and game over if health is depleted
	 * @param damage amount of damage taken
	 */
	public void damage(int damage) {
		health -= damage;
		if(poisonDuration > 0) {
			health -= 3; //poison damage
			poisonDuration--;
		}
	}

	private boolean gameSaved = false;
	private void gameFinishedDataHandler() {
		if(gameSaved){ // should not happen but just in case
			return;
		}
		gameInstance.setGold(gold);
		gameInstance.setGameFinished(true);
		CurrentPlayer.getCurrentPlayer().playedGame(gameInstance);
		gameSaved = true;
	}

	private void attack(int damage, EnemyLabel target, Grid grid) {
		target.damage(damage);
		if(target.getHealth() <= 0) {
			grid.removeEnemy(target);
			grid.setGridCharacter(target.getGridPosY(), target.getGridPosX(), new RoomLabel(Constants.STYLE));
			gold += (int)(Math.random() * 20); //Loot drop
			gameInstance.setKills(gameInstance.getKills() + 1);
		}
	}

	public void poison(int duration) {
		poisonDuration = duration;
	}

	public void loseGold(int amount) {
		gold -= amount;
	}

	private boolean isWalkable(Label dest) {
		return dest instanceof RoomLabel || dest instanceof ItemLabel || dest instanceof PathLabel || dest instanceof LevelLabel;
	}

	private GameObjectLabel labelFromCharacter(String character) {
		switch(character) {
			case ROOM_CHARACTER: return new RoomLabel(Constants.STYLE);
			case PATH_CHARACTER: return new PathLabel(Constants.STYLE);
			default: throw new IllegalArgumentException("Unsupported character");
		}
	}

	private boolean amIDeadYet() {
		if(health <= 0) {
			PlayState.pauseMusic();
			Sound gameOverSound = Gdx.audio.newSound(Gdx.files.internal("audio/GameOverSound.wav"));
			gameOverSound.play(volume * 0.6f);
			System.out.println(gameInstance.getDurationInSeconds());
			gameFinishedDataHandler();
			return true;
		}
		return false;
	}

	public int getGridPosX() {
		return gridPosX;
	}

	public int getGridPosY() {
		return gridPosY;
	}

	public void dispose() {

	}
}
