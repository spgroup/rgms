package rgms.news

import org.springframework.dao.DataIntegrityViolationException

class NewsController {

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 20, 100)
        [newsInstanceList : News.list(params), newsInstanceTotal: News.count()]
    }

    def show(Long id) {
        def newsInstance = News.get(id)
        newsInstanceRedirectIfItsNull(id, newsInstance)
    }

    def create = {
        def news = new News(params)
        [newsInstance: news]
    }

    def flashMessage(String code, paramsID, String action) {
        flash.message = message(code: code, args: [message(code: 'news.label', default: 'News'), paramsID])

        if(!action.equals('')){
            if(action.equals("show")) {
                redirect(action: "show", id: params.id)
            } else {
                redirect(action: action)
            }
        }
    }

    def delete = {
        //noinspection GroovyAssignabilityCheck
        def newsInstance = News.get(params.id)
        if (!newsInstance) {
            flashMessage('default.not.found.message', params.id, 'list')
            return
        }

        try {
            newsInstance.delete(flush: true)
            flashMessage('default.deleted.message', params.id, 'list')
        }
        catch (DataIntegrityViolationException ignored) {
            flashMessage('default.not.deleted.message', params.id, 'show')
        }
    }

    def save = {
        if (!grailsApplication.config.grails.mail.username) {
            throw new RuntimeException(message(code: 'mail.plugin.not.configured', 'default': 'Mail plugin not configured') as Throwable)
        }

        def newsInstance = new News(params)

        if(checkExisting(newsInstance)) {
            flashMessage('news.not.created.unicity.rule.message', params.id, '')
            redirect(action: "list", id: params.id)
            return
        }

        createOrEditNews(newsInstance, "create")

        return
    }

    def createOrEditNews(News newsInstance, String action) {
        if (!newsInstance.save(flush: true)) {
            render(view: action, model: [newsInstance: newsInstance])
            return
        }

        if(action.equals("create")){
            flashMessage('default.created.message', newsInstance.id, '')
        } else {
            flashMessage('default.updated.message', newsInstance.id, '')
        }

        redirect(action: "show", id: newsInstance.id)

        return
    }

    def saveFromTwitter(){
        def newsInstance = new News(params)

        if(checkExisting(newsInstance)){
            return
        }

        if (!newsInstance.save(flush: true)) {
            render(view: "create", model: [newsInstance: newsInstance])
        }
    }

    def update(Long id){
        def newsInstance = News.get(id)
        if(!newsInstanceRedirectIfItsNull(id,newsInstance)){
            return
        }

        newsInstance.properties = params

        createOrEditNews(newsInstance, "edit")

        return
    }

    private def newsInstanceRedirectIfItsNull(Long id, News newsInstance){
        if(!newsInstance){
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'news.label', default: 'News'), id])
            redirect(action: "list")
            return
        }
        [newsInstance: newsInstance]
    }

    private static boolean checkExisting(News newsInstance){
        def newsDB = News.findByDescriptionAndDateAndResearchGroup(newsInstance.description, newsInstance.date, newsInstance.researchGroup);
        if (newsDB) {
            return true
        }
        else return false
    }

}


