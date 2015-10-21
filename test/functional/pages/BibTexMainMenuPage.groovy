package pages

import geb.Page

/**
 * Created by Luís Delgado on 18/04/15.
 */
class BibTexMainMenuPage extends Page {
    static url = "/bibtexmainmenu/home"

    static at = {
        title ==~ /Member Listagem/
    }

    static content = {
        bibTexEntry {
            $("textarea", id: "bibtextManual")
        }
        buttonEntry {
            $("input", id: "botao")
        }
    }

    def verificarEntrada(String bibtexManual){
        $(id: "bibtextManual").value()
    }

    def select(String s) {
        $(id: "botao").find('b', text: s).click()
    }
}
