package com.zombie.client.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.zombie.client.Main;
import com.zombie.client.actors.HealthCounter;
import com.zombie.client.actors.ScoreCounter;
import com.zombie.client.stages.GameStage;
import com.zombie.client.stages.MultiPlayerGameStage;
import com.zombie.client.utils.Constants;

public class GameScreen implements Screen {
    public GameStage stage;
    public Main game;

    private BitmapFont font100, font24;
    private Stage stagePause;
    private SpriteBatch batch;
    private Container pauseContainer, resumeContainer;
    private Label resume, pause;

    public GameScreen(Main game) {
        stage = new GameStage();
        this.game = game;
        HealthCounter.health = Constants.HEALTH;
        ScoreCounter.score = 0;
        setUpPause();
    }

    public void setUpPause() {
        batch = new SpriteBatch();
        Viewport viewport = new ScalingViewport(Scaling.stretch, Constants.GAME_WIDTH, Constants.GAME_HEIGHT,
                new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT));
        stagePause = new Stage(viewport, batch);

        //Fonts
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(Constants.FONT_PATH));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        parameter.borderWidth = 1;
        parameter.color = Color.WHITE;
        parameter.shadowOffsetX = 2;
        parameter.shadowOffsetY = 2;
        parameter.shadowColor = Color.RED;
        font24 = generator.generateFont(parameter); // font size 24 pixels
        parameter.size = 100;
        font100 = generator.generateFont(parameter);
        generator.dispose();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font24;

        resume = new Label("Press escape to resume...", labelStyle);

        labelStyle.font = font100;
        pause = new Label("PAUSE", labelStyle);

        pauseContainer = new Container(pause);
        pauseContainer.center();
        pauseContainer.setFillParent(true);
        pauseContainer.padBottom(250);

        resumeContainer = new Container(resume);
        resumeContainer.center();
        resumeContainer.setFillParent(true);
        resumeContainer.padBottom(-400);

        stagePause.addActor(pauseContainer);
        stagePause.addActor(resumeContainer);
    }

    @Override
    public void render(float delta) {
        //Game Over
        if (stage.gameOver){
            dispose();
            game.setScreen(new GameOverScreen(game));
        }
        //Pause
        else if (stage.isPaused)
            pause();
        //Play
        else{
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            stage.getActors();
            stage.draw();
            stage.act(delta);
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();

        ShapeRenderer renderer = new ShapeRenderer();

        //Transparent mask
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        renderer.setColor(0,0,0,0.7f);
        renderer.rect(0,0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        renderer.end();
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glDisable(GL20.GL_BLEND);
        renderer.dispose();

        stagePause.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            resume();
    }

    @Override
    public void resume() {
        stage.escBlocked = true;
        stage.isPaused = false;
    }

    @Override
    public void dispose()
    {
        stage.clean();
        stage.dispose();
        batch.dispose();
        pauseContainer.remove();
        resumeContainer.remove();
        pause.remove();
        resume.remove();
        font24.dispose();
        font100.dispose();
    }
}
