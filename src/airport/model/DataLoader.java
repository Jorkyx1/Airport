/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.model;

import airport.model.storage.StorageFlight;
import airport.model.storage.StorageLocation;
import airport.model.storage.StoragePassenger;
import airport.model.storage.StoragePlane;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author USUARIO
 */
public class DataLoader {
    public static void loadPlanes(String path) {
        try {
            String content = Files.readString(Paths.get(path));
            JSONArray jsonArray = new JSONArray(content);
            StoragePlane storage = StoragePlane.getInstance();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject planeJson = jsonArray.getJSONObject(i);
                Plane plane = new Plane(
                    planeJson.getString("id"),
                    planeJson.getString("brand"),
                    planeJson.getString("model"),
                    planeJson.getInt("maxCapacity"),
                    planeJson.getString("airline")
                );
                storage.addPlane(plane);
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
    public static void loadLocations(String path) {
      try{
          String content = Files.readString(Paths.get(path));
            JSONArray array = new JSONArray(content);
            StorageLocation storage = StorageLocation.getInstance();

            for (int i = 0; i < array.length(); i++) {
                JSONObject o = array.getJSONObject(i);
                Location loc = new Location(
                o.getString("airportId"),
                o.getString("airportName"),
                o.getString("airportCity"),
                o.getString("airportCountry"),
                o.getDouble("airportLatitude"),
                o.getDouble("airportLongitude")
                );
                storage.addLocation(loc);
            }
      }catch (IOException | JSONException e) {
            e.printStackTrace();
        }      
    } 
    
    public static void loadPassengers(String path){
      try{
            String content = Files.readString(Paths.get(path));
            JSONArray array = new JSONArray(content);
            StoragePassenger storage = StoragePassenger.getInstance();

            for (int i = 0; i < array.length(); i++) {
                JSONObject o = array.getJSONObject(i);
                LocalDate birth = LocalDate.parse(o.getString("birthDate")); // Formato ISO 8601
                Passenger p = new Passenger(
                    o.getLong("id"),
                    o.getString("firstname"),
                    o.getString("lastname"),
                    birth,
                    o.getInt("countryPhoneCode"),
                    o.getLong("phone"),
                    o.getString("country")
                );
                storage.addPassenger(p);
            }
      }catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
    
    public static void loadFlights(String path){
        try{
            String content = Files.readString(Paths.get(path));
            JSONArray array = new JSONArray(content);
            StorageFlight storageF = StorageFlight.getInstance();
            StoragePlane storageP = StoragePlane.getInstance();
            StorageLocation storageL = StorageLocation.getInstance();

            for (int i = 0; i < array.length(); i++) {
                JSONObject o = array.getJSONObject(i);

                String id = o.getString("id");
                Plane plane = storageP.getPlane(o.getString("plane"));
                Location dep = storageL.getAirport(o.getString("departureLocation"));
                Location arr = storageL.getAirport(o.getString("arrivalLocation"));
                Location scale = o.isNull("scaleLocation") ? null : storageL.getAirport(o.getString("scaleLocation"));
                LocalDateTime departureDate = LocalDateTime.parse(o.getString("departureDate"));

                int hArrival = o.getInt("hoursDurationArrival");
                int mArrival = o.getInt("minutesDurationArrival");
                int hScale = o.getInt("hoursDurationScale");
                int mScale = o.getInt("minutesDurationScale");

                Flight f;
                if (scale == null) {
                    f = new Flight(id, plane, dep, arr, departureDate, hArrival, mArrival);
                } else {
                    f = new Flight(id, plane, dep, scale, arr, departureDate, hArrival, mArrival, hScale, mScale);
                }

                storageF.addFlight(f);
            }
        }catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
    
}
