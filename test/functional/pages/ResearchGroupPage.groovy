package pages

import geb.Page

class ResearchGroupPage extends Page {
    static url = "researchGroup/list"

    static at = {
         
        title ==~ /Grupo de Pesquisa Listagem/

    }

    static content = {
    }

    def selectNewResearchGroup() {
        $('a.create').click()
    }

    def showResearchGroup(String a) {
        $('a', text: a).click()
    }
}
