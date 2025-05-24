/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.model.Calculations;

import airport.model.Passenger;
import airport.model.utils.PassengerFormatter;
import java.time.LocalDate;
import java.time.Period;

/**
 *
 * @author USUARIO
 */
public class StandardPassengerFormatter implements PassengerFormatter{

    @Override
    public String getFullname(Passenger p) {
        return p.getFirstname() + " " + p.getLastname();
    }

    @Override
    public String getFormattedPhone(Passenger p) {
        return "+" + p.getCountryPhoneCode() + " " + p.getPhone();
    }

    @Override
    public int calculateAge(Passenger p) {
        return Period.between(p.getBirthDate(), LocalDate.now()).getYears();
    }
    
}
