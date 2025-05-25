/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.controllers.table;

import airport.controllers.utils.Response;
import airport.controllers.utils.Status;
import airport.model.Flight;
import airport.model.Passenger;
import airport.model.storage.StorageFlight;
import airport.model.storage.StoragePassenger;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author osman
 */
public class FlightTableController {

    public static Response updateFlightTable(DefaultTableModel model) {
        try {
            model.setRowCount(0);
            ArrayList<Flight> flights = StorageFlight.getInstance().getFlightsSorted();
            if (flights == null) {
                return new Response("No flights found", Status.NO_CONTENT);
            }
            for (Flight flight : flights) {
                model.addRow(new Object[]{flight.getId(), flight.getDepartureLocation().getAirportId(), flight.getArrivalLocation().getAirportId(), (flight.getScaleLocation() == null ? "-" : flight.getScaleLocation().getAirportId()), flight.getDepartureDate(), flight.calculateArrivalDate(), flight.getPlane().getId(), flight.getNumPassengers()});
            }
            return new Response("Flights updated succesfully", Status.OK);
        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response updateMyFlightsTable(String passengerSelected, DefaultTableModel model) {
        try {
            if (passengerSelected == null || passengerSelected.equals("Select User")) {
                return new Response("Select a passenger", Status.BAD_REQUEST);
            }
            long passengerId;
            try {
                passengerId = Long.parseLong(passengerSelected);
            } catch (NumberFormatException e) {
                return new Response("Invalid passenger ID", Status.BAD_REQUEST);
            }

            Passenger passenger = StoragePassenger.getInstance().getPassenger(passengerId);
            if (passenger == null) {
                return new Response("Passenger not found", Status.NOT_FOUND);
            }
            ArrayList<Flight> flights = passenger.getFlights();
            if (flights == null) {
                return new Response("This passenger has no flights", Status.NO_CONTENT);
            }
            flights.sort(Comparator.comparing(Flight::getDepartureDate));
            model.setRowCount(0);
            for (Flight flight : flights) {
                model.addRow(new Object[]{flight.getId(), flight.getDepartureDate(), flight.calculateArrivalDate()});
            }
            return new Response("Passenger flights updated succesfully", Status.OK);
        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
