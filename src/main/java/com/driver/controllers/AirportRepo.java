package com.driver.controllers;

import com.driver.controllers.AirportController;
import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Repository
public class AirportRepo {
    HashMap<String, Airport> airportDB = new HashMap<>();
    HashMap<Integer,Flight> flightDB = new HashMap<>();
    HashMap<Integer,Passenger> passangerdDB = new HashMap<>();
    HashMap<Integer, Set<Integer>>ticketDB = new HashMap<>();


    public void addAirport(Airport airport)  {
        String name = airport.getAirportName();
        airportDB.put(name,airport);
    }
    public  String getLargestAirportName() {
        String airport_name = "";
        int largest = 0;
        for(String ct : airportDB.keySet()) {
            Airport ap = airportDB.get(ct);
            if(ap.getNoOfTerminals() > largest){
                airport_name = ap.getAirportName();
                largest = ap.getNoOfTerminals();
            }else if(ap.getNoOfTerminals() == largest) {
                int res = ap.getAirportName().compareTo(airport_name);
                if(res < 0){
                    airport_name = ap.getAirportName();
                }
            }
        }
        return airport_name;

    }

    public void addFlight(Flight flight)  {
        int id = flight.getFlightId();
        flightDB.put(id,flight);
    }

    public void addPassenger(Passenger passenger) {
        int passengerId = passenger.getPassengerId();
        passangerdDB.put(passengerId,passenger);
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity) {
        double sortestDur = Double.MAX_VALUE;
        boolean flag = true;
        for(int key : flightDB.keySet()) {
            Flight fl = flightDB.get(key);
            if(fl.getFromCity().equals(fromCity) && fl.getToCity().equals(toCity)) {
                sortestDur = Math.min(sortestDur,fl.getDuration());
                flag = false;
            }
        }
        if(flag)return -1;
        return sortestDur;
    }

    public String bookATicket(Integer flightId, Integer passengerId) {
        if(ticketDB.containsKey(flightId)) {
            Set<Integer> pl = ticketDB.get(flightId);
            if(pl.size() > flightDB.get(flightId).getMaxCapacity()){
                return "FAILURE";
            }else if(pl.contains(passengerId)) {
                return "FAILURE";
            }else {
                pl.add(passengerId);
                ticketDB.put(flightId,pl);
            }
        }else {
            Set<Integer> pl = new HashSet<>();
            pl.add(passengerId);
            ticketDB.put(flightId,pl);
        }
        return "SUCCESS";
    }

    public String cancelATicket(Integer flightId, Integer passengerId) {
        if(ticketDB.get(flightId)== null){
            return "FAILURE";
        }
        if(ticketDB.containsKey(flightId)) {
            if(ticketDB.get(flightId).contains(passengerId)) {
                ticketDB.get(flightId).remove(passengerId);
                return "SUCCESS";
            }
        }
        return "FAILURE";

    }

    public String getAirportNameFromFlightId(Integer flightId) {
        Flight fl = flightDB.get(flightId);
        if(fl == null){
            return null;
        }
        String ct = String.valueOf(fl.getFromCity());
        for (String aiport : airportDB.keySet()) {
            Airport ar = airportDB.get(aiport);
            if(ar.getCity().equals(flightDB.get(flightId).getFromCity())) {
                return ar.getAirportName();
            }
        }
        return null;
    }

    public int calculateFlightFare(Integer flightId) {
        int noOfPeople = ticketDB.get(flightId).size();
        return 3000+(noOfPeople*50);
    }

    public int getNumberOfPeopleOn(Date date, String airportName) {
        Airport airport = airportDB.get(airportName);
        if(airport==null)
            return 0;

        String cityName = String.valueOf(airport.getCity());
        int sum = 0;

        if(passangerdDB.isEmpty())
            return 0;

        for(int flightId: passangerdDB.keySet()){

            Flight flight = flightDB.get(flightId);

            if(flight==null)
                continue;


            if(!flight.getFlightDate().equals(date)){
                continue;
            }

            String fCity = String.valueOf(flight.getFromCity());
            String tCity = String.valueOf(flight.getToCity());

            if(!fCity.equals(cityName) && !tCity.equals(cityName)) {
                continue;
            }
            if(ticketDB.get(flightId)==null) {
                continue;
            }
            sum+= ticketDB.get(flightId).size();


        }
        return sum;

    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId) {
        int cnt = 0;
        for(int id : flightDB.keySet()) {
            Set<Integer>st = ticketDB.get(id);
            if(st.contains(passengerId)) {
                cnt++;
            }
        }
        return cnt;
    }

    public int calculateRevenueOfAFlight(Integer flightId) {
        Set<Integer> passangerList = ticketDB.get(flightId);
        if(passangerList == null || passangerList.size() == 0) {
            return 0;
        }
        int n = passangerList.size()-1;
        return  3000 + ((n*(n+1))/2) * 50;
    }
}