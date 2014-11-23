package pages.member

import geb.Page
import pages.GetPageTitle

class MemberEditionPage extends Page {

    static at = {
        //title ==~ /Editar Member/
		GetPageTitle gp = new GetPageTitle()
        def memberLabel = gp.msg("member.label")
		def currentTitle = gp.msg("default.edit.label", [memberLabel])
		title ==~ currentTitle
    }

    static content = {
        memberform { $("form") }
        searchSubmitButton { memberform.find(".buttons.button.save")}
    }

    def editMemberInformation(String type, String value){
        if(type == 'name')
            $('form').name = value
        $('input', type: 'submit', class: 'save').click()
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