package pages

import geb.Page

class ResearchGroupListPage extends Page {
    static url = "researchGroup/list"

    static at = {
        title ==~ /Research Group Listagem/
    }

    static content = {
    }

    def selectResearchGroup(String s) {
        $('div').find('a', text: s).click()
    }
}