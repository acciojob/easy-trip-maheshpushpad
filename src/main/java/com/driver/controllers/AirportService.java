package com.driver.controllers;

import com.driver.controllers.AirportRepo;
import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AirportService {
    @Autowired
    AirportRepo airportRepo = new AirportRepo();

    public String getLargestAirportName() {
        return airportRepo.getLargestAirportName();
    }

    public void addAirport(Airport airport)  {
        airportRepo.addAirport(airport);
    }

    public void addFlight(Flight flight)  {
        airportRepo.addFlight(flight);
    }

    public void addPassenger(Passenger passenger) {
        airportRepo.addPassenger(passenger);
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity) {
        return airportRepo.getShortestDurationOfPossibleBetweenTwoCities(fromCity,toCity);
    }

    public String bookATicket(Integer flightId, Integer passengerId) {
        return airportRepo.bookATicket(flightId,passengerId);
    }

    public String cancelATicket(Integer flightId, Integer passengerId) {
        return airportRepo.cancelATicket(flightId,passengerId);
    }

    public String getAirportNameFromFlightId(Integer flightId) {
        return airportRepo.getAirportNameFromFlightId(flightId);
    }

    public int calculateFlightFare(Integer flightId) {
        return airportRepo.calculateFlightFare(flightId);
    }

    public int getNumberOfPeopleOn(Date date, String airportName) {
        return airportRepo.getNumberOfPeopleOn(date,airportName);
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId) {
        return airportRepo.countOfBookingsDoneByPassengerAllCombined(passengerId);
    }

    public int calculateRevenueOfAFlight(Integer flightId) {
        return airportRepo.calculateRevenueOfAFlight(flightId);
    }
}