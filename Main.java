package tweets;
import tweets.datamanagement.StateReader;
import tweets.datamanagement.TweetReader;
import tweets.logging.Logger;
import tweets.processor.FluTweetsProcessor;
import tweets.processor.LocationOfFluTweetProcessor;
import tweets.ui.UserInterface;
import tweets.util.StateAndTweetModel;
import org.json.simple.parser.ParseException;
import java.io.IOException;

/**
 * Entry point of the program
 * Initializes the files in the given order
 */
public class Main {


    /**
     * Takes command-line arguments for file paths and starts the program
     * @param args Command-line arguments for file paths in order: [flu_tweets.json or flu_tweets.txt] [states.csv] [log.txt]
     */
    public static void main(String[] args) throws IOException, ParseException {
        if (args.length != 3) {
            System.err.println("Unable to run the program. Please double check the name and path of the input files");
            return;
        }

        String tweetsFileName = args[0];
        String statesFileName = args[1];
        String logFileName = args[2];

        // Start the program
        startApplication(tweetsFileName, statesFileName, logFileName);
    }


    /**
     * Init the program
     * @param tweetsFileName The file path for the flu tweets data.
     * @param statesFileName The file path for the state data.
     * @param logFileName The file path for the log file.
     */
    public static void startApplication(String tweetsFileName, String statesFileName, String logFileName) throws IOException, ParseException {

        // Initialize tweet reader
        TweetReader tweetReader = TweetReader.createTweetReader(tweetsFileName);

        // Initialize state reader
        StateReader stateReader = new StateReader(statesFileName);

        // Initialize StateAndTweetModel
        StateAndTweetModel stateAndTweetModel = new StateAndTweetModel();

        // Load tweets and states into dataHandler
        tweetReader.readTweets(stateAndTweetModel);
        stateReader.readStates(stateAndTweetModel);

        // Initialize processors and logger
        FluTweetsProcessor fluTweetProcessor = new FluTweetsProcessor(stateAndTweetModel);
        LocationOfFluTweetProcessor fluLocationProcessor = new LocationOfFluTweetProcessor(stateAndTweetModel);
        Logger fluEventLogger = Logger.getInstance();
        fluEventLogger.setLogFilePath(logFileName);

        // Init UI and call start method to run the program
        UserInterface userInterface = new UserInterface(stateAndTweetModel, fluTweetProcessor, fluLocationProcessor, fluEventLogger);
        userInterface.start();
    }
}
