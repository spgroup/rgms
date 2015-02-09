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

    def fillTextField(String str) {
        $("form").searchTextField = str
    }

    def selectButton(String btnName) {
        $("input", id:"GenerateBibTex").click()
    }
}
