/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.controllers;

import airport.controllers.utils.Response;
import airport.controllers.utils.Status;
import airport.model.Passenger;
import airport.model.Storage;
import java.time.LocalDate;

/**
 *
 * @author USUARIO
 */
public class PassengerController {
    //Registrar y actualizar pasajeros, añadirlos a vuelos
    public static Response registerPassenger(String id, String firstname, String lastname, String year, String month, String day, String phoneCode, String phone, String country){
    try{
        long idLong, phoneLong;
        int yearInt, monthInt, dayInt,phoneCodeInt;
        try {
                idLong = Long.parseLong(id);
                if (idLong < 0) {
                    return new Response("Id must be positive", Status.BAD_REQUEST);
                }
                if(id.length()>15){
                    return new Response("Id cannot have more than 15 digits", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Id must be numeric", Status.BAD_REQUEST);
            }
        try {
                monthInt = Integer.parseInt(month);
                if (monthInt < 0) {
                    return new Response("Month must be positive", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("You must select a month", Status.BAD_REQUEST);
            }
        try {
                dayInt = Integer.parseInt(day);
                if (dayInt < 0) {
                    return new Response("Day must be positive", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("You must select a day", Status.BAD_REQUEST);
            }
        if (firstname.equals("")) {
                return new Response("Firstname must be not empty", Status.BAD_REQUEST);
            }
            
        if (lastname.equals("")) {
                return new Response("Lastname must be not empty", Status.BAD_REQUEST);
            }
        if (country.equals("")){
            return new Response("Country must be not empty", Status.BAD_REQUEST);
        }
                yearInt = Integer.parseInt(year);
                monthInt = Integer.parseInt(month);
                dayInt = Integer.parseInt(day);
                LocalDate birthDate = LocalDate.of(yearInt, monthInt, dayInt);
                LocalDate actualDate = LocalDate.now();
                if (!birthDate.isBefore(actualDate)){
                    return new Response("the date must be before the current one", Status.BAD_REQUEST);
                }
        try {
                phoneCodeInt = Integer.parseInt(phoneCode);
                if (phoneCodeInt < 0) {
                    return new Response("Id must be positive", Status.BAD_REQUEST);
                }
                if(phoneCode.length()>3){
                    return new Response("Id cannot have more than 3 digits", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Id must be numeric", Status.BAD_REQUEST);
            }
        try {
                phoneLong = Long.parseLong(phone);
                if (phoneLong < 0) {
                    return new Response("Id must be positive", Status.BAD_REQUEST);
                }
                if(phone.length()>11){
                    return new Response("Id cannot have more than 11 digits", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Id must be numeric", Status.BAD_REQUEST);
            }
        Storage storage = Storage.getInstance();            
            if (!storage.addPassenger(new Passenger(idLong, firstname, lastname, birthDate, phoneCodeInt, phoneLong, country))) {
                return new Response("A passenger with that id already exists", Status.BAD_REQUEST);
            }
            return new Response("Passenger created successfully", Status.CREATED);
        } catch (Exception ex) {
            return new Response("Unexpected error"+ex, Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    public static Response updatePassenger(String id, String firstname, String lastname, String year, String month, String day, String phoneCode, String phone, String country){
        try{
            long idLong, phoneLong;
            int yearInt, monthInt, dayInt,phoneCodeInt;
            try {
                idLong = Long.parseLong(id);
                if (idLong < 0) {
                    return new Response("Id must be positive", Status.BAD_REQUEST);
                }
                if(id.length()>15){
                    return new Response("Id cannot have more than 15 digits", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Id must be numeric", Status.BAD_REQUEST);
            }
            
            Storage storage = Storage.getInstance();
            
            Passenger passenger = storage.getPassenger(idLong);
            if (passenger == null) {
                return new Response("Passanger not found", Status.NOT_FOUND);
            }
            try {
                monthInt = Integer.parseInt(month);
                if (monthInt < 0) {
                    return new Response("Month must be positive", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("You must select a month", Status.BAD_REQUEST);
            }
            try {
                dayInt = Integer.parseInt(month);
                if (dayInt < 0) {
                    return new Response("Day must be positive", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("You must select a day", Status.BAD_REQUEST);
            }
            if (firstname.equals("")) {
                return new Response("Firstname must be not empty", Status.BAD_REQUEST);
            }
            
            if (lastname.equals("")) {
                return new Response("Lastname must be not empty", Status.BAD_REQUEST);
            }
            if (country.equals("")){
                return new Response("Country must be not empty", Status.BAD_REQUEST);
        }
                yearInt = Integer.parseInt(year);
                monthInt = Integer.parseInt(month);
                dayInt = Integer.parseInt(day);
                LocalDate birthDate = LocalDate.of(yearInt, monthInt, dayInt);
                LocalDate actualDate = LocalDate.now();
                if (!birthDate.isBefore(actualDate)){
                    return new Response("the date must be before the current one", Status.BAD_REQUEST);
                }
            try {
                phoneCodeInt = Integer.parseInt(phoneCode);
                if (phoneCodeInt < 0) {
                    return new Response("Id must be positive", Status.BAD_REQUEST);
                }
                if(phoneCode.length()>3){
                    return new Response("Id cannot have more than 3 digits", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Id must be numeric", Status.BAD_REQUEST);
            }
            try {
                phoneLong = Long.parseLong(phone);
                if (phoneLong < 0) {
                    return new Response("Id must be positive", Status.BAD_REQUEST);
                }
                if(phone.length()>11){
                    return new Response("Id cannot have more than 11 digits", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Id must be numeric", Status.BAD_REQUEST);
            }
            passenger.setFirstname(firstname);
            passenger.setLastname(lastname);
            passenger.setBirthDate(birthDate);
            passenger.setCountry(country);
            passenger.setPhone(phoneLong);
            passenger.setCountryPhoneCode(phoneCodeInt);
            
            return new Response("Passenger data updated successfully", Status.OK);
        }catch(Exception ex){
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    public static Response showAllPassengers(){
        return null;
    }

}
