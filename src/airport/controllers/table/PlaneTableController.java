/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.controllers.table;

import airport.controllers.utils.Response;
import airport.controllers.utils.Status;
import airport.model.Plane;
import airport.model.storage.StoragePlane;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author osman
 */
public class PlaneTableController {

    public static Response updatePlaneTable(DefaultTableModel model) {
        try {
            model.setRowCount(0);
            ArrayList<Plane> planes = StoragePlane.getInstance().getPlanesSorted();
            if (planes == null) {
                return new Response("No planes found", Status.NO_CONTENT);
            }
            for (Plane plane : planes) {
                model.addRow(new Object[]{plane.getId(), plane.getBrand(), plane.getModel(), plane.getMaxCapacity(), plane.getAirline(), plane.getNumFlights()});
            }
            return new Response("Planes updated succesfully", Status.OK);
        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
