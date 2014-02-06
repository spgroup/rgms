package pages.OrientationPages

import geb.Page
import pages.GetPageTitle


class OrientationCreatePage extends Page {
    static url = "orientation/create"

    static at = {

        //title ==~ /Criar Orientation/

        GetPageTitle gp = new GetPageTitle()
        def currentOrientation = gp.msg("default.orientation.label")
        def currentTitle = gp.msg("default.create.label", [currentOrientation])

        title ==~ currentTitle
        //tituloTese != null
    }

    def fillOrientationDetails() {
        fillOrientationDetails("The Book is on the table")
    }

    def fillOrientationDetails(title) {
        fillOrientationDetailsWithGivenYear(title, 2013)
    }

    def fillOrientationDetailsWithGivenYear(title, year) {
        $("form").tipo = "Mestrado"
        $("form").orientando = "Tomaz"
        $("form").tituloTese = title
        $("form").anoPublicacao = year
        $("form").instituicao = "UFPE"
        $("form").curso = "Ciencia da Computacao"
    }

    def selectCreateOrientation() {
        //$("input", name: "create").click()
        $("input", name: "create", class: "save").click()
    }

}
