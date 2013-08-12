package pages

import geb.Page
import rgms.tool.TwitterTool

class VisitShowPage extends Page {
    static url = "visit/show/1"

    static at = {
        //title ==~ /Ver Visita/
        GetPageTitle gp = new GetPageTitle()
        def currentVisit = gp.msg("default.visit.label")
        def currentTitle = gp.msg("default.show.label", [currentVisit])

        title ==~ currentTitle
    }

    static content = {
    }

    def select(String e,v) {
        if(v == 'delete'){
            assert withConfirm(true) { $("form").find(e, class: v).click() }
        } else {
            $("form").find(e, class: v).click()
        }
    }

    def deleteVisit() {
        assert withConfirm(true) { $("form").find('input', class: 'delete').click() }
    }

    def editVisit() {
        $("form").find('a', class: 'edit').click()
    }

    def addTwitter(String visita) {
        TwitterTool.addTwitterHistory(visita, null)
    }

//#if( $Twitter )
    def clickOnTwitteIt (String login, pw){
        $("#button_twitter").click()
        //$("#password").text = login
        //$("#username_or_email").text = pw
        //$("input", type:"submit", class:"button selected submit", value:"Entrar e Tweetar").click()
        //<input type="submit" class="button selected submit" value="Entrar e Tweetar">
    }
//#end
}
