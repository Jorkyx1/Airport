/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import airport.model.DataLoader;
import airport.view.AirportFrame;
import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.UIManager;

/**
 *
 * @author USUARIO
 */
public class Main {
    public static void main(String args[]) {
        
        DataLoader.loadPlanes("json/planes.json");
        DataLoader.loadLocations("json/locations.json");
        DataLoader.loadPassengers("json/passengers.json");
        DataLoader.loadFlights("json/flights.json");
        
        System.setProperty("flatlaf.useNativeLibrary", "false");

        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AirportFrame().setVisible(true);
            }
        });
        
    }
}
