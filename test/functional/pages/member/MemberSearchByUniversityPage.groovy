package pages.member

import geb.Page
import pages.GetPageTitle
/**
 * Created by samuel on 20/01/15.
 */
class MemberSearchByUniversityPage extends Page{
    static at = {
        GetPageTitle gp = new GetPageTitle()
        def memberLabel = gp.msg("member.slabel")
        def listLabel = gp.msg("default.searchByTitle.label", [memberLabel])
        assert title ==~ listLabel
    }

    def resultsListContains(String name){
        return $('table').find('td',text: name) != null
    }

    def selectSearchButtonByUniversity(){
        $("input", type: "submit", class: "search").click()
    }

    static content = {
    }

    def fillSearchBox(String name){
        $(".inputUniversity").language() << name
    }

}

