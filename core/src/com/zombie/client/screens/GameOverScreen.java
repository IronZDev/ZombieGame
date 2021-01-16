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
import com.zombie.client.Main;
import com.zombie.client.actors.ScoreCounter;
import com.zombie.client.utils.Constants;

import java.util.ArrayList;

public class GameOverScreen implements Screen, InputProcessor {
    private Viewport viewport;
    private Stage stage;
    private Table table;
    private Main game;
    private BitmapFont fontNotActive, fontActive;
    private int choice = 1;
    private Label score, retry, returnMenu;
    Label.LabelStyle labelStyleActive, labelStyleNotActive;
    private Texture background;

    private SpriteBatch batch;

    public GameOverScreen(Main game){
        this.game = game;
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
        retry = new Label("Retry", labelStyleActive);
        returnMenu = new Label("Return to menu", labelStyleNotActive);

        table = new Table();
        table.add(score).padBottom(350);
        table.row().padTop(25);
        table.add(retry);
        table.row().padTop(10);
        table.add(returnMenu).padBottom(75);
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
        retry.setStyle(labelStyleNotActive);
        returnMenu.setStyle(labelStyleNotActive);
        if (choice == 1)
            retry.setStyle(labelStyleActive);
        else if (choice == 2)
            returnMenu.setStyle(labelStyleActive);
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
        if (keycode==Input.Keys.UP && choice>1)
            choice--;
        if (keycode==Input.Keys.DOWN && choice<2)
            choice++;
        if (keycode==Input.Keys.SPACE || keycode==Input.Keys.ENTER){
            dispose();
            if (choice == 1)
                game.setScreen(new GameScreen(game));
            if (choice == 2)
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
