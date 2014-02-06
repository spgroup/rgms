package pages


class BookChapterSavePage extends FormPage {
    static url = "bookChapter/save"

    static at = {
        //GetPageTitle gp = new GetPageTitle()
        title ==~ /Capítulo de livro Listagem/
    }

    static content = {
        flashmessage {
            $("div", class: "message", role: "status")
        }
    }
}
