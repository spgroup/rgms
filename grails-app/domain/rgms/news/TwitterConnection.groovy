package rgms.news

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

class TwitterConnection {
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
