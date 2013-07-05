package pages

import geb.Page

class MemberViewPage extends Page {
    static url = "member/show"

    static at = {
        title ==~ /Ver Member/
    }

    static content = {

    }


}
