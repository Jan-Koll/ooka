package org.hbrs.ooka.uebung1.buchungssystem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HotelRetrieval implements HotelSearch {
    private final DBAccess db = new DBAccess();
    Cache cache;

    public HotelRetrieval() {
        this.cache = null;
    }

    public HotelRetrieval(Cache cache) {
        this.cache = cache;
    }

    @Override
    public Hotel[] getAllHotels() {
        List<String> searchResult = this.db.getObjects(DBAccess.HOTEL, "*");
        ArrayList<Hotel> hotelList = new ArrayList<>();
        Iterator<String> searchResultIterator = searchResult.iterator();
        while (searchResultIterator.hasNext()) {
            int hotelId = Integer.parseInt(searchResultIterator.next());
            if (!searchResultIterator.hasNext()) break;
            String hotelName = searchResultIterator.next();
            if (!searchResultIterator.hasNext()) break;
            String hotelCity = searchResultIterator.next();

            hotelList.add(new Hotel(hotelId, hotelName, hotelCity));
        }
        searchResultIterator.remove();

        Hotel[] result = new Hotel[hotelList.size()];
        return hotelList.toArray(result);
    }

    @Override
    public Hotel getHotelByName(String name) {
        List<String> searchResult = this.db.getObjects(DBAccess.HOTEL, name);
        if (searchResult.size() < 3) return null;
        int hotelId = Integer.parseInt(searchResult.get(0));
        String hotelName = searchResult.get(1);
        String hotelCity = searchResult.get(2);

        return new Hotel(hotelId, hotelName, hotelCity);
    }

    @Override
    public void closeSession() {
        this.db.closeConnection();
    }

    @Override
    public void openSession() {
        this.db.openConnection();
    }
}
