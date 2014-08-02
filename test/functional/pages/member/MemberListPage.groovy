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

    def clickOnUsernameOrder(){
        $('a[href*="username"]').click()
    }
    def clickOnName(String id){
        $('a[href*="show/'+id+'"]').click()
    }

    def checkLogoutLink(){
        return $('a[href*="signOut"]').size()!=1
    }

    def returnNumberOfNewMembers(){

        return $('div.list:first table tbody tr').size()
    }
    def getMenuOption(String option) {
        $("div.nav a", text: option)
    }

    def selectMember(String member) {
        $("a", text: member).click()
    }

    //#if ($memberListAndPageImprovement)
    def orderMemberByName() {
        $("th", text: "Name").click()
    }


    //#end

}
