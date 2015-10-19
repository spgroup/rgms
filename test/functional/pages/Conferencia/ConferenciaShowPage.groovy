package pages.Conferencia


class ConferenciaShowPage {

    static url = "conferencia/show"

    static at = {
        title ==~ "Ver Conferencia"
    }

    def edit(){
        $("a.edit").click()
    }

    def remove(){
        $("a.remove").click()
    }

    def clickOn(String option){
        if (option.equals("edit"))
            edit()
        else if (option.equals("remove"))
            remove()
    }
}
