package tweets.util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The StateAndTweetModel class provides the state information and flu-related tweets data schema
 * Passing data to all tiers
 */
public class StateAndTweetModel {
    private Map<String, State> states;
    private List<Tweet> tweets;

    /**
     * StateAndTweetModel constructor
     */
    public StateAndTweetModel() {
        states = new HashMap<>();
        tweets = new ArrayList<>();
    }


    /**
     * Adds a state to the collection of states
     * @param state
     */
    public void addState(State state) {
        // State name is the key, and state detail (name, long and latitude) is the value
        states.put(state.getName(), state);
    }

    /**
     * Adds a tweet to the collection of tweets
     * @param tweet The Tweet object to be added.
     */
    public void addTweet(Tweet tweet) {
        tweets.add(tweet);
    }


    /**
     * Gets the collection of states
     * @return The Map of states
     */
    public Map<String, State> getStates() {
        return states;
    }


    /**
     * Gets the collection of tweets
     * @return The List of tweets
     */
    public List<Tweet> getTweets() {
        return tweets;
    }
}
