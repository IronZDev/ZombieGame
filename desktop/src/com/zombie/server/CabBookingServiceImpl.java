package com.zombie.server;

import com.zombie.api.Booking;
import com.zombie.api.GameSessionException;
import com.zombie.api.CabBookingService;

import static java.lang.Math.random;
import static java.util.UUID.randomUUID;

public class CabBookingServiceImpl implements CabBookingService {

    @Override public Booking bookRide(String pickUpLocation) throws GameSessionException {
        if (random() < 0.3) throw new GameSessionException("Cab unavailable");
        return new Booking(randomUUID().toString());
    }
}
