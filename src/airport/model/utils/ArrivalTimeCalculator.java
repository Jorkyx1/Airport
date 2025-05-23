/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package airport.model.utils;

import airport.model.Flight;
import java.time.LocalDateTime;

/**
 *
 * @author osman
 */
public interface ArrivalTimeCalculator {
    LocalDateTime calculateArrivalDate(Flight flight);
}
