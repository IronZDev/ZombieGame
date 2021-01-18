package com.zombie.client.screens;


import com.zombie.client.Main;
import com.zombie.client.stages.MultiPlayerGameStage;

public class GameScreenMultiplayer extends GameScreen {

    public GameScreenMultiplayer(Main game, int playerID) {
        super(game);
        stage = new MultiPlayerGameStage(game.getGameService(), playerID);
    }

}
