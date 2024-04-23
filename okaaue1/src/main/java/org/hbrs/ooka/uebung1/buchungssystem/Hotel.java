package org.hbrs.ooka.uebung1.buchungssystem;

public class Hotel {
    private final int id;
    private final String name;
    private final String city;

    public Hotel(int id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getCity() {
        return this.city;
    }
}
