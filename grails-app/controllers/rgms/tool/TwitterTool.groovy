package rgms.tool

import rgms.messagesTwitter.MessagesTwitter
import twitter4j.Status
import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.auth.AccessToken

public class TwitterTool{

    final static String consumerKey = "ZHgJv54dR7ZkYuKLvBeFJA";
    final static String consumerSecret = "9Qj0OVOSEbkVOdCl4X1XUnoHSA46lQuAF3zCd7Gdk";


	static twittersHistory = []

    public static void sendGTwitter(String articleName, String accessToken, String accessSecret, String text){
        TwitterFactory factory = new TwitterFactory()
        Twitter twitter = factory.getInstance()
        AccessToken accessTokenO = new AccessToken(accessToken, accessSecret)
    
		twitter.setOAuthConsumer(consumerKey, consumerSecret)
        twitter.setOAuthAccessToken(accessTokenO)
		def today = new Date ()
        Status status = twitter.updateStatus(today.toString()+" - "+text)
        twittersHistory.add([articleName : articleName, status : status.getId()])
    }
	
	public static void addTwitterHistory(String articleName, String status){
		println "addTwitterHistory="+articleName
		twittersHistory.add([articleName : articleName, status : status])
	}

    static public boolean consult(String articleName) {
		println "consult="+articleName
		def twitterFound = twittersHistory.find { articleNameTwitter ->
            articleNameTwitter.articleName == articleName
        }
        if (twitterFound == null){
            return false
        }
        twitterFound?.status != 0
    }

}