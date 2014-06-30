package pages
// #if($bibtexGenerateFile)
class BibtexGenerateFilePage extends FormPage {
    static url = "bibtexGenerateFile/home"

    static at = {
        def titleName = /${(new GetPageTitle()).msg("bibtex.generate.title")}/
        title ==~ titleName
    }

    static content = {
        booktitle {
            $("input", id: "booktitle")
        }
        flashmessage {
            $("div", class: "message")
        }
    }

    def select(String s) {
        $('div').find('a', text: s).click()
    }

    def getBibtexDetails() {
        return $('textarea#bibtexContent').value()
    }
}
// #end