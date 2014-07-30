package pages.Conferencia

class ConferenciaEditPage {

    static url = "conferencia/edit"

    static at = {
        title ==~ "Editar Conferencia"
    }

    def removeAuthor() {
        $("form").removeAuthor() //TODO
    }

    def addAuthor(){
        $("form").addAuthor() //TODO
    }

    def clickOn(String option){
        if (option.equals("add author"))
            addAuthor()
        else if (option.equals("remove author"))
            removeAuthor()
    }
}
