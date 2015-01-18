package pages.member

import geb.Page
import pages.GetPageTitle

class MemberSearchPage extends Page {
    static url = "member/search"

    static at = {
        
        GetPageTitle gp = new GetPageTitle()
        def memberLabel = gp.msg("member.label")
        def listLabel = gp.msg("default.search.label", [memberLabel])
        title ==~ listLabel

    }

    static content = {
    }

    def fillSearchBox(String name){
        $("form").name = name
    }

    def selectSearchButton(){
        $("input", type: "submit", class: "search").click()
    }

    def resultsListContains(String name){
        return $('table').find('td',text: name) != null
    }

}
