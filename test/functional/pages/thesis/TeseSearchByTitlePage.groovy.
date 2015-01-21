package pages.thesis

import geb.Page
import pages.GetPageTitle

class TeseSearchByTitlePage {

    static at = {
        GetPageTitle gp = new GetPageTitle()
        def teseLabel = gp.msg("tese.slabel")
        def listLabel = gp.msg("default.searchByTitle.label", [teseLabel])
        assert title ==~ listLabel
    }

    def resultsListContains(String title){
        return $('table').find('td',text: title) != null
    }

    def selectSearchButtonByTitle(){
        $("input", type: "submit", class: "search").click()
    }

    static content = {
    }

    def fillSearchBox(String title){
        $(".inputTitle").language() << title
    }
}
