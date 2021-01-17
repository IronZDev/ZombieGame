package com.zombie.api;

import java.util.ArrayList;

public interface GameService {
    int connectPlayer() throws GameSessionException;
    void disconnectPlayer(int ID) throws GameSessionException;
    Player getPlayer(int ID) throws GameSessionException;
    ArrayList<Player> getPlayerList() throws GameSessionException;
    void setHealth(int playerID, int health) throws GameSessionException;
    void setState(int playerID, PlayerStatus status) throws GameSessionException;
    void setScore(int playerID, int score) throws GameSessionException;
}
