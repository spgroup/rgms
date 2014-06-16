package pages.researchProject

import pages.FormPage
import pages.GetPageTitle
import steps.FunderTestDataAndOperations

/**
 * Created by Bruno Soares on 24/02/14.
 */
class ResearchProjectPageCreatePage extends FormPage{
    static url = "researchProject/create"

    static at = {
        GetPageTitle gp = new GetPageTitle()
        def currentReseachProject = gp.msg("default.researchProject.label")
        def currentTitle = gp.msg("default.create.label", [currentReseachProject])
        title ==~ currentTitle
    }

    static content = {
        flashmessage {
            $("div", class: "message")
        }
    }

    def fillResearchProjectDetails(projectName) {
        $("form").projectName = projectName
        $("form").description = "Neste projeto pretendemso definir e validar um método para a implementação de aplicações orientadas a Aspectos Complexas web. Em particular, este método deve suportar uma abordagem progressiva para implementação orientada a objetos, de forma que aspectos de distribuição, concorrência, e persistência não sejam inicialmente considerados pelo processo de implementação, mas sejam gradualmente introduzidos, preservando os requisitos funcionais da aplicação."
        $("form").status = "CONCLUIDO"
        $("form").startYear = 2001
        $("form").endYear = 2004
        $("form").responsible = "Paulo Henrique Monteiro Borba"
    }

    def fillResearchProjectDetailsWithBlankDescription(projectName) {
        $("form").projectName = projectName
        $("form").description = ""
        $("form").status = "CONCLUIDO"
        $("form").startYear = 2001
        $("form").endYear = 2004
        $("form").responsible = "Paulo Henrique Monteiro Borba"
    }

    def createResearchProject() {
        $("form").create().click()
    }

    def checkHasErrorMsg() {
        def listDiv = $('div', id: 'create-researchProject')
        def researchGroupUl = (listDiv.find('ul'))[0]
        def researchGroupRows = researchGroupUl.find('li')
        int size = researchGroupRows.size();

        assert (size > 0)
    }
}
