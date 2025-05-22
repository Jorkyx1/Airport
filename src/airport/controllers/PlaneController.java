/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.controllers;

import airport.controllers.utils.Response;
import airport.controllers.utils.Status;
import airport.model.Plane;
import airport.model.Storage;
import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author USUARIO
 */
public class PlaneController {

    //Crear y consultar aviones
    public static Response createPlane(String id, String brand, String model, String maxCapacity, String airline) {
        int maxCapacityint;
        try {
            if (id == null || id.length() != 7) {
                return new Response("Id must be 7 digits long", Status.BAD_REQUEST);
            }
            for (int i = 0; i < 2; i++) {
                char c = id.charAt(i);
                if (c < 'A' || c > 'Z') {
                    return new Response("Id must start with 2 capital letters", Status.BAD_REQUEST);
                }
            }
            for (int i = 2; i < 7; i++) {
                char c = id.charAt(i);
                if (c < '0' || c > '9') {
                    return new Response("Invalid id", Status.BAD_REQUEST);
                }
            }
            if (brand == null) {
                return new Response("Brand must not be empty", Status.BAD_REQUEST);
            }
            if (model == null) {
                return new Response("Model must not be empty", Status.BAD_REQUEST);
            }
            try {
                maxCapacityint = Integer.parseInt(maxCapacity);
            } catch (NumberFormatException ex) {
                return new Response("Max capacity must be numeric", Status.BAD_REQUEST);
            }
            if (airline == null) {
                return new Response("Airline must not be empty", Status.BAD_REQUEST);
            }
            Storage storage = Storage.getInstance();
            if (!storage.addPlane(new Plane(id, brand, model, maxCapacityint, airline))) {
                return new Response("An airplane with that id already exists", Status.BAD_REQUEST);
            }
            return new Response("Airplane created successfully", Status.CREATED);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response showAllPlanes() {
        Storage storage = Storage.getInstance();
        ArrayList<Plane> planes = storage.getPlanes();
        if (planes.isEmpty()) {
            return new Response("No planes found", Status.NOT_FOUND);
        }
        ArrayList<Plane> clones = new ArrayList<>();
        for (Plane p : planes) {
            clones.add(p.clone());
        }
        clones.sort(Comparator.comparing(Plane::getId));
        return new Response("Planes tab updated succesfully", Status.OK, clones);
    }

}
