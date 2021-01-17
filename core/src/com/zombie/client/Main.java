package com.zombie.client;

import com.badlogic.gdx.Game;
import com.zombie.api.GameService;
import com.zombie.api.GameSessionException;
import com.zombie.client.screens.MainMenu;

public class Main extends Game {
	private GameService service;

	public Main(GameService service) {
		this.service = service;
	}

	public GameService getGameService() {
		return service;
	}

	@Override
	public void create () {
		setScreen(new MainMenu(this));
	}


}
