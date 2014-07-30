package pages.member

import geb.Page

class MemberViewPage extends Page {
    static url = "member/show"

    static at = {
        title ==~ /Ver Member/
    }

    static content = {

    }

    def boolean compareMemberName(String name){
       return $("link").getAttribute('text') ==~ /${name}/
    }

}
