/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.model.storage;

import airport.model.Passenger;
import java.util.ArrayList;

/**
 *
 * @author USUARIO
 */
public class StoragePassenger extends Observable{
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
}
