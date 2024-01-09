package edu.upenn.cit594.datamanagement;
import edu.upenn.cit594.util.StateAndTweetModel;
import org.json.simple.parser.ParseException;
import java.io.IOException;


public abstract class TweetReader {
    public abstract void readTweets(StateAndTweetModel stateAndTweetModel) throws IOException, ParseException;


    /**
     * Creates a TweetReader based on the provided tweets file
     * @param tweetsFile
     * @return A TweetReader instance for the specified file type
     */
    public static TweetReader createTweetReader(String tweetsFile) {
        if (isJsonFile(tweetsFile)) {
            return new JsonTweetReader(tweetsFile);
        }

        if (isTextFile(tweetsFile)) {
            return new TextTweetReader(tweetsFile);
        }
        throw new IllegalArgumentException("Unrecognized file type for " + tweetsFile);

    }


    /**
     * Checks if the given file has a ".json" extension
     * @param fileName
     * @return true if the file has a ".json" extension, else false
     */
    private static boolean isJsonFile(String fileName) {
        return fileName.toLowerCase().endsWith(".json");
    }


    /**
     * Checks if the given file has a ".txt" extension
     * @param fileName
     * @return true if the file has a ".txt" extension, else false
     */
    private static boolean isTextFile(String fileName) {
        return fileName.toLowerCase().endsWith(".txt");
    }



    /**
     * Splits a line into parts using the specified delimiter and checks the number of expected parts
     * @param line           The input line to be split
     * @param delimiter      The delimiter to use for splitting the line
     * @param expectedParts  The expected number of parts after splitting
     * @return An array of strings representing the parts of the line
     */
    public static String[] splitLine(String line, String delimiter, int expectedParts) {
        String[] parts = line.split(delimiter);

        if (parts.length != expectedParts) {
            throw new IllegalArgumentException("Invalid format: Expected " + expectedParts + " parts, but found " + parts.length);
        }
        return parts;
    }

}
