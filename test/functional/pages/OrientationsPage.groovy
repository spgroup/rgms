package pages

import geb.Page
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
   


	def selectViewOrientation(title) {
		def listDiv = $('div', id: 'list-orientation')
        def orientationTable = (listDiv.find('table'))[0]
        def orientationRow  = orientationTable.find('tbody').find('tr')
        def showLink = orientationRow.find('td').find([text:title])
        showLink.click()
	}


	def checkOrientationAtList(title,row){
		def listDiv = $('div', id: 'list-orientation')
		def orientationTable = (listDiv.find('table'))[0]
		def orientantionRows  = orientationTable.find('tbody').find('tr')
		def orientationColumns = orientationRows[row].find('td')

		def testorientation = Orientation.findByTituloTese(title)
		assert orientationColumns[1].text() == testorientation.tipo
		assert orientationColumns[2].text() == testorientation.orientando
		assert orientationColumns[4].text() == testorientation.tituloTese
	}
}
