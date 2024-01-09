package tweets.datamanagement;
import tweets.util.StateAndTweetModel;
import tweets.util.Tweet;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/**
 * The TextTweetReader class is responsible for reading tweet information from a tab-separated text file
 * and write Tweet objects into DataProcessor
 */
public class TextTweetReader extends TweetReader {
    private final String tweetsFile;


    /**
     * TextTweetReader constructor with the specified tweets file path
     * @param tweetsFile
     */
    public TextTweetReader(String tweetsFile) {
        this.tweetsFile = tweetsFile;
    }


    /**
     * Reads tweets from the specified tab-separated text file and returns a list of Tweet objects
     * @return A list of Tweet objects
     */
    @Override
    public void readTweets(StateAndTweetModel stateAndTweetModel) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(tweetsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                double[] locationArray = parseLocation(line);
                String text = parseText(line);

                Tweet tweet = new Tweet(locationArray, text);
                stateAndTweetModel.addTweet(tweet);
            }
        } catch (IOException e) {
            System.err.println("Error reading text tweets file: " + e.getMessage());
            throw e;
        }
    }



    /**
     * Parses the location information from a line of tweet data
     * @param line
     * @return The parsed location as a double array.
     */
    private double[] parseLocation(String line) {
        // Split the line into parts using LineSplitter to handle invalid line format
        String[] fields = splitLine(line, "\t", 4);
        // Extract and parse latitude and longitude
        String[] coordinates = fields[0].substring(1, fields[0].length() - 1).split(", ");
        double latitude = Double.parseDouble(coordinates[0]);
        double longitude = Double.parseDouble(coordinates[1]);
        return new double[]{latitude, longitude};
    }


    /**
     * Parses the text information from a line of tweet data
     * @param line
     * @return The parsed text
     */
    private String parseText(String line) {
        // Split the line into parts using LineSplitter to handle invalid line format
        String[] fields = splitLine(line, "\t", 4);
        // Extract and return the text
        return fields[3];
    }
}
