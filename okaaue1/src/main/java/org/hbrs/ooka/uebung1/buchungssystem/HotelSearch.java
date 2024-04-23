package org.hbrs.ooka.uebung1.buchungssystem;

public interface HotelSearch {
    Hotel[] getAllHotels();

    Hotel getHotelByName(String hotelName);
    //Hotel getHotelById(int hotelId);
    //Hotel[] getHotelsByCity(String cityName);

    void openSession();

    void closeSession();
}
