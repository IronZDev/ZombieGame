package com.zombie.client.stages;

import com.zombie.api.GameService;
import com.zombie.api.GameSessionException;
import com.zombie.api.Player;
import com.zombie.client.actors.HealthCounter;
import com.zombie.client.actors.OpponentHealthCounter;
import com.zombie.client.actors.OpponentScoreCounter;
import com.zombie.client.actors.ScoreCounter;
import com.zombie.client.utils.AssetManager;

import java.util.ArrayList;

public class MultiPlayerGameStage extends GameStage {
    private GameService service;
    private int playerID;
    public MultiPlayerGameStage(GameService service, int ID) {
        super();
        this.service = service;
        this.playerID = ID;
    }

    @Override
    public void setUpWorld() {
        super.setUpWorld();
        setUpOpponent();
    }

    private void setUpOpponent() {
        addActor(new OpponentHealthCounter(AssetManager.getTextureAtlas(), AssetManager.getFont()));
        addActor(new OpponentScoreCounter(AssetManager.getFont()));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        updateOpponent();
        updatePlayer();
    }

    private void updateOpponent() {
        try {
            ArrayList<Player> players = service.getPlayerList();
            Player enemy;
            if (playerID == 0) {
                enemy = players.get(1);
            } else {
                enemy = players.get(0);
            }
            OpponentHealthCounter.health = enemy.getHealth();
            OpponentScoreCounter.score = enemy.getScore();
        } catch (GameSessionException ex) {
            ex.printStackTrace();
        }
    }

    private void updatePlayer() {
        try {
            service.setHealth(playerID, HealthCounter.health);
            service.setScore(playerID, ScoreCounter.score);
        } catch (GameSessionException ex) {
            ex.printStackTrace();
        }
    }
}
