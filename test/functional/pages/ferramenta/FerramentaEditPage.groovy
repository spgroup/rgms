package pages.ferramenta

import geb.Page

class FerramentaEditPage extends Page {
    static url = "Ferramenta/edit"

    static at = {
        title ==~ /Editar Ferramenta/
    }

    static content = {
    }

    def editWebsite(String website) {
        $("form").website = website
        save()
    }

    def editTitle(String title){
        $("form").title = title
        save()
    }

    def save(){
        $("form").save().click()
    }
}
