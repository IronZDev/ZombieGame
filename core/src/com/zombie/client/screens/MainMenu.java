package com.zombie.client.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
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
import com.zombie.client.utils.Constants;


public class MainMenu implements Screen, InputProcessor {
    private Viewport viewport;
    private Stage stage;
    private Table table;
    private Main game;
    private BitmapFont fontNotActive, fontActive;
    private int choice = 1;
    private Label play, scoreboard, exit;
    Label.LabelStyle labelStyleActive, labelStyleNotActive;
    private boolean toExit = false;
    private Texture background = new Texture(Gdx.files.internal(Constants.MAIN_MENU_IMAGE_PATH));

    private SpriteBatch batch;

    public MainMenu(Main game){
        this.game = game;
        Gdx.input.setInputProcessor(this);
        batch = new SpriteBatch();
        viewport = new ScalingViewport(Scaling.stretch, Constants.GAME_WIDTH, Constants.GAME_HEIGHT,
                new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT));
        stage = new Stage(viewport, batch);

        background = new Texture(Gdx.files.internal(Constants.MAIN_MENU_IMAGE_PATH));
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
    }

    @Override
    public void show() {
        labelStyleNotActive = new Label.LabelStyle();
        labelStyleNotActive.font = fontNotActive;
        labelStyleActive = new Label.LabelStyle();
        labelStyleActive.font = fontActive;

        play = new Label("Play", labelStyleActive);
        scoreboard = new Label("Scoreboard", labelStyleNotActive);
        exit = new Label("Exit", labelStyleNotActive);

        table = new Table();
        table.add(play).padTop(250);
        table.row().padTop(25);
        table.add(scoreboard);
        table.row().padTop(25);
        table.add(exit);
        table.center();
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
        if(toExit){
            dispose();
            Gdx.app.exit();
        }
    }

    public void updateGraphics(){
        play.setStyle(labelStyleNotActive);
        exit.setStyle(labelStyleNotActive);
        scoreboard.setStyle(labelStyleNotActive);
        if (choice == 1)
            play.setStyle(labelStyleActive);
        else if (choice == 2)
            scoreboard.setStyle(labelStyleActive);
        else if (choice == 3)
            exit.setStyle(labelStyleActive);
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
    public boolean keyDown(int keycode) {
        if (keycode==Input.Keys.UP && choice>1)
            choice--;
        if (keycode==Input.Keys.DOWN && choice<3)
            choice++;
        if (keycode==Input.Keys.SPACE || keycode==Input.Keys.ENTER){
            if (choice == 1) {
                dispose();
                game.setScreen(new GameScreen(game));
            }
            if (choice == 2) {
                dispose();
                game.setScreen(new Scoreboard(game));
            }
            if (choice == 3)
                toExit=true;
        }
        return false;
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
