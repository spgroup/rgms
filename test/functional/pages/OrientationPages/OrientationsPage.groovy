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
	}


	def selectNewOrientation() {
		$('a.create').click()
	}




    def getRow(){

        def listDiv = $('div', id: 'list-orientation')
        def orientationTable = (listDiv.find('table'))[0]
        def orientationRow  = orientationTable.find('tbody').find('tr')

        return orientationRow
    }

    def selectViewOrientation(title) {
        getRow()
        def showLink = getRow().find('td').find([text:title])
        showLink.click()
	}


    def checkOrientationAtList(title,row){
        def orientationColumns = getRow()[row].find('td')

        def testorientation = Orientation.findByTituloTese(title)
		assert orientationColumns[1].text() == testorientation.tipo
		assert orientationColumns[2].text() == testorientation.orientando
		assert orientationColumns[4].text() == testorientation.tituloTese
	}

    def checkIfOrientationListIsEmpty(){
        def conferenciaColumns = getRow()[0].find('td')

        assert conferenciaColumns.size() < 8
    }

    def uploadWithoutFile(){
        $('input.save').click()
    }
}
