package pages

import geb.Page

class MemberListPage extends Page {
	static url = "member/list"

	static at = {
		title ==~ /Member Listagem/
	}

	static content = {
		
	}

    def selectMember(String s) {
        $('div').find('a', text: s).click()
    }
}
