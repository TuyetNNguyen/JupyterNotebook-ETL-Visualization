package edu.upenn.cit594.datamanagement;
import edu.upenn.cit594.util.State;
import edu.upenn.cit594.util.StateAndTweetModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/**
 * The StateReader class is responsible for reading state information from a file
 * and write Tweet objects into DataProcessor
 */
public class StateReader {
    private final String statesFile;


    /**
     * StateReader constructor with the specified states file path
     * @param statesFile The path to the states file
     */
    public StateReader(String statesFile) {
        this.statesFile = statesFile;
    }


    /**
     * Reads states from the specified states file and adds them to the DataProcessor
     */
    public void readStates(StateAndTweetModel stateAndTweetModel) {
        try (BufferedReader reader = new BufferedReader(new FileReader(statesFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                State state = parseState(line);
                stateAndTweetModel.addState(state);
            }
        } catch (IOException e) {
            System.err.println("Error reading states file: " + e.getMessage());
        }
    }


    /**
     * Parses a line of state information and creates a State object
     * @param line
     * @return The parsed State object
     */
    private State parseState(String line) {
        // Split the line into parts using LineSplitter to handle invalid line format
        String[] parts = TweetReader.splitLine(line, ",", 3);
        // Extract and trim state details
        String name = parts[0].trim();
        double latitude = Double.parseDouble(parts[1].trim());
        double longitude = Double.parseDouble(parts[2].trim());
        return new State(name, latitude, longitude);
    }
}





