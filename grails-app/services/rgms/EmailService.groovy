package rgms

/**
 * Created with IntelliJ IDEA.
 * User: Cynthia
 * Date: 25/08/13
 * Time: 00:56
 * To change this template use File | Settings | File Templates.
 */
class EmailService {

    public void sendEmail(def emailTo, def emailFrom, def title, def content){
        sendMail {
            to emailTo
            from emailFrom
            subject title
            body content
        }
    }
}
