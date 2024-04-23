package org.hbrs.ooka.uebung1;

import org.hbrs.ooka.uebung1.buchungssystem.Hotel;
import org.hbrs.ooka.uebung1.buchungssystem.HotelSearchProxy;
import org.hbrs.ooka.uebung1.buchungssystem.Cache;

public class Client{
    public static void main(String[] args){
        System.out.println("-----Test ohne Cache-----");
        HotelSearchProxy hotelSearchProxyWithoutCache = new HotelSearchProxy();
        hotelSearchProxyWithoutCache.openSession();
        // -----------------------------
        hotelSearchProxyWithoutCache.printHotel(hotelSearchProxyWithoutCache.getHotelByName("WinzerHotel"));
        hotelSearchProxyWithoutCache.printHotel(hotelSearchProxyWithoutCache.getHotelByName("WinzerHotel"));
        // -----------------------------
        hotelSearchProxyWithoutCache.closeSession();




        System.out.println("-----Test mit Cache-----");
        Cache cache = new Cache();
        HotelSearchProxy hotelSearchProxy = new HotelSearchProxy(cache);
        hotelSearchProxy.openSession();
        // -----------------------------
        hotelSearchProxy.printHotel(hotelSearchProxy.getHotelByName("WinzerHotel"));
        hotelSearchProxy.printHotel(hotelSearchProxy.getHotelByName("WinzerHotel"));
        for (Hotel hotel : hotelSearchProxy.getAllHotels()) {
            hotelSearchProxy.printHotel(hotel);
        }
        hotelSearchProxy.printHotel(hotelSearchProxy.getHotelByName("Riu Plaza"));
        cache.clear();
        // cache.remove("Riu Plaza");
        hotelSearchProxy.printHotel(hotelSearchProxy.getHotelByName("Riu Plaza"));
        hotelSearchProxy.printHotel(hotelSearchProxy.getHotelByName("Riu Plaza"));
        hotelSearchProxy.printHotel(hotelSearchProxy.getHotelByName("Peter Lustig"));

        // -----------------------------
        hotelSearchProxy.closeSession();
    }
}