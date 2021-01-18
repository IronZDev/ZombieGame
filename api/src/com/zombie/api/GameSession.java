package com.zombie.api;

import java.util.ArrayList;

public class GameSession {
    static private final ArrayList<Player> playerList = new ArrayList<>();

    public int connectPlayer() throws GameSessionException {
        if (playerList.size() == 0) {
            playerList.add(new Player(3, 0, PlayerStatus.WAITING));
            return 0;
        } else if (playerList.size() == 1){
            playerList.get(0).setPlayerStatus(PlayerStatus.PLAYING);
            playerList.add(new Player(3, 0, PlayerStatus.PLAYING));
            return 1;
        } else {
            throw new GameSessionException("Session is already full!");
        }
    }

    public void disconnectPlayer(int playerID) {
        playerList.remove(playerList.get(playerID));
    }

    public ArrayList<Player> getSessionInfo() {
        return playerList;
    }
}
