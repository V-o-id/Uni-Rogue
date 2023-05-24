package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.data.GameInstance;
import com.mygdx.game.data.GamesMap;
import com.mygdx.game.data.PlayerMap;
import com.mygdx.game.data.Playerdata;
import com.mygdx.game.sprites.Text;
import com.mygdx.game.sprites.font.Font;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

public class LeaderboardState extends State {

    private final GamesMap gamesMap = GamesMap.getPlayerMap();
    private final PlayerMap playerMap = PlayerMap.getPlayerMap();
    private final Table table = new Table();
    private final Stage stage = new Stage();
    private final ScrollPane scrollPane;
    private final Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

    private final Text backText;

    private final Text playerStatsText;
    private final Text gameStatsText;

    private final boolean gameStats = true;


    public LeaderboardState(GameStateManager gsm) {
        super(gsm);
        table.setSkin(skin);
        table.defaults().pad(10);
        initFirstRow();
        initTable(gamesMap.getGamesSortedByScore());
        scrollPane = new ScrollPane(table);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setFillParent(true);
        stage.addActor(scrollPane);
        stage.setScrollFocus(scrollPane);
        backText = new Text("Back to Menu", 100, State.HEIGHT/2f, Font.getBitmapFont(), false);
        playerStatsText = new Text("Player Stats", State.WIDTH-300, State.HEIGHT/2f + Font.getBitmapFont().getCapHeight() + 15, Font.getBitmapFont(), true);
        gameStatsText = new Text("Game Stats",  State.WIDTH-300, State.HEIGHT/2f - Font.getBitmapFont().getCapHeight() - 15, Font.getBitmapFont(), true);
        Gdx.input.setInputProcessor(stage);
    }

