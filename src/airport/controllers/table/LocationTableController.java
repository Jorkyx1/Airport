/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.controllers.table;

import airport.controllers.utils.Response;
import airport.controllers.utils.Status;
import airport.model.Location;
import airport.model.storage.StorageLocation;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author osman
 */
public class LocationTableController {

    public static Response updateLocationTable(DefaultTableModel model) {
        try {
            model.setRowCount(0);
            ArrayList<Location> locations = StorageLocation.getInstance().getLocationsSorted();
            if (locations == null) {
                return new Response("No locations found", Status.NO_CONTENT);
            }
            for (Location location : locations) {
                model.addRow(new Object[]{location.getAirportId(), location.getAirportName(), location.getAirportCity(), location.getAirportCountry()});
            }
            return new Response("Locations updated succesfully", Status.OK);
        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
