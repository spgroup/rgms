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

    def setValues(String title, String publisher, int volume, String pages, String filename) {
        $('form').title = title
        $("form").publisher = publisher
        $("form").volume = volume
        $("form").pages = pages
        $("form").file = new File(".").getCanonicalPath() + File.separator + "test" + File.separator + "functional" + File.separator + "steps" + File.separator + filename
        saveBook()
    }

    def fillBookDetails(title, filename) {
        setValues(title, "Person", 1, "20", filename)
    }
	
	def fillBookDetails(title, filename, author) {
		setValues(title, author, 1, "20", filename)
	}

    def saveBook() {
        $("form").create().click()
    }
}