package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.data.CurrentPlayer;
import com.mygdx.game.data.GameInstance;
import com.mygdx.game.sprites.Text;
import com.mygdx.game.sprites.font.Font;

public class GameOverState extends State{
    private final Text gameOverText;
    private final Text retryText;
    private final Text returnToMenuText;
    private final Text closeGameText;
    private final BitmapFont font = Font.getBitmapFont();
    PlayState playState;
    Stage stage;

    public GameOverState(GameStateManager gsm, PlayState playState) {
        super(gsm);
        font.setColor(Color.WHITE); // Otherwise Poison makes gameover screen purple
        stage = new Stage();
        this.playState = playState;
        gameOverText = new Text("GAME OVER", State.WIDTH / 2F, State.HEIGHT * 0.9F, font, true);
        retryText = new Text("Retry", State.WIDTH / 2F, State.HEIGHT * 0.65F - TEXT_HEIGHT, font, true);
        returnToMenuText = new Text("Return to Menu", State.WIDTH / 2F, State.HEIGHT * 0.65F - 2 * TEXT_HEIGHT, font, true);
        closeGameText = new Text("Close Game", State.WIDTH / 2F, State.HEIGHT * 0.65F - 3 * TEXT_HEIGHT, font, true);
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.isTouched()) {
            int x = Gdx.input.getX(), y = HEIGHT - Gdx.input.getY();
            if (retryText.isClicked(x, y)) {
                //new game
                gsm.pop();
                gsm.set(new PlayState(gsm, 1, 100, 5, 0, 0, new GameInstance(CurrentPlayer.getCurrentPlayer())));
            }
            if (returnToMenuText.isClicked(x, y)) {
                //return to menu
                gsm.pop();
                gsm.pop();
            }
            if (closeGameText.isClicked(x, y)) {
                //close game
                Gdx.app.exit();
                System.exit(0);
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();

        gameOverText.getFont().draw(sb, gameOverText.getText(), gameOverText.getPosition().x, gameOverText.getPosition().y + gameOverText.getGlyphLayout().height);
        retryText.getFont().draw(sb, retryText.getText(), retryText.getPosition().x, retryText.getPosition().y + retryText.getGlyphLayout().height);
        returnToMenuText.getFont().draw(sb, returnToMenuText.getText(), returnToMenuText.getPosition().x, returnToMenuText.getPosition().y + returnToMenuText.getGlyphLayout().height);
        closeGameText.getFont().draw(sb, closeGameText.getText(), closeGameText.getPosition().x, closeGameText.getPosition().y + closeGameText.getGlyphLayout().height);

        sb.end();
    }

    @Override
    public void dispose() {

    }
}
