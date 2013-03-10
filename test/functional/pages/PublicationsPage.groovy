package pages

import geb.Page

class PublicationsPage extends Page {
    static url = ""

    static at = {
        title ==~ /RGMS/
    }

    static content = {
    }

    def getLink(String linkName) {
        $('div#status a', text: linkName)
    }
}
