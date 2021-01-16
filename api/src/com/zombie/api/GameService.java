package com.zombie.api;

import java.util.ArrayList;

public interface GameService {
    int connectPlayer() throws GameSessionException;
    void disconnectPlayer() throws GameSessionException;
    Player getPlayer() throws GameSessionException;
    ArrayList<Player> getPlayerList() throws GameSessionException;
}
