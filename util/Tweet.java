package tweets.util;


/**
 * The Tweet class represents information about a tweet, including its location and text content
 */
public class Tweet {
    private double[] location;
    private String text;


    /**
     * Tweet constructor
     * @param location
     * @param text
     */
    public Tweet(double[] location, String text) {
        this.location = location;
        this.text = text;
    }


    /**
     * Gets the tweet latitude
     * @return The tweet latitude
     */
    public double getLatitude() {
        return location[0];
    }

    /**
     * Gets the tweet longitude
     * @return The tweet longitude
     */
    public double getLongitude() {
        return location[1];
    }

    /**
     * Gets the tweet (this method is used for testing)
     * @return The tweet
     */
    public String getText() {
        return text;
    }

}
