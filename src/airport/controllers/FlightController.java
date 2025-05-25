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
import airport.model.storage.StorageFlight;
import airport.model.storage.StorageLocation;
import airport.model.storage.StoragePassenger;
import airport.model.storage.StoragePlane;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author USUARIO
 */
public class FlightController {

    public static Response createFlight(String id, String planeId, String departureLocationId, String arrivalLocationId, String scaleLocationId, String year, String month, String day, String hour, String minutes, String hoursDurationsArrival, String minutesDurationsArrival, String hoursDurationsScale, String minutesDurationsScale) {
        StorageFlight storageF = StorageFlight.getInstance();
        StoragePlane storageP = StoragePlane.getInstance();
        StorageLocation storageL = StorageLocation.getInstance();

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
            if (storageP.getPlane(planeId) == null) {
                return new Response("Plane does not exist", Status.BAD_REQUEST);
            } else {
                plane = storageP.getPlane(planeId);
            }

            Location departureLocation, arrivalLocation, scaleLocation;
            if (storageL.getAirport(departureLocationId) == null) {
                return new Response("Departure location id does not exist", Status.BAD_REQUEST);
            } else {
                departureLocation = storageL.getAirport(departureLocationId);
            }

            if (storageL.getAirport(arrivalLocationId) == null) {
                return new Response("Arrival location id does not exist", Status.BAD_REQUEST);
            } else {
                arrivalLocation = storageL.getAirport(arrivalLocationId);
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
                if (storageL.getAirport(scaleLocationId) == null) {
                    return new Response("Scale location does not exist", Status.BAD_REQUEST);
                } else {
                    scaleLocation = storageL.getAirport(scaleLocationId);
                }

                // Validate scale duration
                try {
                    hoursDurationsScaleint = Integer.parseInt(hoursDurationsScale);
                    minutesDurationScaleint = Integer.parseInt(minutesDurationsScale);
                } catch (NumberFormatException ex) {
                    return new Response("You must select a valid scale duration", Status.BAD_REQUEST);
                }
                if (hoursDurationsScaleint == 0 && minutesDurationScaleint == 0) {
                    return new Response("Scale duration must be greater than 0", Status.BAD_REQUEST);
                }
                Flight flight = new Flight(id, plane, departureLocation, scaleLocation, arrivalLocation, departureDate, hoursDurationArrivalInt, minutesDurationArrivalInt, hoursDurationsScaleint, minutesDurationScaleint);
                // Create flight with scale
                if (!storageF.addFlight(flight)) {
                    return new Response("Flight with this ID already exists", Status.BAD_REQUEST);
                }
                plane.addFlight(flight);
            } else {
                Flight flight = new Flight(id, plane, departureLocation, arrivalLocation, departureDate, hoursDurationArrivalInt, minutesDurationArrivalInt);
                // Create flight without scale
                if (!storageF.addFlight(flight)) {
                    return new Response("Flight with this ID already exists", Status.BAD_REQUEST);
                }
                plane.addFlight(flight);
                storageP.notifyObservers();
            }
            return new Response("Flight created successfully", Status.CREATED);

        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response addPassenger(String passengerId, String flightId) {
        try {
            long passengerIdLong;
            try {
                passengerIdLong = Long.parseLong(passengerId);
                if (passengerIdLong < 0) {
                    return new Response("Id must be positive", Status.BAD_REQUEST);
                }
                if (passengerId.length() > 15) {
                    return new Response("Id cannot have more than 15 digits", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Id must be numeric", Status.BAD_REQUEST);
            }
            if (flightId.equals("Flight")) {
                return new Response("You must select a flight Id", Status.BAD_REQUEST);
            }
            StorageFlight storageF = StorageFlight.getInstance();
            StoragePassenger storageP = StoragePassenger.getInstance();
            Passenger passenger = storageP.getPassenger(passengerIdLong);
            Flight flight = storageF.getFlight(flightId);
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
            storageF.notifyObservers();
            storageP.notifyObservers();

            return new Response("Passenger added to flight successfully", Status.OK);

        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }

    }

    public static Response delayFlight(String flightId, String hours, String minutes) {
        int hoursInt, minutesInt;
        Flight flight = null;

        if (flightId.equals("ID")) {
            return new Response("You must select a flight Id", Status.BAD_REQUEST);
        }
        try {
            hoursInt = Integer.parseInt(hours);
            minutesInt = Integer.parseInt(minutes);
        } catch (NumberFormatException ex) {
            return new Response("Hours and minutes must be numeric", Status.BAD_REQUEST);
        }
        StorageFlight storage = StorageFlight.getInstance();
        flight = storage.getFlight(flightId);
        if (flight == null) {
            return new Response("Flight does not exist", Status.BAD_REQUEST);
        }
        if (hoursInt == 0 && minutesInt == 0) {
            return new Response("Flight delay must be greater than zero", Status.BAD_REQUEST);
        }
        storage.updateFlight(flight, hoursInt, minutesInt);
        return new Response("Delay added succesfully", Status.OK);
    }

    public static Response showAllFlights() {
        StorageFlight storage = StorageFlight.getInstance();
        ArrayList<Flight> flights = storage.getFlightsSorted();
        if (flights.isEmpty()) {
            return new Response("No planes found", Status.NOT_FOUND);
        }
        ArrayList<Flight> clones = new ArrayList<>();
        for (Flight f : flights) {
            clones.add(f.clone());
        }
        return new Response("Planes tab updated succesfully", Status.OK, clones);
    }

    public static ArrayList<String> refreshFlightCombo() {
        StorageFlight storage = StorageFlight.getInstance();
        ArrayList<Flight> flights = storage.getFlights();
        ArrayList<String> ids = new ArrayList<>();
        for (Flight f : flights) {
            ids.add(f.getId());
        }
        return ids;
    }
}
