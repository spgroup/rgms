package pages.ResearchGroup

import geb.Page

class ResearchGroupEditarPage extends Page {

    static at = {

        title ==~ /Editar Grupo de Pesquisa/
    }

    def selectAlterarResearchGroup() {
        $('input.save').click()
    }

    def changeResearchGroupDetails(String name) {
        $("form").name = name
        $("form").twitter = "SPG1"
        $("form").description = "grupo de pesquisa " + name
    }


    def changeChildOfTo(long id) {
        $("#childOf").value(id.toString())
    }

}
