package pages.member

import geb.Page
import pages.GetPageTitle

class MemberListPage extends Page {
    static url = "member/list"

    static at = {
        //  title ==~ /Member Listagem/
        GetPageTitle gp = new GetPageTitle()
        def memberLabel = gp.msg("member.label")
        def listLabel = gp.msg("default.list.label", [memberLabel])
        title ==~ listLabel

    }

    static content = {
    }

    def getMenuOption(String option) {
        $("div.nav a", text: option)
    }

    def selectMember(String member) {
        $("a", text: member).click()
    }

    def selectNthMember(String number){
        $('a', text: number).click()
    }

}
