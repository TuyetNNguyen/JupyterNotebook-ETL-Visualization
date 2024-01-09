package tweets.ui;
import tweets.logging.Logger;
import tweets.processor.FluTweetsProcessor;
import tweets.processor.LocationOfFluTweetProcessor;
import tweets.util.StateAndTweetModel;
import tweets.util.Tweet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


/**
 * The UserInterface class handles the interaction with the user
 * Display summaries of flu-related state data
 */
public class UserInterface {
    private StateAndTweetModel stateAndTweetModel;
    private LocationOfFluTweetProcessor fluLocationProcessor;
    private FluTweetsProcessor fluTweetProcessor;
    private Logger fluEventLogger;


    /**
     * UserInterface constructor
     * @param fluTweetProcessor     The processor for flu-related tweets
     * @param fluLocationProcessor  The processor for determining the location of flu-related tweets
     * @param fluEventLogger        The logger for flu-related events
     */
    public UserInterface(StateAndTweetModel stateAndTweetModel, FluTweetsProcessor fluTweetProcessor, LocationOfFluTweetProcessor fluLocationProcessor, Logger fluEventLogger) {
        this.stateAndTweetModel = stateAndTweetModel;
        this.fluTweetProcessor = fluTweetProcessor;
        this.fluLocationProcessor = fluLocationProcessor;
        this.fluEventLogger = fluEventLogger;
    }


    /**
     * Starts the user interface, displaying a menu and processing user input
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Main menu: ");
            System.out.println("1. Display summary of flu tweets for all states");
            System.out.println("2. Display summary of the Logger");
            System.out.println("3. End the program");
            System.out.print("Please enter your choice (1-3): ");

            int option = readOption(scanner);

            switch (option) {
                case 1:
                    displayFluTweetsSummary();
                    break;
                case 2:
                    displayLoggerSummary();
                    break;
                case 3:
                    System.out.println("Goodbye");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Let's try again. Please select a valid option between 1 and 3.");
            }
        }
    }



    /**
     * Reads and validates the user's input for menu options
     * @param scanner
     * @return The validated user input, representing a number between 1 and 3
     */
    private int readOption(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Hmm...invalid input! Please re-enter a number between 1 and 3.");
            scanner.next();
        }
        return scanner.nextInt();
    }



    /**
     * Displays the summary of states that have flu tweets
     */
    private void displayFluTweetsSummary() {
        // Get all the flu tweets
        FluTweetsProcessor fluTweetsProcessor = new FluTweetsProcessor(stateAndTweetModel);
        List<Tweet> fluTweets = fluTweetsProcessor.identifyFluTweets();

        // Counts of tweets for each state
        LocationOfFluTweetProcessor locationProcessor = new LocationOfFluTweetProcessor(stateAndTweetModel);
        Map<String, Integer> stateTally = locationProcessor.stateTweetsTallyMap(fluTweets);

        System.out.println("Below is the summary of all states that have flu tweets:");
        for (Map.Entry<String, Integer> entry : stateTally.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println();
    }


    /**
     * Displays the summary of flu-related log entries recorded
     */
    private void displayLoggerSummary() {
        Logger loggerReader = Logger.getInstance();

        String logFilePath = fluEventLogger.getLogFilePath();
        System.out.println("Logger file path:\t\t " + logFilePath);

        // Get all LogEntries with a specific file path
        List<String> allLogEntries = loggerReader.getAllLogEntries(logFilePath);

        System.out.println("Below is the summary of the log entries:");
        for (String logEntry : allLogEntries) {
            System.out.println(logEntry);
        }
        System.out.println();
    }
}
