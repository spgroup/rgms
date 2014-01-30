package pages.Util

import geb.Page


/**
 * Created by rafael on 1/29/14.
 */
class CommonPages {
    def static selectView(page, title) {
        page.getRow()
        def showLink = page.getRow().find('td').find([text:title])
        showLink.click()
    }
}
