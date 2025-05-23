/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.model.Calculations;

import airport.model.Passenger;
import java.time.LocalDate;
import java.time.Period;

/**
 *
 * @author USUARIO
 */
public class StandardPassengerFormatter{

    public static String getFullname(Passenger p) {
        return p.getFirstname() + " " + p.getLastname();
    }

    public static String getFormattedPhone(Passenger p) {
        return "+" + p.getCountryPhoneCode() + " " + p.getPhone();
    }

    public static int calculateAge(Passenger p) {
        return Period.between(p.getBirthDate(), LocalDate.now()).getYears();
    }
    
}
