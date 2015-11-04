package pages

import geb.Page

/**
 * Created by luisdelgado on 18/04/15.
 */
class BibTexMainMenuPage extends Page{
    static url = "/bibtexMainMenu/home"

    static at = {
        title ==~ /Member Listagem/
    }

    static content = {


    }

    def verificarEntrada(entrada){
        $(entrada).click()
    }

    def select(o){
        $(o).click()
    }
}
