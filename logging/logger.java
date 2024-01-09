package tweets.logging;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Logger class manages the recording of flu-related tweets in a log file
 */
public class Logger {

    private static Logger instance;
    private PrintWriter writer;
    private String logFilePath;


    // Using List to store all log entries
    // to maintains the order of insertion and allows duplicate entries
    private List<String> logEntries = new ArrayList<>();


    /**
     * Private constructor to prohibit instantiation in other classes
     */
    private Logger(){};


    /**
     * Gets the Logger instance. If an instance does not exist, a new one is created
     * Using synchronized to ensure that only one thread can execute if instance == null
     * @return instance
     */
    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }



    /**
     * Appends a flu tweet entry to the log file
     * @param stateName The name of the state
     * @param fluTweet The text of the flu tweet
     */
    public void appendFluTweetToLog(String stateName, String fluTweet) {
        if (logFilePath != null) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(logFilePath, true))) {

                // Log the flu tweet to the file along with a linebreak
                String logEntry = stateName + "\t" + fluTweet;
                writer.println(logEntry);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Closes the logger
     */
    public void closeLogger() {
        // Only close the file if it was successfully created, prevent NullPointerException
        if (writer != null) {
            writer.close();
        }
    }


    /**
     * Sets or changes the log file path
     * If the file already exists, it will be opened in append mode instead of being overwritten
     * @param loggingFileName The path to the log file
     */
    public void setLogFilePath(String loggingFileName) {
        try {
            // Release the previous log file if it exists
            closeLogger();
            // Create a new file if the file does not exist
            // if it already exists, open it in append mode instead of being overwritten
            writer = new PrintWriter(new FileWriter(loggingFileName, true));

            // Store the file path
            logFilePath = loggingFileName;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeLogger(); // Only close the file if it was successfully created
        }
    }


    /**
     * Gets the log file path.
     * @return The log file path.
     */
    public String getLogFilePath() {
        return logFilePath;
    }


    /**
     * Get all log entries from the log file directly
     * @return List of all log entries
     */
    public List<String> getAllLogEntries(String logFilePath) {
        List<String> logEntries = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(logFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logEntries.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logEntries;
    }
}
