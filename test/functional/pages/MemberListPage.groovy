package pages

import geb.Page

class MemberListPage extends Page {
    static url = "member/list"

    static at = {
        title ==~ /Member Listagem/

    }

    static content = {
    }

    def getMenuOption(String option) {
        $("div.nav a", text: option)
    }

    def selectMember(String member) {
        $("a", text: member).click()
    }

}
