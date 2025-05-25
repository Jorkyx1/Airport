/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.model.storage;

import airport.model.Flight;
import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author USUARIO
 */
public class StorageFlight extends Observable {

    private static StorageFlight instance;

    private ArrayList<Flight> flights;

    private StorageFlight() {
        this.flights = new ArrayList<>();
    }

    public static StorageFlight getInstance() {
        if (instance == null) {
            instance = new StorageFlight();
        }
        return instance;
    }

    public boolean addFlight(Flight flight) {
        for (Flight p : this.flights) {
            if (p.getId().equals(flight.getId())) {
                return false;
            }
        }
        this.flights.add(flight);
        notifyObservers();
        return true;
    }

    public Flight getFlight(String id) {
        for (Flight l : this.flights) {
            if (l.getId().equals(id)) {
                return l;
            }
        }
        return null;
    }

    public ArrayList<Flight> getFlights() {
        return flights;
    }

    public ArrayList<Flight> getFlightsSorted() {
        ArrayList<Flight> locationSorted = new ArrayList<>();
        for (Flight l : flights) {
            locationSorted.add(l.clone());
        }
        locationSorted.sort(Comparator.comparing(Flight::getDepartureDate));
        return locationSorted;
    }
}
