package pages.ResearchGroup

import geb.Page

class ResearchGroupListPage extends Page {
    static url = "researchGroup/list"

    static at = {
        String teste = "Grupo de Pesquisa Listagem"
        /*"/" +  GetPageTitle.getMessage("researchGroup.label") +
            " " + GetPageTitle.getMessage("default.button.list.label") + "/"       */
        title ==~ teste
    }

    static content = {
    }

    def select(String s) {
        $('div', id: 'status').find('a', text: s).click()
    }

    def selectResearchGroup(String s) {
        $('div').find('a', text: s).click()
    }
}