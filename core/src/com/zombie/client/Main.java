package com.zombie.client;

import com.badlogic.gdx.Game;
import com.zombie.api.GameService;
import com.zombie.api.GameSessionException;
import com.zombie.client.screens.MainMenu;

public class Main extends Game {
	private GameService service;

	public Main(GameService service) {
		this.service = service;
		try {
			int ID = service.connectPlayer();
			System.out.println(ID);
		} catch (GameSessionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void create () {
		setScreen(new MainMenu(this));
	}


}
