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
        tituloTese != null
    }

    def fillOrientationDetails() {
        fillOrientationDetails("The Book is on the table")
    }

    def fillOrientationDetails(title) {

        //def orientador = TestDataAndOperations.members[0]

        $("form").tituloTese = title
        $("form").tipo = "Mestrado"
        $("form").orientando = "Tomaz"
        //$("form").orientador = orientador
        $("form").anoPublicacao = 2013
        $("form").instituicao = "UFPE"
        //selectCreateOrientation()

        // Could parametrize, obtaining data from class TestDataAndOperations
    }

    def fillOrientationDetailsWithGivenYear(title, year) {
        $("form").tituloTese = title
        $("form").tipo = "Mestrado"
        $("form").orientando = "Tomaz"
        $("form").anoPublicacao = year
        $("form").instituicao = "UFPE"
    }


    def selectCreateOrientation() {
        $("input", name: "create").click()

    }
}
