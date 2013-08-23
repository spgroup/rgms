package pages

import geb.Page

class ThesisShowPage extends Page {
    static url = "tese/show"

    static at = {
        GetPageTitle gp = new GetPageTitle()
        def currentTese = gp.msg("default.tese.label")
        def currentTitle = gp.msg("default.show.label", [currentTese])
        title ==~ currentTitle
	}
	
    static content = {

    }

}
