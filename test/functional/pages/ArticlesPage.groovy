package pages

import geb.Page

class ArticlesPage extends Page {
    static url = "article/list"

    static at = {
        title ==~ /Journal List/
    }

    static content = {
    }

    def selectNewArticle() {
        $('a.create').click()
    }
}