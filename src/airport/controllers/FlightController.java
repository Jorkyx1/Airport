/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.controllers;

import airport.controllers.utils.Response;
import airport.controllers.utils.Status;
import airport.model.Storage;

/**
 *
 * @author USUARIO
 */
public class FlightController {

    public static Response createFlight(String id, String planeId, String departureLocationId, String arrivalLocationId, String scaleLocationId, String year, String month, String day, String hour, String minutes, String hoursDurationsArrival, String minutesDurationsArrival, String hoursDurationsScale, String minutesDurationsScale) {
        Storage storage = Storage.getInstance();
        int hoursDurationsScaleint, minutesDurationScaleint;
        try {
            if (id == null) {
                return new Response("Invalid id", Status.BAD_REQUEST);
            }
            if (storage.getPlane(planeId) == null) {
                return new Response("Plane does not exist", Status.BAD_REQUEST);
            }
            if (departureLocationId == null) {
                return new Response("Invalid departure location id", Status.BAD_REQUEST);
            }
            if (storage.getAirport(departureLocationId) == null) {
                return new Response("Departure location id does not exist", Status.BAD_REQUEST);
            }
            if (arrivalLocationId == null) {
                return new Response("Invalid arrival location id", Status.BAD_REQUEST);
            }
            if (storage.getAirport(arrivalLocationId) == null) {
                return new Response("Arrival location id does not exist", Status.BAD_REQUEST);
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
            if (scaleLocationId != null && storage.getAirport(scaleLocationId) == null) {
                return new Response("Scale location id does not exist", Status.BAD_REQUEST);
            } else if (scaleLocationId == null && minutesDurationScaleint != 0 || scaleLocationId == null && hoursDurationsScaleint != 0) {
                return new Response("Hours duration scale AND Minutes duration scale must be zero", Status.BAD_REQUEST);

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
