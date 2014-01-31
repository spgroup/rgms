package rgms.news

import twitter4j.*

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
            //TODO ajeitar para funcionar com a nova exigência do twitter: https://dev.twitter.com/docs/security/using-ssl
            //TODO usar nova versão do plugin twitter4j
        } catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get timeline: " + te.getMessage());
            //TODO deveria dar mensagem de erro decente via GUI
        }
		return statusList;
	}
}
