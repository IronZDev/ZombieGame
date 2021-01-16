package com.zombie.client;

import com.badlogic.gdx.Game;
import com.zombie.client.screens.MainMenu;

import java.util.Collections;

public class Main extends Game {


	public Main() {
	}

	@Override
	public void create () {
		setScreen(new MainMenu(this));
	}


}
