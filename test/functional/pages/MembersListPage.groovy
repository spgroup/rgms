package pages


import geb.Page
import geb.navigator.Navigator

class MembersListPage extends Page {
    static url = "member/list"

    static at = {
        title ==~ /Member Listagem/

    }

    static content = {
    }

    def getMenuOption(String option){
        $("div.nav a", text: option)
    }

}
