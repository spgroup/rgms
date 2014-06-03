package pages
// #if($bibtexGenerateFile)
class BibtexGenerateFilePage extends FormPage {
    def titleName = /${(new GetPageTitle()).getMessageServerLocale("bibtex.generate.title")}/
    static url = "bibtexGenerateFile/home"

    static at = {
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

}
// #end