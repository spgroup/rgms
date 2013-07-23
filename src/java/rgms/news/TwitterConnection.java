package rgms.news;

import twitter4j.*;

import java.util.List;

public class TwitterConnection {

    private static Paging pageConfig = new Paging(1, 10);
    private static Twitter twitter = new TwitterFactory().getInstance();

    public boolean canConnect(String user) {
        try {
            twitter.getUserTimeline(user);
            return true;
        } catch (TwitterException te) {
            return false;
        }

    }

    public List<Status> getTimeLine(String user) {
        List<Status> statusList = null;
        try {
            statusList = twitter.getUserTimeline(user, pageConfig);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
        }
        return statusList;
    }

}
