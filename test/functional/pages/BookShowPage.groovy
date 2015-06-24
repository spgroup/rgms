//#if($Book)
package pages

/**
 * Created by Luis on 26/05/2015.
 */

import geb.Page
import pages.GetPageTitle

class BookShowPage extends Page {
    static url = "book/show/1"

    static at = {
        //title ==~ /Ver Book/
        GetPageTitle gp = new GetPageTitle()
        def currentBook = gp.getMessageServerLocale("default.book.label")
        def currentTitle = currentBook + " " + gp.getMessageServerLocale("default.button.list.label")
    }

    static content = {
    }

    def select(String e, v) {
        if (v == 'delete') {
            assert withConfirm(true) { $("form").find(e, class: v).click() }
        } else {
            $("form").find(e, class: v).click()
        }
    }
//#if ($Twitter)
    def clickOnTwitteIt(String login, pw) {
        $("#button_twitter").click()
        //$("#password").text = login
        //$("#username_or_email").text = pw
        //$("input", type:"submit", class:"button selected submit", value:"Entrar e Tweetar").click()
        //<input type="submit" class="button selected submit" value="Entrar e Tweetar">
    }
//#end
//#if ($Facebook)
    def clickOnFacebookIt(String login, pw, message) {
        $("#share_facebook").click()
        //$("#password").text = login
        //$("#username_or_email").text = pw
        //$("input", type:"submit", class:"button selected submit", value:"Entrar e Tweetar").click()
        //<input type="submit" class="button selected submit" value="Entrar e Tweetar">
    }

    def clickOnShareOnFacebook() {
        $("form").find(id: 'share').click()
    }
//#end
}
//#end

