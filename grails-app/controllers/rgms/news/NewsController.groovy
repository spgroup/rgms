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

    def delete = {
        def newsInstance = News.get(params.id)
        if (!newsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'news.label', default: 'News'), params.id])
            redirect(action: "list")
            return
        }

        try {
            newsInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'news.label', default: 'News'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException ignored) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'news.label', default: 'News'), params.id])
            redirect(action: "show", id: params.id)
        }
    }

    def save = {
        if (!grailsApplication.config.grails.mail.username) {
            throw new RuntimeException(message(code: 'mail.plugin.not.configured', 'default': 'Mail plugin not configured') as Throwable)
        }

        def newsInstance = new News(params)
        //def newsDB = News.findByDescriptionAndDateAndResearchGroup(newsInstance.description, newsInstance.date, newsInstance.researchGroup);
        //if (newsDB) {
            //flash.message = message(code: 'news.not.created.unicity.rule.message', args: [message(code: 'news.label', default: 'News'), params.id])
            //redirect(action: "show", id: params.id)
            //return
        //}
        //verificar unicidade aqui
        //flash.message

        if(checkExisting(newsInstance)) {
            flash.message = message(code: 'news.not.created.unicity.rule.message', args: [message(code: 'news.label', default: 'News'), params.id])
            redirect(action: "list", id: params.id)
            return
        }

        if (!newsInstance.save(flush: true)) {
            render(view: "create", model: [newsInstance: newsInstance])
            return
        }

        flash.message = message(code: 'default.created.message',args: [message(code: 'news.label', default: 'News'),newsInstance.id])
        redirect(action: "show", id: newsInstance.id)
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

    def edit(Long id) {
        def newsInstance = News.get(id)
        newsInstanceRedirectIfItsNull(id, newsInstance)
    }

    def update(Long id){
        def newsInstance = News.get(id)
        if(!newsInstanceRedirectIfItsNull(id,newsInstance)){
            return
        }

        newsInstance.properties = params

        if(!newsInstance.save(flush:true)){
           render(view: "edit", model: [newsInstance: newsInstance])
           return
        }

        flash.message = message(code: 'default.updated.message',args: [message(code: 'news.label', default: 'News'),newsInstance.id])
        redirect(action: "show", id: newsInstance.id)
    }

    private def newsInstanceRedirectIfItsNull(Long id, News newsInstance){
        if(!newsInstance){
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'news.label', default: 'News'), id])
            redirect(action: "list")
            return
        }
        [newsInstance: newsInstance]
    }

    private boolean checkExisting(News newsInstance){
        def newsDB = News.findByDescriptionAndDateAndResearchGroup(newsInstance.description, newsInstance.date, newsInstance.researchGroup);
        if (newsDB) {
            return true
        }
        else return false
    }

}


