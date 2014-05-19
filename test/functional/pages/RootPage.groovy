package pages

import geb.Page

class RootPage extends Page {
    def loginPageTitle = /${(new GetPageTitle()).getMessageServerLocale("user.login.title")}/
    def mainMenuPageTitle = /${(new GetPageTitle()).getMessageServerLocale("mainMenu.title")}/
    static url = "/rgms"

    static at = {
        title ==~ loginPageTitle || title ==~ mainMenuPageTitle
    }

    static content = {
    }

}
