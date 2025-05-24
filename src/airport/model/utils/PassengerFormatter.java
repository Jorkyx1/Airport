/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package airport.model.utils;

import airport.model.Passenger;

/**
 *
 * @author USUARIO
 */
public interface PassengerFormatter {
    String getFullname(Passenger p);
    String getFormattedPhone(Passenger p);
    int calculateAge(Passenger p);
}
