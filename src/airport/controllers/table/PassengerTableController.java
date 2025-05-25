/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.controllers.table;

import airport.controllers.utils.Response;
import airport.controllers.utils.Status;
import airport.model.Flight;
import airport.model.Passenger;
import airport.model.storage.StorageFlight;
import airport.model.storage.StoragePassenger;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author osman
 */
public class PassengerTableController {
     public static Response updatePassengerTable(DefaultTableModel model) {
        try {
            model.setRowCount(0);
            ArrayList<Passenger> passengers = StoragePassenger.getInstance().getPassengersSorted();
            if (passengers == null) {
                return new Response("No passengers found", Status.NO_CONTENT);
            }
            for (Passenger passenger : passengers) {
                model.addRow(new Object[]{passenger.getId(), passenger.getFullname(), passenger.getBirthDate(), passenger.calculateAge(), passenger.getFormattedPhone(), passenger.getCountry(), passenger.getNumFlights()});
            }
            return new Response("Passengers updated succesfully", Status.OK);
        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
      
}
