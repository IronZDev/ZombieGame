package com.zombie.api;

public interface CabBookingService {
    Booking bookRide(String pickUpLocation) throws BookingException;
}
