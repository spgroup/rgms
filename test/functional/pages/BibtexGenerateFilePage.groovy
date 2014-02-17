package pages

class BibtexGenerateFilePage extends FormPage {
    static url = "bibtexGenerateFile/home"

    static at = {
        title ==~ /BibTexImport Listagem/
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
}
