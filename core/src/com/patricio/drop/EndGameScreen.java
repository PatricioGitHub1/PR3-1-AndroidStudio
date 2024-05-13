package com.patricio.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;

public class EndGameScreen implements Screen {
    final DropGame game;
    final int gatheredDrops;
    boolean surpassedMaxScore = false;
    Music winSound;
    Music loseSound;
    OrthographicCamera camera;
    Stage stage;
    TextButton playAgainButton;
    Label endGameLabel;
    public Texture backgroundTexture;
    public Sprite backgroundSprite;

    public EndGameScreen(final DropGame game1, int drops) {
        this.game = game1;
        this.gatheredDrops = drops;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        stage = new Stage();
        winSound = Gdx.audio.newMusic(Gdx.files.internal("win-sound.mp3"));
        loseSound = Gdx.audio.newMusic(Gdx.files.internal("lose-sound.mp3"));
        backgroundTexture = new Texture("minecraft-desert.jpg");
        backgroundSprite = new Sprite(backgroundTexture);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        BitmapFont font = new BitmapFont();
        font.getData().setScale(2);
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;

        endGameLabel = new Label("", labelStyle);
        // Calculate the height for text and button
        float visibleHeight = Gdx.graphics.getHeight() * 0.15f;
        float startY = (Gdx.graphics.getHeight() - visibleHeight) / 2f;

        endGameLabel.setAlignment(Align.center); // Center the text
        endGameLabel.setPosition((Gdx.graphics.getWidth() - endGameLabel.getWidth()) / 2f, startY + visibleHeight - endGameLabel.getHeight());
        stage.addActor(endGameLabel);

        setButton();
        playAgainButton.setPosition((Gdx.graphics.getWidth() - playAgainButton.getWidth()) / 2f, (Gdx.graphics.getHeight() - playAgainButton.getHeight()) / 2f - endGameLabel.getHeight() - 20); // Adjust the button position to be below the text

        // si superem el max score:
        if (gatheredDrops > game.maxScore) {
            surpassedMaxScore = true;
            game.maxScore = gatheredDrops;
            endGameLabel.setText("Congratulations for beating your last High Score!\nNew High Score: " + game.maxScore);
            winSound.play();
        } else {
            surpassedMaxScore = false;
            endGameLabel.setText("Unfortunately, you could not beat the previous High Score...");
            loseSound.play();
        }

        playAgainButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Handle button click
                game.setScreen(new GameScreen(game)); // Example: Go to GameScreen again
                dispose();
            }
        });

    }

    public void setButton() {
        Skin skin = new Skin();
        skin.add("button9", new Texture("button.9.png"));

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = skin.getDrawable("button9"); // Use the button texture for the up state

        BitmapFont bitFont = new BitmapFont();
        bitFont.getData().setScale(2);
        style.font = bitFont;

        playAgainButton = new TextButton("Play Again", style);
        stage.addActor(playAgainButton);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        backgroundSprite.draw(game.batch);
        game.batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        winSound.dispose();
        loseSound.dispose();
    }
}
