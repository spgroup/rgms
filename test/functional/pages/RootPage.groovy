package pages

import geb.Page
import geb.navigator.Navigator

class RootPage extends Page {
    static url = "/rgms"

    static at = {
        title ==~ /Login/ || title ==~ /RGMS/
    }

    static content = {
    }

}
