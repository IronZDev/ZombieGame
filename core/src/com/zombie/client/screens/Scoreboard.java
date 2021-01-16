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
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.zombie.client.Main;
import com.zombie.client.utils.Constants;


public class Scoreboard implements Screen, InputProcessor {
    private Viewport viewport;
    private Stage stage;
    private Table table;
    private Container titleContainer, returnContainer;
    private Main game;
    private BitmapFont fontNotActive, fontActive, fontTitle;
    Label.LabelStyle labelStyleActive, labelStyleNotActive, labelStyleTitle;
    private Texture background;

    private SpriteBatch batch;

    public Scoreboard(Main game){
        this.game = game;
        batch = new SpriteBatch();
        viewport = new ScalingViewport(Scaling.stretch, Constants.GAME_WIDTH, Constants.GAME_HEIGHT,
                new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT));
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(this);

        background = new Texture(Gdx.files.internal(Constants.SCOREBOARD_IMAGE_PATH));
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
        parameter.size = 50;
        fontTitle = generator.generateFont(parameter);
        generator.dispose();
        labelStyleNotActive = new Label.LabelStyle();
        labelStyleNotActive.font = fontNotActive;
        labelStyleActive = new Label.LabelStyle();
        labelStyleActive.font = fontActive;
        labelStyleTitle = new Label.LabelStyle();
        labelStyleTitle.font = fontTitle;
    }

    @Override
    public void show() {

        table = new Table();
        Label title = new Label("SCOREBOARD:",labelStyleTitle);
        titleContainer = new Container(title);
        titleContainer.top();
        titleContainer.padTop(100);
        titleContainer.setFillParent(true);
        Preferences prefs = Gdx.app.getPreferences("Scoreboard");
        for (int counter = 1; counter<=5; ++counter)
            if(prefs.getInteger(Integer.toString(counter), 0)>0){
                Label score = new Label(Integer.toString(counter)+". "+prefs.getInteger(Integer.toString(counter), 0),labelStyleNotActive);
                table.add(score);
                table.row();
            }
        Label returnMenu = new Label("Press any key to return to main menu...", labelStyleActive);
        returnContainer = new Container(returnMenu);
        returnContainer.bottom();
        returnContainer.padBottom(50);
        returnContainer.setFillParent(true);
        table.center();
        table.setFillParent(true);

        stage.addActor(returnContainer);
        stage.addActor(titleContainer);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(new TextureRegion(background), 0, 0, Constants.GAME_WIDTH,
                Constants.GAME_HEIGHT);
        batch.end();
        stage.act();
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
        fontTitle.dispose();
        fontActive.dispose();
        fontNotActive.dispose();
        titleContainer.remove();
        returnContainer.remove();
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
        dispose();
        game.setScreen(new MainMenu(game));
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
