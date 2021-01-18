package com.zombie.client.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.zombie.api.GameService;
import com.zombie.api.GameSessionException;
import com.zombie.api.Player;
import com.zombie.api.PlayerStatus;
import com.zombie.client.Main;
import com.zombie.client.actors.ScoreCounter;
import com.zombie.client.utils.Constants;

import java.util.ArrayList;

public class GameOverScreenMultiplayer implements Screen, InputProcessor {
    private Viewport viewport;
    private Stage stage;
    private Table table;
    private Main game;
    private BitmapFont fontNotActive, fontActive;
    private int choice = 1;
    private Label score, opponentScore, returnMenu;
    Label.LabelStyle labelStyleActive, labelStyleNotActive;
    private Texture background;

    private SpriteBatch batch;

    private GameService service;
    private int playerID;

    public GameOverScreenMultiplayer(Main game, int playerID){
        this.game = game;
        this.service = game.getGameService();
        this.playerID = playerID;
        Gdx.input.setInputProcessor(this);
        batch = new SpriteBatch();
        viewport = new ScalingViewport(Scaling.stretch, Constants.GAME_WIDTH, Constants.GAME_HEIGHT,
                new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT));
        stage = new Stage(viewport, batch);
        background = new Texture(Gdx.files.internal(Constants.GAME_OVER_IMAGE_PATH));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(Constants.FONT_PATH));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        parameter.borderWidth = 1;
        parameter.color = Color.WHITE;
        fontNotActive = generator.generateFont(parameter); // font size 24 pixels
        parameter.shadowOffsetX = 2;
        parameter.shadowOffsetY = 2;
        parameter.shadowColor = Color.RED;
        fontActive = generator.generateFont(parameter);
        generator.dispose();
        saveScore();
    }

    private void saveScore() {
        Preferences prefs = Gdx.app.getPreferences("Scoreboard");
        ArrayList<Integer> highscores = new ArrayList<Integer>();
        boolean added = false;
        int next = 0;
        for (int counter = 1; counter<=5; ++counter) {
            if(ScoreCounter.score>prefs.getInteger(Integer.toString(counter), 0) && !added) {
                next = prefs.getInteger(Integer.toString(counter), 0);
                highscores.add(ScoreCounter.score);
                added=true;
            }
            else if(next>prefs.getInteger(Integer.toString(counter), 0)) {
                highscores.add(next);
                next = prefs.getInteger(Integer.toString(counter), 0);
            }
            else
                highscores.add(prefs.getInteger(Integer.toString(counter), 0));
        }
        for (int counter = 1; counter<=5; ++counter) {
            prefs.putInteger(Integer.toString(counter), highscores.get(counter-1));
        }
        prefs.flush();
    }

    @Override
    public void show() {
        labelStyleNotActive = new Label.LabelStyle();
        labelStyleNotActive.font = fontNotActive;
        labelStyleActive = new Label.LabelStyle();
        labelStyleActive.font = fontActive;

        score = new Label("Your score: "+ScoreCounter.score, labelStyleActive);
        opponentScore = new Label("Opponent's score:", labelStyleActive);
        returnMenu = new Label("Return to menu", labelStyleActive);

        table = new Table();
        table.add(score).padBottom(350).padRight(50);
        table.add(opponentScore).padBottom(350).padLeft(50);
        table.row().padTop(10);
        table.add(returnMenu).colspan(2).padBottom(75);
        table.bottom();
        table.setFillParent(true);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(new TextureRegion(background), 0, 0, Constants.GAME_WIDTH,
                Constants.GAME_HEIGHT);
        batch.end();
        updateGraphics();
        stage.act();
        stage.draw();
    }

    public void updateGraphics(){
        try {
            Player opponent;
            if (playerID == 0) {
                opponent = service.getPlayer(1);
            } else {
                opponent = service.getPlayer(0);
            }
            if (opponent.getPlayerStatus() == PlayerStatus.PLAYING) {
                opponentScore.setText("Opponent is still playing!");
            } else {
                opponentScore.setText("Opponent's score: "+ opponent.getScore());
            }
        } catch (GameSessionException ex) {
            ex.printStackTrace();
        }
        table.invalidate();
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
        fontActive.dispose();
        fontNotActive.dispose();
        table.remove();
        Array<Actor> activeActors = stage.getActors();
        for (Actor actor : activeActors)
            actor.remove();
        stage.dispose();
        batch.dispose();
        background.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode==Input.Keys.SPACE || keycode==Input.Keys.ENTER){
            try {
                service.setState(playerID, PlayerStatus.DISCONNECTED);
            } catch (GameSessionException ex) {
                ex.printStackTrace();
            }
            dispose();
            game.setScreen(new MainMenu(game));
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
