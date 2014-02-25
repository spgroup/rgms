package pages.researchProject

import pages.FormPage
import pages.GetPageTitle

/**
 * Created by Bruno Soares on 24/02/14.
 */
class ResearchProjectPage extends FormPage {
    static url = "researchProject/list"

    static at = {
        GetPageTitle gp = new GetPageTitle()
        def currentReseachProject = gp.msg("default.researchProject.label")
        def currentTitle = gp.msg("default.list.label", [currentReseachProject])
        title ==~ currentTitle
    }

    static content = {
        flashmessage {
            $("div", class: "message")
        }
    }

    def selectNewReseachGroup() {
        $('a', class: 'create').click()
    }

    def backToHome(){
        $('a', class:'home').click()
    }

    def submitXML(){
        $('input', class:'save').click()
    }

    def hasInvalidXMLSubmited(){
        GetPageTitle gp = new GetPageTitle()
        return gp.msg('default.xml.parserror.message') == $("div", class: "message", role: "status").text()
    }

    def selectReseachGroup(String name){
        $("a", text: name).click()
    }

}
