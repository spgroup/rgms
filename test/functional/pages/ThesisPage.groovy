package pages

import geb.Page

/**
 * @author carloscemb
 */
class ThesisPage extends Page {
    static url = "tese/list"

    static at = {
        //title ==~ /Tese Listagem/
        GetPageTitle gp = new GetPageTitle()
        def currentTese = gp.msg("default.tese.label")
        def currentTitle = gp.msg("default.list.label", [currentTese])
        title ==~ currentTitle
    }

    def selectNewThesis() {
        $('a.create').click()
    }
}
