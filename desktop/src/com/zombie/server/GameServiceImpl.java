package com.zombie.server;

import com.zombie.api.GameService;
import com.zombie.api.GameSession;
import com.zombie.api.GameSessionException;
import com.zombie.api.Player;

import java.util.ArrayList;

public class GameServiceImpl implements GameService {
    private static GameSession gameSession;

    public GameServiceImpl() {
        gameSession = new GameSession();
    }

    @Override
    public int connectPlayer() throws GameSessionException {
        return gameSession.connectPlayer();
    }

    @Override
    public void disconnectPlayer(int ID) throws GameSessionException {
        ArrayList<Player> players =  gameSession.getSessionInfo();
        try {
            Player player = players.get(ID);
            if (player == null) {
                throw new GameSessionException("No player with ID: " + ID);
            }
        } catch (IndexOutOfBoundsException ex) {
            throw new GameSessionException("No player with ID: " + ID);
        }
        gameSession.disconnectPlayer(ID);
    }

    @Override
    public Player getPlayer(int ID) throws GameSessionException {
        ArrayList<Player> players =  gameSession.getSessionInfo();
        try {
            Player player = players.get(ID);
            if (player == null) {
                throw new GameSessionException("No player with ID: " + ID);
            }
            return player;
        } catch (IndexOutOfBoundsException ex) {
            throw new GameSessionException("No player with ID: " + ID);
        }
    }

    @Override
    public ArrayList<Player> getPlayerList() throws GameSessionException {
        return gameSession.getSessionInfo();
    }
}
