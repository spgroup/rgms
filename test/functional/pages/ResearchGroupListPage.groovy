package pages

import geb.Page

class ResearchGroupListPage extends Page {
    static url = "researchGroup/list"

    static at = {
        String teste = "/" +  GetPageTitle.getMessage("researchGroup.label") +
                " " + GetPageTitle.getMessage("default.button.list.label") + "/"
        title ==~  teste
    }

    static content = {
    }

    def selectResearchGroup(String s) {
        $('div').find('a', text: s).click()
    }
}