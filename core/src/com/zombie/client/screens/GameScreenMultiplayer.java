package com.zombie.client.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.zombie.api.GameService;
import com.zombie.api.GameSessionException;
import com.zombie.api.PlayerStatus;
import com.zombie.client.Main;
import com.zombie.client.stages.GameStage;
import com.zombie.client.stages.MultiPlayerGameStage;

public class GameScreenMultiplayer extends GameScreen {
    private GameService service;
    private int playerID;

    public GameScreenMultiplayer(Main game, int playerID) {
        super(game);
        this.service = game.getGameService();
        this.playerID = playerID;
        stage = new MultiPlayerGameStage(service, playerID);
    }

    @Override
    public void render(float delta) {
        //Game Over
        if (stage.gameOver){
            dispose();
            try {
                game.getGameService().setState(playerID, PlayerStatus.GAME_OVER);
                game.setScreen(new GameOverScreenMultiplayer(game, playerID));
            } catch (GameSessionException ex) {
                ex.printStackTrace();
            }
        }
        //Pause
        else if (GameStage.isPaused)
            pause();
            //Play
        else{
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            stage.getActors();
            stage.draw();
            stage.act(delta);
        }
    }
}
