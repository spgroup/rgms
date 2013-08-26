package pages

import geb.Page
import rgms.publication.Ferramenta

class FerramentaShowPage extends Page{
	static url = "ferramenta/show"

	static at = {
		title ==~ /Ver Ferramenta/
	}

	static content = {
	}

	def editFerramenta(){
		$('a.edit').click()
	}

	def deleteFerramenta(){
		assert withConfirm(true) {
			$('input.delete').click()
		}
	}

    def checkFerramentaTitle(title){
        def listInformations = $('ol', class : 'ferramenta')
        def rowTitle = (listInformations.find('li'))[0]
        def titleFerramenta = rowTitle.find('span', class: 'property-value')

        def editedFerramenta = Ferramenta.findByTitle(title)
        assert titleFerramenta.text() == editedFerramenta.title
    }
}
