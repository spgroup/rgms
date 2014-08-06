package pages.Conferencia

import pages.FormPage
import rgms.member.Member

class ConferenciaCreatePage extends FormPage {
    static url = "conferencia/create"

    static at = {
        title ==~ /Criar Conferencia/
        booktitle != null
    }

    static content = {
        booktitle {
            $("input", id: "booktitle")
        }
        flashmessage {
            $("div", class: "message")
        }
    }

    def fillConferenciaDetails() {
        $("form").title = "A theory of Software Product Line Refinement"
        $("form").booktitle = "Theoretical Computer Science"
        $("form").pages = "20-100"
        $("form").create().click()
        // Could parametrize, obtaining data from class TestConferenciaDataAndOperations
    }

    def fillTitle() {
        $("form").title = "A theory of Software Product Line Refinement"
        $("form").create().click()
    }

    def selectHome() {
        $('a.home').click()
    }

    def fillAuthorName(String name)
    {
        $("form").addAuthor = name
    }

    def addAuthor()
    {
        $("#addAuthorButton").click()
    }

    def checkFirstAuthor()
    {
      String firstAuthor = $("#authors table td", 0).text()
      assert firstAuthor.equals("Administrador do sistema")
    }

    def checkLastAuthor(String authorName)
    {
        int lastPost = $("#authors table td").allElements().size() - 1
        String lastAuthor = $("#authors table td", lastPost).text()
        assert authorName.equals(lastAuthor)
    }

    def clickOn(String option)
    {
        if (option.equals("home"))
            selectHome()
        else if (option.equals("add author"))
            addAuthor()
        else if (option.equals("remove author"))
            removeAuthor()
    }

}
