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
        $("form").authorName = name
    }

    def checkFirstAuthor()
    {
      String firstAuthor = $("form").find("select#members option", value: "1").text()
      assert firstAuthor.equals("Administrador do sistema")
    }

    def checkLastAuthor(String authorName)
    {
        int listLen = $("form").members.collect().size()
        String lastPos = String.valueOf(listLen)

        String lastAuthor = $("form").find("select#members option", value: lastPos).text()
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
