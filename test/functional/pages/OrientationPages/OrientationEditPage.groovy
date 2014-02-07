package pages.OrientationPages

import geb.Page
import pages.GetPageTitle

class OrientationEditPage extends Page {
    static url = "orientation/edit/1"

    static at = {
        //title ==~ /Editar Orientation/

        GetPageTitle gp = new GetPageTitle()
        def currentOrientation = gp.msg("default.orientation.label")
        def currentTitle = gp.msg("default.edit.label", [currentOrientation])

        title ==~ currentTitle
    }

    static content = {
        flashmessage {
            $("div", class: "message")
        }
    }

    def editTituloTese(String novovalor) {
        $("form").tituloTese = novovalor
    }

    def editYear(String newYear) {
        $("form").anoPublicacao = Integer.parseInt(newYear)
    }


    def select(String s) {
        $("form").find("input", value: s).click()
    }

    def confirmEdit() {
        $('input', class: 'save').click()
    }


    def delete() {
        assert withConfirm(true) { $("form").find('input', class: 'delete').click() }
    }
}
