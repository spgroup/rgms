package pages

import geb.Page

class ResearchGroupShowPage extends Page {
    static url = "researchGroup/show/1"

    static at = {

        title ==~ /Ver Grupo de Pesquisa/

    }

    static content = {
    }

    def selectEditResearchGroup() {
        $('a', name: 'botaoEditar').click()
    }

    def selectRemoveResearchGroup() {
        $('input.delete').click()
    }
}
