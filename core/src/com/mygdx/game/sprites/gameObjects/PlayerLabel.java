package com.mygdx.game.sprites.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.sprites.Constants;
import com.mygdx.game.sprites.gameObjects.enemies.EnemyLabel;
import com.mygdx.game.sprites.Grid;
import com.mygdx.game.sprites.Room;
import com.mygdx.game.states.GameStateManager;
import com.mygdx.game.states.PauseState;
import com.mygdx.game.states.PlayState;
import com.mygdx.game.states.State;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mygdx.game.sprites.gameObjects.LevelLabel.LEVEL_CHARACTER;
import static com.mygdx.game.sprites.gameObjects.PathLabel.PATH_CHARACTER;
import static com.mygdx.game.sprites.gameObjects.RoomLabel.ROOM_CHARACTER;
import static com.mygdx.game.sprites.gameObjects.items.itemTypes.HealthLabel.HEALTH_CHARACTER;
import static com.mygdx.game.sprites.gameObjects.items.itemTypes.SwordLabel.SWORD_CHARACTER;


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
	private static float SOUND_VOLUME = 0.6f;
	private boolean hasGoneOnPath = false; // to check if its possible that player is in a new room
	private Sound stepSound;

	public void setCurrentRoom(Room currentRoom) {
		this.currentRoom = currentRoom;
	}
	public Room getCurrentRoom() {
		return currentRoom;
	}

	public PlayerLabel(Grid grid, LabelStyle style, int gridPosX, int gridPosY, Room currentRoom, int health, int attackDamage, int gold) {
		super(playerCharacter, style);
		playerCharacter = Gdx.files.local("selectedCharacter.txt").readString().trim();

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

	public void characterControl(Grid grid, GameStateManager gsm, PlayState playState) {

		if ((Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) && (gridPosY + 1 < Grid.ROWS)) {
			stepSound.play(SOUND_VOLUME);

			String topCharacter = grid.getGrid()[gridPosY + 1][gridPosX].getLabelString();

			if(topCharacter.equals(PATH_CHARACTER)){
				hasGoneOnPath = true;
			}

			//TODO: Find better way instead of the very long if - also bad if the add an item/enemy, we need to add to if
			if (topCharacter.equals(ROOM_CHARACTER) || topCharacter.equals(PATH_CHARACTER) || topCharacter.equals(SWORD_CHARACTER) || topCharacter.equals(HEALTH_CHARACTER) || topCharacter.equals(LEVEL_CHARACTER)) {
				damage(0);
				grid.setGridCharacter(gridPosY, gridPosX, previousCharacter);
				gridPosY++;
				collectItems(topCharacter, grid);
				grid.setGridCharacter(gridPosY, gridPosX, this);
				grid.updateEnemies();
				if(hasGoneOnPath && !topCharacter.equals(PATH_CHARACTER)){
					checkNewRoom(grid, playState, grid.getPlayer().getCurrentRoom().getRoomNumber());
				}
				if(!topCharacter.equals(PATH_CHARACTER)) {
					hasGoneOnPath = false;
				}
			}

		}

		if ((Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || Gdx.input.isKeyJustPressed(Input.Keys.S)) && (gridPosY > 0)) {
			stepSound.play(SOUND_VOLUME);

			String bottomCharacter = grid.getGrid()[gridPosY - 1][gridPosX].getLabelString();

			if(bottomCharacter.equals(PATH_CHARACTER)){
				hasGoneOnPath = true;
			}

			//TODO: Find better way instead of the very long if - also bad if the add an item/enemy, we need to add to if
			if (bottomCharacter.equals(ROOM_CHARACTER) || bottomCharacter.equals(PATH_CHARACTER) || bottomCharacter.equals(SWORD_CHARACTER) || bottomCharacter.equals(HEALTH_CHARACTER) || bottomCharacter.equals(LEVEL_CHARACTER)) {
				damage(0);
				grid.setGridCharacter(gridPosY, gridPosX, previousCharacter);
				gridPosY--;
				collectItems(bottomCharacter, grid);
				grid.setGridCharacter(gridPosY, gridPosX, this);
				grid.updateEnemies();
				if(hasGoneOnPath && !bottomCharacter.equals(PATH_CHARACTER)){
					checkNewRoom(grid, playState, grid.getPlayer().getCurrentRoom().getRoomNumber());
				}
				if(!bottomCharacter.equals(PATH_CHARACTER)) {
					hasGoneOnPath = false;
				}
			}

		}

		if ((Gdx.input.isKeyJustPressed(Input.Keys.LEFT) || Gdx.input.isKeyJustPressed(Input.Keys.A)) && (gridPosX > 0)) {
			stepSound.play(SOUND_VOLUME);

			String leftCharacter = grid.getGrid()[gridPosY][gridPosX - 1].getLabelString();
			if(leftCharacter.equals(PATH_CHARACTER)){
				hasGoneOnPath = true;
			}
			//TODO: Find better way instead of the very long if - also bad if the add an item/enemy, we need to add to if
			if (leftCharacter.equals(ROOM_CHARACTER) || leftCharacter.equals(PATH_CHARACTER) || leftCharacter.equals(SWORD_CHARACTER) || leftCharacter.equals(HEALTH_CHARACTER) || leftCharacter.equals(LEVEL_CHARACTER)) {
				damage(0);
				grid.setGridCharacter(gridPosY, gridPosX, previousCharacter);
				gridPosX--;
				collectItems(leftCharacter, grid);
				grid.setGridCharacter(gridPosY, gridPosX, this);
				grid.updateEnemies();
				if(hasGoneOnPath && !leftCharacter.equals(PATH_CHARACTER)){
					checkNewRoom(grid, playState, grid.getPlayer().getCurrentRoom().getRoomNumber());
				}
				if(!leftCharacter.equals(PATH_CHARACTER)) {
					hasGoneOnPath = false;
				}
			}

		}

		if ((Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || Gdx.input.isKeyJustPressed(Input.Keys.D)) && (gridPosX + 1 < Grid.COLUMNS)) {
			stepSound.play(SOUND_VOLUME);

			String rightCharacter = grid.getGrid()[gridPosY][gridPosX + 1].getLabelString();
			if(rightCharacter.equals(PATH_CHARACTER)){
				hasGoneOnPath = true;
			}
			//TODO: Find better way instead of the very long if - also bad if the add an item/enemy, we need to add to if
			if (rightCharacter.equals(ROOM_CHARACTER) || rightCharacter.equals(PATH_CHARACTER) || rightCharacter.equals(SWORD_CHARACTER) || rightCharacter.equals(HEALTH_CHARACTER) || rightCharacter.equals(LEVEL_CHARACTER)) {
				damage(0);
				grid.setGridCharacter(gridPosY, gridPosX, previousCharacter);
				gridPosX++;
				collectItems(rightCharacter, grid);
				grid.setGridCharacter(gridPosY, gridPosX, this);
				grid.updateEnemies();
				if(hasGoneOnPath && !rightCharacter.equals(PATH_CHARACTER)){
					checkNewRoom(grid, playState, grid.getPlayer().getCurrentRoom().getRoomNumber());
				}
				if(!rightCharacter.equals(PATH_CHARACTER)) {
					hasGoneOnPath = false;
				}
			}

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

	}

	private void checkNewRoom(Grid grid, PlayState playState, int lastRoomNumber) {
		//check room of new position
		Room[] rooms = grid.getRooms();
		if(lastRoomNumber > 0) {
			lastRoomNumber--;
		}
		for (int i=lastRoomNumber; i<lastRoomNumber+3; i++) {
			if (rooms[i].isIn(gridPosX, gridPosY)) {
				currentRoom = rooms[i];
				break;
			}
		}
		grid.getPlayer().setCurrentRoom(currentRoom);
		playState.updateCurrentRoomText();
	}

	private void collectItems(String character, Grid grid) {

		switch (character) {
			case SWORD_CHARACTER: previousCharacter = ROOM_CHARACTER; setAttackDamage(grid.getGrid()[gridPosY][gridPosX].getObjectValue()); break;
			case HEALTH_CHARACTER: previousCharacter = ROOM_CHARACTER; setHealth(grid.getGrid()[gridPosY][gridPosX].getObjectValue()); break;
			case LEVEL_CHARACTER: setInformation("New Level");
			default: previousCharacter = character;
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
				if(gridPosX + j < 0 || gridPosY + i < 0) break;
				if(grid.getGrid()[i + gridPosY][j + gridPosX] instanceof EnemyLabel) {
					enemy = (EnemyLabel) grid.getGrid()[i + gridPosY][j + gridPosX];
					attack(attackDamage, enemy, grid);
					enemy.attack(this, enemy.getDamage());
				}
			}
		}
	}

	public void damage(int damage) {
		health -= damage;
		if(poisonDuration > 0) {
			health -= 3; //poison damage
			poisonDuration--;
		}
		if(health <= 0) {
			System.out.println("GAME OVER :(((((( 👀🎂🤞😢🐱‍👓😆");
		}
	}

	private void attack(int damage, EnemyLabel target, Grid grid) {
		target.damage(damage);
		if(target.getHealth() <= 0) {
			grid.removeEnemy(target);
			grid.setGridCharacter(target.getGridPosY(), target.getGridPosX(), new RoomLabel(Constants.STYLE));
		}
	}

	public void poison(int duration) {
		poisonDuration = duration;
	}

	public void loseGold(int amount) {
		gold -= amount;
	}

	public void dispose() {

	}
}
