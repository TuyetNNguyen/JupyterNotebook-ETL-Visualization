package edu.upenn.cit594.util;


/**
 * The State class represents information about a state, including its name, longitude, and latitude
 */
public class State {

    private String name;
    private double longitude;
    private double latitude;

    /**
     * State constructor
     * @param name
     * @param longitude
     * @param latitude
     */
    public State(String name, double longitude, double latitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }


    /**
     * Gets the longitude coordinate of the state
     * @return The state longitude
     */
    public double getLongitude() {
        return longitude;
    }


    /**
     * Gets the latitude coordinate of the state
     * @return The state latitude
     */
    public double getLatitude() {
        return latitude;
    }


    /**
     * Gets the name of the state
     * @return The state name
     */
    public String getName() {
        return name;
    }
}
