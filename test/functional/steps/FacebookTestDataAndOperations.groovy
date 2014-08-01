package steps

import rgms.member.Member
import rgms.publication.PublicationController

/**
 * Created with IntelliJ IDEA.
 * User: Alberto Junior
 * Date: 25/08/13
 * Time: 22:47
 * To change this template use File | Settings | File Templates.
 */
class FacebookTestDataAndOperations {

    static public void ShareArticleOnFacebook(String title){
        def member = new Member()
        member.access_token =  "CAAJIlmRWCUwBAN0r1puBTUa4vDZAKxWWlR5gN4qtgZAosBDKGUOLBquyKuHYQ0zxICioiarTJ66mpdZC08U4rHJOrtvXJCB8hMBcLKlQaTdwYZCgMTJtbFnQfIBZAxi6hRIkfw2fCSyCS6DuFIrGRThI53ZCzBOLsZD"
        member.facebook_id = "100006411132660"
        PublicationController.sendPostFacebook(member, title)
    }
}
