package pages

import geb.Page

class MemberEditionPage extends Page {

    static at = {
        //title ==~ /Editar Member/
		GetPageTitle gp = new GetPageTitle()
		//def currentPeriodico = gp.msg("default.member.label")
		def currentPeriodico = "Member"
		def currentTitle = gp.msg("default.edit.label", [currentPeriodico])
		title ==~ currentTitle
    }

    static content = {
        memberform { $("form") }
        searchSubmitButton { memberform.find(".buttons.button.save")}
    }

    def editEnableUser(String userName){

        def member = TestDataAndOperations.findByUsername(userName)
        $("form").active = true
        $("form").enabled = true
        $("form").permissions = "*:*"
        $("form").twitterAccessToken = member.twitterAccessToken ?: ""
        $("form").twitterAccessSecret = member.twitterAccessSecret ?: ""
        $("input", type: "submit", class: "save").click()
    }

}