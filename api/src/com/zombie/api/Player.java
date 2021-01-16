package com.zombie.api;

import java.io.Serializable;

public class Player implements Serializable {
    private int health;
    private int score;

    public Player(int health, int score, PlayerStatus playerStatus) {
        this.health = health;
        this.score = score;
        this.playerStatus = playerStatus;
    }

    private PlayerStatus playerStatus;

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public PlayerStatus getPlayerStatus() {
        return playerStatus;
    }

    public void setPlayerStatus(PlayerStatus playerStatus) {
        this.playerStatus = playerStatus;
    }
}
