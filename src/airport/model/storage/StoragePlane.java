/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.model.storage;

import airport.model.Plane;
import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author USUARIO
 */
public class StoragePlane extends Observable{
    private static StoragePlane instance;

    private ArrayList<Plane> planes;
    
    private StoragePlane() {
        this.planes = new ArrayList<>();
    }

    public static StoragePlane getInstance() {
        if (instance == null) {
            instance = new StoragePlane();
        }
        return instance;
    }
    
    public boolean addPlane(Plane plane) {
        for (Plane p : this.planes) {
            if (p.getId().equals(plane.getId())) {
                return false;
            }
        }
        this.planes.add(plane);
        notifyObservers();
        return true;
    }
    
    public Plane getPlane(String id) {
        for (Plane p : this.planes) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }
    
    public ArrayList<Plane> getPlanes() {
        return planes;
    }
    
    public ArrayList<Plane> getPlanesSorted() {
        ArrayList<Plane> planesSorted = new ArrayList<>();
        for (Plane l : planes) {
            planesSorted.add(l.clone());
        }
        planesSorted.sort(Comparator.comparing(Plane::getId));
        return planesSorted;
    }
}
