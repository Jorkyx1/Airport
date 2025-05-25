/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.model.storage;

import airport.model.Passenger;
import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author USUARIO
 */
public class StoragePassenger extends Observable {

    private static StoragePassenger instance;

    private ArrayList<Passenger> passengers;

    private StoragePassenger() {
        this.passengers = new ArrayList<>();
    }

    public static StoragePassenger getInstance() {
        if (instance == null) {
            instance = new StoragePassenger();
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
        notifyObservers();
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

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    public ArrayList<Passenger> getPassengersSorted() {
        ArrayList<Passenger> passengerSorted = new ArrayList<>();
        for (Passenger l : passengers) {
            passengerSorted.add(l.clone());
        }
        passengerSorted.sort(Comparator.comparing(Passenger::getId));
        return passengerSorted;
    }
}
