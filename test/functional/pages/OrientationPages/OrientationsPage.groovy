package pages.OrientationPages

import geb.Page
import pages.GetPageTitle

class OrientationsPage extends Page {
    static url = "orientation/list"


    static at = {

        //title ==~ /Orientation Listagem/

        GetPageTitle gp = new GetPageTitle()
        def currentOrientation = gp.msg("default.orientation.label")
        def currentTitle = gp.msg("default.list.label", [currentOrientation])

        title ==~ currentTitle
    }

    static content = {
        flashmessage {
            $("div", class: "message")
        }
    }

    def selectNewOrientation() {
        $('a', class: 'create').click()
    }

    def selectViewOrientation(String title) {
        $("a", text: title).click()
    }

    private Object getTdOnRow(row) {
        //noinspection GroovyAssignabilityCheck
        getRow()[row].find('td')
    }

    def checkIfOrientationListIsEmpty() {
        def conferenciaColumns = getTdOnRow(0)
        assert conferenciaColumns.size() < 8
    }

    def uploadWithoutFile() {
        $('input.save').click()
    }
}
