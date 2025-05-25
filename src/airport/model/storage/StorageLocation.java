/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.model.storage;

import airport.model.Location;
import java.util.ArrayList;

/**
 *
 * @author USUARIO
 */
public class StorageLocation extends Observable{
    private static StorageLocation instance;

    private ArrayList<Location> locations;
    
    private StorageLocation() {
        this.locations = new ArrayList<>();
    }

    public static StorageLocation getInstance() {
        if (instance == null) {
            instance = new StorageLocation();
        }
        return instance;
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
    
    public Location getAirport(String id) {
        for (Location l : this.locations) {
            if (l.getAirportId().equals(id)) {
                return l;
            }
        }
        return null;
    }
    
    public ArrayList<Location> getLocations() {
        return locations;
    }
}
