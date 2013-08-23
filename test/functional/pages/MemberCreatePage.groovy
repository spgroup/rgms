package pages

import geb.Page

class MemberCreatePage extends Page {
    static url = "member/create"

    static at = {
        title ==~ /Criar Member/
    }

    static content = {
        mensagem { $("div", 4, class: "errors") }
    }

    def fillMemberDetails(String name, String username, String email, String university, String additionalInfo) {
        $("form").phone = "81 2126 8430"
        $("form").website = "http://www.cin.ufpe.br"
        $("form").city = "Recife"
        $("form").country = "Brasil"
        $("form").active = true
        $("form").status = "Graduate Student"
        $("form").enabled = true
        $("form").additionalInfo = additionalInfo
        fillSomeMemberDetails(name, username, email, university)
    }

    def fillSomeMemberDetails(String name, String username, String email, String university) {
        $("form").name = name
        $("form").username = username
        $("form").email = email
        $("form").university = university
        $("input", id: "create").click()
    }

    def compareMemberUniversity(String university){
        $("form").university ==~ /${university}/
    }

    def compareMemberCity(String city){
        $("form").city ==~ /${city}/
    }
}

