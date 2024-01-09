package tweets.datamanagement;
import tweets.util.StateAndTweetModel;
import tweets.util.Tweet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;


/**
 * The JsonTweetReader class is responsible for reading tweet information from a Json file
 * and write Tweet objects into StateAndTweetModel
 */
public class JsonTweetReader extends TweetReader {
    private final String tweetsFile;


    /**
     * JsonTweetReader constructor with the specified JSON tweets file
     * @param tweetsFile
     */
    public JsonTweetReader(String tweetsFile) {
        this.tweetsFile = tweetsFile;
    }



    /**
     * Reads tweets from the specified JSON file and returns a list of Tweet objects
     * @return A list of Tweet objects
     */
    @Override
    public void readTweets(StateAndTweetModel stateAndTweetModel) throws IOException, ParseException {
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(tweetsFile)) {
            // Parse the JSON array from the file
            JSONArray jsonArray = (JSONArray) parser.parse(reader);

            // Iterate over each JSON object in the array
            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;

                // Extract location and text
                double[] locationArray = extractLocation(jsonObject);
                String text = (String) jsonObject.get("text");

                // Create a new Tweet and add it to the list
                Tweet tweet = new Tweet(locationArray, text);
                stateAndTweetModel.addTweet(tweet);
            }
        } catch (IOException | ParseException e) {
            System.err.println("Error reading JSON tweets file: " + e.getMessage());
            throw e;
        }
    }


    /**
     * Extracts the location (latitude and longitude) from a JSON object
     * @param jsonObject
     * @return A double array representing the location [latitude, longitude]
     */
    private double[] extractLocation(JSONObject jsonObject) {
        // Extract the 'location' array from the JSON object
        JSONArray locationArray = (JSONArray) jsonObject.get("location");
        // Extract latitude and longitude from the array
        double latitude = (double) locationArray.get(0);
        double longitude = (double) locationArray.get(1);
        return new double[]{latitude, longitude};
    }
}
