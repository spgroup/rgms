package rgms.news

import org.springframework.dao.DataIntegrityViolationException;

class NewsController {

    def index() { }
	
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
		catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'news.label', default: 'News'), params.id])
			redirect(action: "show", id: params.id)
		}
	}
	
	def save = {		
		 if (!grailsApplication.config.grails.mail.username) {
			 throw new RuntimeException(message(code: 'mail.plugin.not.configured', 'default' : 'Mail plugin not configured'))
		 }
		 
		 def newsInstance = new News(params)
		 def newsDB = News.findAllByDescriptionAndDate(newsInstance.description,newsInstance.date);
		 if(!newsDB?.empty)
		 {
			 flash.message = message(code: 'news.not.created.unicity.rule.message', args: [message(code: 'news.label', default: 'News'), params.id])
			 redirect(action: "show", id: params.id)
			 return
			
		 }
		 //verificar unicidade aqui
		 //flash.message 
		 
		 if (!newsInstance.save(flush: true)) {
			 render(view: "create", model: [newsInstance: newsInstance])
			 return
		 }
	 }
}


