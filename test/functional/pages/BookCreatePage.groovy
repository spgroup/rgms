package pages

/**
 * Created with IntelliJ IDEA.
 * User: dyego
 * Date: 23/02/14
 * Time: 10:08
 * To change this template use File | Settings | File Templates.
 */
class BookCreatePage extends FormPage {
    static url = "book/create"

    static at = {
        GetPageTitle gp = new GetPageTitle()

        def currentReport = gp.msg("default.book.label")
        title ==~ gp.msg("default.create.label", [currentReport])
        publisher != null
    }

    static content = {
        publisher {
            $("input", id: "publisher")
        }
        flashmessage {
            $("div", class: "message")
        }
    }

    def fillBookDetails() {
        def path = new File(".").getCanonicalPath() + File.separator + "test" + File.separator + "files" + File.separator + "NGS.pdf"
        fillBookDetails(path, "Next Generation Software Product Line Engineering")
    }

    def fillBookDetails(filename, title) {
        $("form").title = title
        $("form").file = filename
        $("form").publisher = "Person"
        $("form").volume = 1
        $("form").pages = "20"
    }

    def selectCreateBook() {
        $("input", name: "create").click()
    }

}