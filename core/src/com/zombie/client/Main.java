package com.zombie.client;

import com.badlogic.gdx.Game;
import com.zombie.api.Booking;
import com.zombie.api.GameSessionException;
import com.zombie.api.CabBookingService;
import com.zombie.client.screens.MainMenu;

public class Main extends Game {
	private CabBookingService service;

	public Main(CabBookingService service) {
		this.service = service;
		try {
			Booking booking = service.bookRide("Street 12321");
			System.out.println(booking.toString());
		} catch (GameSessionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void create () {
		setScreen(new MainMenu(this));
	}


}
