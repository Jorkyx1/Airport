/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.controllers;

import airport.controllers.utils.Response;
import airport.controllers.utils.Status;
import airport.model.Flight;
import airport.model.Location;
import airport.model.Passenger;
import airport.model.Plane;
import airport.model.Storage;
import java.time.LocalDateTime;

/**
 *
 * @author USUARIO
 */
public class FlightController {

    public static Response createFlight(String id, String planeId, String departureLocationId, String arrivalLocationId, String scaleLocationId, String year, String month, String day, String hour, String minutes, String hoursDurationsArrival, String minutesDurationsArrival, String hoursDurationsScale, String minutesDurationsScale) {
        Storage storage = Storage.getInstance();

        int hoursDurationArrivalInt, minutesDurationArrivalInt;
        int hoursDurationsScaleint, minutesDurationScaleint;
        int yearInt, monthInt, dayInt, hourInt, minutesInt;
        try {
            if (id == null || id.length() != 6) {
                return new Response("Id must be 6 digits long", Status.BAD_REQUEST);
            }
            for (int i = 0; i < 3; i++) {
                char c = id.charAt(i);
                if (c < 'A' || c > 'Z') {
                    return new Response("Id must start with 3 capital letters", Status.BAD_REQUEST);
                }
            }
            for (int i = 3; i < 6; i++) {
                char c = id.charAt(i);
                if (c < '0' || c > '9') {
                    return new Response("Invalid id", Status.BAD_REQUEST);
                }
            }

            Plane plane;
            if (storage.getPlane(planeId) == null) {
                return new Response("Plane does not exist", Status.BAD_REQUEST);
            } else {
                plane = storage.getPlane(planeId);
            }

            Location departureLocation, arrivalLocation, scaleLocation;
            if (storage.getAirport(departureLocationId) == null) {
                return new Response("Departure location id does not exist", Status.BAD_REQUEST);
            } else {
                departureLocation = storage.getAirport(departureLocationId);
            }

            if (storage.getAirport(arrivalLocationId) == null) {
                return new Response("Arrival location id does not exist", Status.BAD_REQUEST);
            } else {
                arrivalLocation = storage.getAirport(arrivalLocationId);
            }

            try {
                yearInt = Integer.parseInt(year);
                monthInt = Integer.parseInt(month);
                dayInt = Integer.parseInt(day);
                hourInt = Integer.parseInt(hour);
                minutesInt = Integer.parseInt(minutes);
            } catch (NumberFormatException ex) {
                return new Response("Invalid date", Status.BAD_REQUEST);
            }

            LocalDateTime actualDate = LocalDateTime.now();
            LocalDateTime departureDate = LocalDateTime.of(yearInt, monthInt, dayInt, hourInt, minutesInt);
            if (departureDate.isBefore(actualDate)) {
                return new Response("Invalid date", Status.BAD_REQUEST);
            }

            try {
                hoursDurationArrivalInt = Integer.parseInt(hoursDurationsArrival);
                minutesDurationArrivalInt = Integer.parseInt(minutesDurationsArrival);
            } catch (NumberFormatException ex) {
                return new Response("You must select a valid duration", Status.BAD_REQUEST);
            }
            if (hoursDurationArrivalInt == 0 && minutesDurationArrivalInt == 0) {
                return new Response("Duration must be different from zero", Status.BAD_REQUEST);
            }

            boolean hasScale = !(scaleLocationId.equals("Location") || scaleLocationId.equals(""));

        if (hasScale) {
            // Validate scale location
            if (storage.getAirport(scaleLocationId) == null) {
                return new Response("Scale location does not exist", Status.BAD_REQUEST);
            }else{
                scaleLocation = storage.getAirport(scaleLocationId);
            }

            // Validate scale duration
            hoursDurationsScaleint = Integer.parseInt(hoursDurationsScale);
            minutesDurationScaleint = Integer.parseInt(minutesDurationsScale);
            if (hoursDurationsScaleint == 0 && minutesDurationScaleint == 0) {
                return new Response("Scale duration must be greater than 0", Status.BAD_REQUEST);
            }

            // Create flight with scale
            if (!storage.addFlight(new Flight(id, plane, departureLocation, scaleLocation, arrivalLocation, departureDate, hoursDurationArrivalInt, minutesDurationArrivalInt, hoursDurationsScaleint, minutesDurationScaleint))) {
                return new Response("Flight with this ID already exists", Status.BAD_REQUEST);
            }

        } else {
            // Create flight without scale
            if (!storage.addFlight(new Flight(id, plane, departureLocation, arrivalLocation, departureDate, hoursDurationArrivalInt, minutesDurationArrivalInt))) {
                return new Response("Flight with this ID already exists", Status.BAD_REQUEST);
            }
        }

        return new Response("Flight created successfully", Status.CREATED);

        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response addPassenger(String passengerId, String flightId) {
       try{ 
       long passengerIdLong;
        try {
                passengerIdLong = Long.parseLong(passengerId);
                if (passengerIdLong < 0) {
                    return new Response("Id must be positive", Status.BAD_REQUEST);
                }
                if(passengerId.length()>15){
                    return new Response("Id cannot have more than 15 digits", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Id must be numeric", Status.BAD_REQUEST);
            }
            if (flightId.equals("Flight")){
                return new Response("You must select a flight Id", Status.BAD_REQUEST);
            }
            Storage storage = Storage.getInstance();
            Passenger passenger = storage.getPassenger(passengerIdLong);
            Flight flight = storage.getFlight(flightId);
            if (passenger == null) {
            return new Response("Passenger not found", Status.NOT_FOUND);
            }
            if (flight == null) {
            return new Response("Flight not found", Status.NOT_FOUND);
            }
        
            if (passenger.getFlights().contains(flight)) {
            return new Response("Passenger already assigned to this flight", Status.BAD_REQUEST);
            }
            passenger.addFlight(flight);
            flight.addPassenger(passenger);

            return new Response("Passenger added to flight successfully", Status.OK);
        
        
            
       }catch(Exception ex){
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
        
    }

    public Response delayFlight() {
        return null;
    }

    public Response showAllFlights() {
        return null;
    }
}
