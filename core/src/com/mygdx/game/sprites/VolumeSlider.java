package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.Application;
import com.mygdx.game.sprites.font.Font;
import com.mygdx.game.states.PlayState;

import static com.mygdx.game.sprites.Constants.*;

public class VolumeSlider extends Slider {

    private final Text volumeText;
    private final float x, y, width, height;
    private final Stage stage;
    private final BitmapFont font = Font.getBitmapFont();

    public VolumeSlider(float x, float y, float width, float height, float min, float max, float stepSize, boolean vertical, Stage stage) {
        super(min, max, stepSize, vertical, new Skin(Gdx.files.internal("uiskin.json")));
        this.stage = stage;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        volumeText = new Text("Volume", x, y, font, true);
        createSlider();
    }

    private void createSlider() {
        setValue(volume);
        setColor(new Color(255f,255f,255f,1f));
        setPosition(x - width/2, y - 100);
        setSize(width, height);
        stage.addActor(this);
        Gdx.input.setInputProcessor(stage);
        addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                volume = getValue();
                PlayState.setVolume(getValue());
            }
        });
    }

    @Override
    public void draw(Batch sb, float parentAlpha) {
        volumeText.getFont().draw(sb, volumeText.getText(), volumeText.getPosition().x, volumeText.getPosition().y + volumeText.getGlyphLayout().height);
        super.draw(sb, parentAlpha);
    }

    @Override
    public boolean remove() {
        return stage.getActors().removeValue(this, true);
    }
}