    private void initFirstRow() {
        Label[] l = new Label[9];
        l[0] = new Label("Nr", skin);
        l[1] = new Label("Name", skin);
        l[2] = new Label("Score", skin);
        l[3] = new Label("Game Won", skin);
        l[4] = new Label("Level", skin);
        l[5] = new Label("Rooms", skin);
        l[6] = new Label("Kills", skin);
        l[7] = new Label("Time", skin);
        l[8] = new Label("Date", skin);

        l[0].setFontScale(1.1f);
        l[0].setColor(1f, 0.5f, 0.5f, 1);
        table.add(l[0]);

        for(int i = 1; i < l.length; i++){
            l[i].setFontScale(1.1f);
            l[i].setColor(1f, 0.5f, 0.5f, 1);
            final int index = i-1;
            l[i].addListener(new ClickListener() {
                @Override
                public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                    realignTable(SortingmethodGames.values()[index]);
                }
            });
            table.add(l[i]);
        }
        table.pad(5);
        table.row();
    }
    enum SortingmethodGames {
        // take care of the order - should be the same as in the table - 1
        NAME, SCORE, GAME_WON, LEVEL, ROOMS, KILLS, TIME, DATE
    }

    private void realignTable(SortingmethodGames method) {
        Set<GameInstance> set;
        switch(method){
            case NAME: set = gamesMap.getGamesSortedByPlayerName(); break;
            case SCORE: set = gamesMap.getGamesSortedByScore(); break;
            case GAME_WON: set = gamesMap.getGamesSortedByGameWon(); break;
            case LEVEL: set = gamesMap.getGamesSortedByLevels(); break;
            case ROOMS: set = gamesMap.getGamesSortedByBeatenRooms(); break;
            case KILLS: set = gamesMap.getGameSortedByKills(); break;
            case TIME: set = gamesMap.getGamesByDuration(); break;
            case DATE: set = gamesMap.getGamesSortedByDateTime(); break;
            default: set = gamesMap.getGamesSortedByScore();
        }
        table.clearChildren();
        initFirstRow();
        initTable(set);
    }

    private void initTable(Set<GameInstance> set) {
        int counter = 1;
        for(GameInstance gI : set){
            createRow(counter, gI.getPlayerName(), gI.getScore(), gI.isGameWon(), gI.getLevel(), gI.getBeatenRooms(), gI.getKills(), gI.getDurationInSeconds(), gI.getStartDateTime());
            counter++;
        }
    }

    private void createRow(int nr, String playerName, int score, boolean gameWon, int level, int beatenRooms, int kills, long durationInSeconds, String date) {
        table.add(nr + ".");
        table.add(playerName);
        table.add(String.valueOf(score));
        table.add(gameWon ? "Won" : "Loose");
        table.add(String.valueOf(level));
        table.add(String.valueOf(beatenRooms));
        table.add(String.valueOf(kills));
        table.add(String.valueOf(durationInSeconds));
        table.add(getFormattedDate(date));
        table.row();
    }
    private static String getFormattedDate(String date) {
        //string to date: Tue May 23 20:48:05 CEST 2023 -> dd.MM.yyyy - HH:mm:ss
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        Date parsedDate = null;
        try {
            parsedDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(parsedDate != null) {
            sdf.applyPattern("dd.MM.yyyy - HH:mm:ss");
            return sdf.format(parsedDate);
        }
        return date;
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            gsm.pop();
        }
        if(Gdx.input.isTouched()){
            if(backText.isClicked(Gdx.input.getX(), HEIGHT - Gdx.input.getY())){
                gsm.pop();
            }
            if(playerStatsText.isClicked(Gdx.input.getX(), HEIGHT - Gdx.input.getY())){
                realignTableToPlayer(SortingmethodPlayers.SCORE);
            }
            if(gameStatsText.isClicked(Gdx.input.getX(), HEIGHT - Gdx.input.getY())){
                realignTable(SortingmethodGames.SCORE);
            }
        }
    }

    private void realignTableToPlayer(SortingmethodPlayers method) {
        Set<Playerdata> set;
        switch(method){
            case NAME: set = playerMap.getPlayersSortedByName(); break;
            case SCORE: set = playerMap.getPlayersSortedByScore(); break;
            case GAMES_PLAYED: set = playerMap.getPlayersSortedByGamesPlayed(); break;
            case GAMES_WON: set = playerMap.getPlayersSortedByGamesWon(); break;
            case LEVELS: set = playerMap.getPlayersSortedByLevels(); break;
            case ROOMS: set = playerMap.getPlayersSortedByBeatenRooms(); break;
            case KILLS: set = playerMap.getPlayersSortedByKills(); break;
            case TIME: set = playerMap.getPlayersSortedByPlaytime(); break;
            case DATE: set = playerMap.getPlayersSortedByDateTime(); break;
            default: set = playerMap.getPlayersSortedByScore();
        }

        for(Playerdata p : set){
            System.out.println(p.getName());
        }

        table.clearChildren();
        initFirstRowPlayer();
        initTablePlayer(set);
    }

    private void initFirstRowPlayer() {
        Label[] l = new Label[10];
        l[0] = new Label("Nr", skin);
        l[1] = new Label("Name", skin);
        l[2] = new Label("Score", skin);
        l[3] = new Label("Games Played", skin);
        l[4] = new Label("Games Won", skin);
        l[5] = new Label("Levels", skin);
        l[6] = new Label("Rooms", skin);
        l[7] = new Label("Kills", skin);
        l[8] = new Label("Playtime", skin);
        l[9] = new Label("Created", skin);

        l[0].setFontScale(1.1f);
        l[0].setColor(1f, 0.5f, 0.5f, 1);
        table.add(l[0]);

        for(int i = 1; i < l.length; i++){
            l[i].setFontScale(1.1f);
            l[i].setColor(1f, 0.5f, 0.5f, 1);
            final int index = i-1;
            l[i].addListener(new ClickListener() {
                @Override
                public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                    realignTableToPlayer(SortingmethodPlayers.values()[index]);
                }
            });
            table.add(l[i]);
        }
        table.pad(5);
        table.row();

    }
    enum SortingmethodPlayers {
        // take care of the order - should be the same as in the table - 1
        NAME, SCORE, GAMES_PLAYED, GAMES_WON, LEVELS, ROOMS, KILLS, TIME, DATE
    }

    private void initTablePlayer(Set<Playerdata> set) {
        int counter = 1;
        for(Playerdata pD : set){
            createRowPlayer(counter, pD.getName(), pD.getTotalScore(), pD.getTotalGamesPlayed(), pD.getTotalGamesWon(), pD.getTotalLevelsCompleted(), pD.getTotalRoomsBeaten(), pD.getTotalKills(), pD.getPlayTimeInSeconds(), pD.getCreationDate());
            counter++;
        }
    }

    private void createRowPlayer(int nr, String playerName, long score, long gamesPlayed, long gamesWon, long level, long beatenRooms, long kills, long durationInSeconds, String date) {
        table.add(nr + ".");
        table.add(playerName);
        table.add(String.valueOf(score));
        table.add(String.valueOf(gamesPlayed));
        table.add(String.valueOf(gamesWon));
        table.add(String.valueOf(level));
        table.add(String.valueOf(beatenRooms));
        table.add(String.valueOf(kills));
        table.add(String.valueOf(durationInSeconds));
        table.add(getFormattedDate(date));
        table.row();
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        stage.act();
        stage.draw();
        backText.getFont().draw(sb, backText.getText(), backText.getPosition().x, backText.getPosition().y + backText.getGlyphLayout().height);
        playerStatsText.getFont().draw(sb, playerStatsText.getText(), playerStatsText.getPosition().x, playerStatsText.getPosition().y + playerStatsText.getGlyphLayout().height);
        gameStatsText.getFont().draw(sb, gameStatsText.getText(), gameStatsText.getPosition().x, gameStatsText.getPosition().y + gameStatsText.getGlyphLayout().height);
        sb.end();
    }

    @Override
    public void dispose() {

    }

}
