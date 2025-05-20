/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.controllers;

import airport.controllers.utils.Response;
import airport.controllers.utils.Status;
import airport.model.Flight;
import airport.model.Location;
import airport.model.Plane;
import airport.model.Storage;
import java.time.LocalDate;
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
            if (id == null) {
                return new Response("Invalid id", Status.BAD_REQUEST);
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

            try {
                hoursDurationsScaleint = Integer.parseInt(hoursDurationsScale);
            } catch (NumberFormatException ex) {
                return new Response("Invalid hours duration scale", Status.BAD_REQUEST);
            }
            try {
                minutesDurationScaleint = Integer.parseInt(hoursDurationsScale);
            } catch (NumberFormatException ex) {
                return new Response("Invalid minutes duration scale", Status.BAD_REQUEST);
            }
            if (storage.getAirport(scaleLocationId) == null) {
                return new Response("Scale location id does not exist", Status.BAD_REQUEST);
            } else if (scaleLocationId.equals("Location") && minutesDurationScaleint != 0 || scaleLocationId.equals("Location") && hoursDurationsScaleint != 0) {
                return new Response("Hours duration scale AND Minutes duration scale must be zero", Status.BAD_REQUEST);
            }

            boolean hasScale = !(scaleLocationId.equals("Location"));

            if (hasScale) {
                scaleLocation = storage.getAirport(scaleLocationId);
                if (!storage.addFlight(new Flight(id, plane, departureLocation, scaleLocation, arrivalLocation, departureDate, hoursDurationArrivalInt, minutesDurationArrivalInt, hoursDurationsScaleint, minutesDurationScaleint))) {
                    return new Response("A flight with that id already exists", Status.BAD_REQUEST);
                }
                return new Response("Flight created successfully", Status.CREATED);
            } else {
                if (!storage.addFlight(new Flight(id, plane, departureLocation, arrivalLocation, departureDate, hoursDurationArrivalInt, minutesDurationArrivalInt))) {
                    return new Response("A flight with that id already exists", Status.BAD_REQUEST);
                }
                return new Response("Flight created successfully", Status.CREATED);
            }

        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public Response addPassenger() {
        return null;
    }

    public Response delayFlight() {
        return null;
    }

    public Response showAllFlights() {
        return null;
    }
}
