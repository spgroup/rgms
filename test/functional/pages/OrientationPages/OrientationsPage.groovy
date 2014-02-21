package pages.OrientationPages

import geb.Page
import pages.GetPageTitle
import rgms.member.Orientation

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

        def id = Orientation.findByTituloTese(title).id
        $("a", text: id.toString()).click()

        /*def listDiv = $('div', id: 'list-orientation')
        def orientationTable = (listDiv.find('table'))[0]
        def orientationRow  = (orientationTable.find('tbody'))[0].find('tr')
        System.out.println("------------------------------------")
        System.out.println(orientationRow.toString())
        System.out.println("------------------------------------")
        def showLink = orientationRow.find('td').find([text:title])
        showLink.click()*/

        /*def showLink = (getRow().find([text:title]))[0].find('td')
        System.out.println("testando "+showLink)
        showLink[0].click()*/
    }

    def checkOrientationAtList(String title, row) {
        def orientationColumns = getTdOnRow(row)

        def testorientation = Orientation.findByTituloTese(title)
        //noinspection GroovyAssignabilityCheck
        assert orientationColumns[1].text() == testorientation.tipo
        //noinspection GroovyAssignabilityCheck
        assert orientationColumns[2].text() == testorientation.orientando
        //noinspection GroovyAssignabilityCheck
        assert orientationColumns[4].text() == testorientation.tituloTese
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
