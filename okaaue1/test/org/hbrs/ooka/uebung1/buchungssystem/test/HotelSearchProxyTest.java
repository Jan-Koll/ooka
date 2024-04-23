package org.hbrs.ooka.uebung1.buchungssystem.test;

import org.hbrs.ooka.uebung1.buchungssystem.Cache;
import org.hbrs.ooka.uebung1.buchungssystem.Hotel;
import org.hbrs.ooka.uebung1.buchungssystem.HotelSearchProxy;

import static org.junit.jupiter.api.Assertions.*;

class HotelSearchProxyTest {

    @org.junit.jupiter.api.Test
    void getHotelByNameWithCache() {
        Cache cache = new Cache();
        HotelSearchProxy hotelSearchProxy = new HotelSearchProxy(cache);
        hotelSearchProxy.openSession();
        String name = "WinzerHotel";
        Hotel hotelA = hotelSearchProxy.getHotelByName(name);
        Hotel hotelB = hotelSearchProxy.getHotelByName(name);
        Hotel hotelC = hotelSearchProxy.getHotelByName("Riu Plaza");
        assertSame(hotelA, hotelB);
        assertNotNull(hotelA);
        assertNotNull(hotelB);
        assertEquals(name, hotelA.getName());
        assertEquals(name, hotelB.getName());
        assertEquals(hotelA.getCity(), hotelB.getCity());
        assertEquals(hotelA.getId(), hotelB.getId());
        assertNotNull(hotelC);
        assertNotSame(hotelA, hotelC);
        assertNotSame(hotelB, hotelC);
        assertNotEquals(hotelA.getName(), hotelC.getName());
        cache.remove(name);
        Hotel hotelD = hotelSearchProxy.getHotelByName(name);
        assertNotNull(hotelD);
        assertEquals(hotelD.getId(), hotelA.getId());
        hotelSearchProxy.closeSession();
    }

    @org.junit.jupiter.api.Test
    void getHotelByNameWithoutCache() {
        HotelSearchProxy hotelSearchProxy = new HotelSearchProxy();
        hotelSearchProxy.openSession();
        String name = "WinzerHotel";
        Hotel hotelA = hotelSearchProxy.getHotelByName(name);
        Hotel hotelB = hotelSearchProxy.getHotelByName(name);
        hotelSearchProxy.closeSession();
        assertNotNull(hotelA);
        assertNotNull(hotelB);
        assertEquals(name, hotelA.getName());
        assertEquals(name, hotelB.getName());
    }


    @org.junit.jupiter.api.Test
    void getHotelByNameWithNoMatch() {
        HotelSearchProxy hotelSearchProxy = new HotelSearchProxy();
        hotelSearchProxy.openSession();
        Hotel result = hotelSearchProxy.getHotelByName("404 not found");
        hotelSearchProxy.closeSession();
        assertNull(result);
    }

    @org.junit.jupiter.api.Test
    void getAllHotels() {
        Cache cache = new Cache();
        HotelSearchProxy hotelSearchProxy = new HotelSearchProxy(cache);
        hotelSearchProxy.openSession();
        Hotel[] hotels = hotelSearchProxy.getAllHotels();
        assertNotNull(hotels);
        Hotel hotelA = hotels[0];
        Hotel hotelB = hotelSearchProxy.getHotelByName(hotelA.getName());
        hotelSearchProxy.closeSession();
        assertEquals(hotelA.getId(), hotelB.getId());
        assertNotEquals(1, hotels.length);
    }

    @org.junit.jupiter.api.Test
    void printHotelEmpty() {
        HotelSearchProxy hotelSearchProxy = new HotelSearchProxy();
        hotelSearchProxy.openSession();
        Hotel result = hotelSearchProxy.getHotelByName("404 not found");
        hotelSearchProxy.closeSession();
        hotelSearchProxy.printHotel(result);
    }
}