/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.controllers;

import airport.controllers.utils.Response;
import airport.controllers.utils.Status;
import airport.model.Location;
import airport.model.storage.StorageLocation;
import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author USUARIO
 */
public class LocationController {

    //Crear y consultar aeropuertos
    public static Response createAirport(String id, String name, String city, String country, String latitude, String longitude) {
        double latitudeDouble, longitudeDouble;
        try {
            if (id == null || id.length() != 3) {
                return new Response("Id must be 3 digits long", Status.BAD_REQUEST);
            }
            for (int i = 0; i < id.length(); i++) {
                char c = id.charAt(i);
                if (c < 'A' || c > 'Z') {
                    return new Response("Id must be 3 capital letters", Status.BAD_REQUEST);
                }
            }
            if (name == null) {
                return new Response("Invalid name", Status.BAD_REQUEST);
            }
            if (city == null) {
                return new Response("Invalid city", Status.BAD_REQUEST);
            }
            if (country == null) {
                return new Response("Invalid country", Status.BAD_REQUEST);
            }
            try {
                latitudeDouble = Double.parseDouble(latitude);
                if (latitudeDouble < -90 || latitudeDouble > 90) {
                    return new Response("Latitude must be between -90 and 90", Status.BAD_REQUEST);
                }
                int index = latitude.indexOf(".");
                if (index != -1) {
                    if (latitude.substring(index + 1).length() > 4) {
                        return new Response("Latitude must have a maximun number of 4 decimals", Status.BAD_REQUEST);
                    }
                }
            } catch (NumberFormatException ex) {
                return new Response("Latitude must be numeric", Status.BAD_REQUEST);
            }
            try {
                longitudeDouble = Double.parseDouble(longitude);
                if (longitudeDouble < -180 || longitudeDouble > 180) {
                    return new Response("Longitude must be between -180 and 180", Status.BAD_REQUEST);
                }
                int index = longitude.indexOf(".");
                if (index != -1) {
                    if (longitude.substring(index + 1).length() > 4) {
                        return new Response("Longitude must have a maximun number of 4 decimals", Status.BAD_REQUEST);
                    }
                }
            } catch (NumberFormatException ex) {
                return new Response("Longitude must be numeric", Status.BAD_REQUEST);
            }
            StorageLocation storage = StorageLocation.getInstance();
            if (!storage.addLocation(new Location(id, name, city, country, latitudeDouble, longitudeDouble))) {
                return new Response("An airport with that id already exists", Status.BAD_REQUEST);
            }
            return new Response("Airport created successfully", Status.CREATED);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response showAllAirports() {
        StorageLocation storage = StorageLocation.getInstance();
        ArrayList<Location> locations = storage.getLocations();
        if (locations.isEmpty()) {
            return new Response("No locations found", Status.NOT_FOUND);
        }
        ArrayList<Location> clones = new ArrayList<>();
        for (Location l : locations) {
            clones.add(l.clone());
        }
        clones.sort(Comparator.comparing(Location::getAirportId));
        return new Response("Locations tab updated succesfully", Status.OK, clones);
    }
    public static ArrayList<String> refreshPlaneCombo(){
        StorageLocation storage = StorageLocation.getInstance();
        ArrayList<Location> locations = storage.getLocations();
        ArrayList<String> ids = new ArrayList<>();
        for(Location l : locations){
            ids.add(l.getAirportId());
        }
        return ids;
    }

}
