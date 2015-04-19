package pages.ResearchGroup

import geb.Page

class ResearchGroupCreatePage extends Page {
    static url = "researchGroup/create"

    static at = {
        title ==~ /Criar Grupo de Pesquisa/
    }

    static content = {
    }

    def fillResearchGroupDetails(String name) {
        $("form").name = name
        $("form").twitter = "spg"
        $("form").description = "A research group called " + name
    }
    

    def clickOnCreate() {
        $("input", name: "create").click()
    }

}
