package pages.researchProject

import pages.FormPage
import pages.GetPageTitle

/**
 * Created by Bruno Soares on 24/02/14.
 */
class ResearchProjectPageShowPage extends FormPage {
    static url = "researchProject/show"

    static at = {
        GetPageTitle gp = new GetPageTitle()
        def currentReseachProject = gp.msg("default.researchProject.label")
        def currentTitle = gp.msg("default.show.label", [currentReseachProject])
        title ==~ currentTitle
    }

    static content = {
        flashmessage {
            $("div", class: "message")
        }
    }

    def backToList(){
        $('a', class:'list').click()
    }

    def selectNewReseachGroup() {
        $('a', class: 'create').click()
    }

    def selectEditReseachGroup() {
        $('a', class: 'edit').click()
    }

    def backToHome(){
        $('a', class:'home').click()
    }

    def delete() {
        assert withConfirm(true) { $("form").find('input', class: 'delete').click() }
    }
}
