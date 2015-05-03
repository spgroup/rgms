package pages

class BibtexGenerateFilePage extends FormPage {
    static url = "bibtexGenerateFile/home"

    static at = {
        title ==~ /Member Listagem/
    }

    static content = {
        booktitle {
            $("input", id: "booktitle")
        }
        flashmessage {
            $("div", class: "message")
        }
    }

    def showBibtex() {
        $('a.Generate All BibTex').click()
    }

    def verificarEntrada(String entrada) {

    }

    def select(String s) {
        $('div', id: 'status').find('a', text: s).click()
    }
}