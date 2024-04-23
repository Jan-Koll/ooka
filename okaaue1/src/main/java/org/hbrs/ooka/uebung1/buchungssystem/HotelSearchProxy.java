package org.hbrs.ooka.uebung1.buchungssystem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class HotelSearchProxy implements HotelSearch {
    private final HotelRetrieval retrieval;

    public HotelSearchProxy() {
        retrieval = new HotelRetrieval();
    }

    public HotelSearchProxy(Cache cache) {
        retrieval = new HotelRetrieval(cache);
    }

    @Override
    public void openSession() {
        this.log("Herstellen einer neuen Datenbankverbindung über openSession().");
        this.retrieval.openSession();
    }

    @Override
    public void closeSession() {
        this.retrieval.closeSession();
        this.log("Verbindung zur Datenbank wurde über closeSession() getrennt.");
    }

    @Override
    public Hotel getHotelByName(String name) {
        this.log(String.format("Zugriff auf Buchungssystem über Methode getHotelByName. Suchwort: %s", name));
        Hotel result;

        if (this.retrieval.cache != null) {
            List<Object> cacheResult = this.retrieval.cache.get(name);
            if (cacheResult != null && !cacheResult.isEmpty() && cacheResult.get(0) instanceof Hotel) {
                result = (Hotel) cacheResult.get(0);
                this.log(String.format("\tHotel %s(%d) konnte aus dem Cache geladen werden.", result.getName(), result.getId()));
                return result;
            }
            result = this.retrieval.getHotelByName(name);
            if (result != null) {
                this.retrieval.cache.cacheResult(name, Collections.singletonList(result));
                this.log(String.format("\tHotel %s wurde aus der Datenbank geholt und in den Cache geschrieben.", result.getName()));
            } else {
                this.log("\tKein Hotel gefunden.");
            }
        } else {
            result = this.retrieval.getHotelByName(name);
            if(result != null){
                this.log(String.format("\tHotel %s wurde aus der Datenbank geholt.", result.getName()));
            } else {
                this.log("\tKein Hotel gefunden.");
            }
        }
        return result;
    }

    @Override
    public Hotel[] getAllHotels() {
        this.log("Zugriff auf Buchungssystem über Methode getAllHotels.");
        Hotel[] result;
        // We don't know if the cache contains all elements, so we can't simply return the whole cache.
        // Thus, we must always re-fetch everything.
        // A possible alternative would be to create a "*"-Cache-Element with all hotels in it,
        // but that would probably not scale very well.
        result = this.retrieval.getAllHotels();
        if (this.retrieval.cache != null) {
            for (Hotel hotel : result) {
                this.retrieval.cache.cacheResult(hotel.getName(), Collections.singletonList(hotel));
            }
            this.log(String.format("\tEs wurden %d Hotels aus der Datenbank geholt und in den Cache geschrieben.", result.length));
        } else {
            this.log(String.format("\tEs wurden %d Hotels aus der Datenbank geholt.", result.length));
        }
        return result;
    }

    private void log(String message) {
        String timestamp = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm:ss").format(LocalDateTime.now());
        System.out.printf("[%s]: %s%n", timestamp, message);
    }

    public void printHotel(Hotel hotel) {
        if (hotel != null) {
            this.log(String.format("\t%-3d\tName: %-20s\tCity: %-15s", hotel.getId(), hotel.getName(), hotel.getCity()));
        } else {
            this.log("\tKein Hotel gefunden.");
        }
    }
}