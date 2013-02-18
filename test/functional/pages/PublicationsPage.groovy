package pages

import geb.Page

class PublicationsPage extends Page {
    static url = "auth/signIn"

    static at = {
        title ==~ /RGMS/
    }

    static content = {
    }

    def select(String s) {
        $('div', id: 'status').find('a', text: s).click()
    }
}
