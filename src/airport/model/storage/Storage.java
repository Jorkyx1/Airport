/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.model.storage;

import airport.model.Flight;
import airport.model.Location;
import airport.model.Passenger;
import airport.model.Plane;
import java.util.ArrayList;

/**
 *
 * @author USUARIO
 */
public class Storage {

    private static Storage instance;

    private ArrayList<Passenger> passengers;
    private ArrayList<Flight> flights;
    private ArrayList<Location> locations;
    private ArrayList<Plane> planes;

    private Storage() {
        this.passengers = new ArrayList<>();
        this.flights = new ArrayList<>();
        this.locations = new ArrayList<>();
        this.planes = new ArrayList<>();
    }

    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }

    public boolean addPassenger(Passenger passenger) {
        for (Passenger p : this.passengers) {
            if (p.getId() == passenger.getId()) {
                return false;
            }
        }
        this.passengers.add(passenger);
        return true;
    }

    public boolean addFlight(Flight flight) {
        for (Flight p : this.flights) {
            if (p.getId().equals(flight.getId())) {
                return false;
            }
        }
        this.flights.add(flight);
        return true;
    }

    public boolean addLocation(Location location) {
        for (Location p : this.locations) {
            if (p.getAirportId().equals(location.getAirportId())) {
                return false;
            }
        }
        this.locations.add(location);
        return true;
    }

    public boolean addPlane(Plane plane) {
        for (Plane p : this.planes) {
            if (p.getId().equals(plane.getId())) {
                return false;
            }
        }
        this.planes.add(plane);
        return true;
    }

    public Passenger getPassenger(long id) {
        for (Passenger p : this.passengers) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public Plane getPlane(String id) {
        for (Plane p : this.planes) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }
    
    public Location getAirport(String id) {
        for (Location l : this.locations) {
            if (l.getAirportId().equals(id)) {
                return l;
            }
        }
        return null;
    }
    
    public Flight getFlight(String id) {
        for (Flight l : this.flights) {
            if (l.getId().equals(id)) {
                return l;
            }
        }
        return null;
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    public ArrayList<Flight> getFlights() {
        return flights;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public ArrayList<Plane> getPlanes() {
        return planes;
    }
    
}
