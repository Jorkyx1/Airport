/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.model.Calculations;

import airport.model.Flight;
import airport.model.utils.ArrivalTimeCalculator;
import java.time.LocalDateTime;

/**
 *
 * @author osman
 */
public class StandardFlightArrivalCalculator implements ArrivalTimeCalculator {
    @Override
    public LocalDateTime calculateArrivalDate(Flight flight) {
        return flight.getDepartureDate()
            .plusHours(flight.getHoursDurationScale())
            .plusMinutes(flight.getMinutesDurationScale())
            .plusHours(flight.getHoursDurationArrival())
            .plusMinutes(flight.getMinutesDurationArrival());
    }
}


