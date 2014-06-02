//#if($Article)
package rgms.publication

import grails.converters.JSON
import org.apache.commons.collections.set.ListOrderedSet
import org.apache.shiro.SecurityUtils
import org.springframework.dao.DataIntegrityViolationException
import rgms.GoogleScholarService
import rgms.authentication.User

class PeriodicoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST", share: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [periodicoInstanceList: Periodico.list(params), periodicoInstanceTotal: Periodico.count()]
    }

    //#if($Report)
    //
    def report() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def GoogleScholarService gss = new GoogleScholarService()
        gss.findCitations(Periodico.list(params))
        [periodicoInstanceList: Periodico.list(params), periodicoInstanceTotal: Periodico.count()]
    }
    //#end

    def create() {
        def periodicoInstance = new Periodico(params)
        //#if($contextualInformation)
        PublicationController.addAuthor(periodicoInstance)
        //#end
        [periodicoInstance: periodicoInstance]
    }

    def save() {
        //def pb = new PublicationController()
        def periodicoInstance = new Periodico(params)

        if (!isValidPeriodico(periodicoInstance)) {
            return
        }

        periodicoInstance = PublicationController.extractAuthors(periodicoInstance)

        if (!periodicoInstance.save(flush: true)) {
            handleSavingError(periodicoInstance, 'periodico.saving.failure')
            return
        }

        //#if($facebook)
        // def user = User.findByUsername(SecurityUtils.subject?.principal)
        // Member author = user?.author
        // PublicationController.sendPostFacebook(author, periodicoInstance.toString())
        //#end

        flash.message = message(code: 'default.created.message', args: [message(code: 'periodico.label', default: 'Periodico'), periodicoInstance.id])
        redirect(action: "show", id: periodicoInstance.id)
    }

    def isValidPeriodico(Periodico periodicoInstance) {

        if (Periodico.findByTitle(params.title)) {
            handleSavingError(periodicoInstance, 'periodico.duplicatetitle.failure')
            return false
        }
        if (!PublicationController.newUpload(periodicoInstance, flash, request)) { // (!pb.upload(periodicoInstance)) {
            // com a segunda opção, o sistema se perde e vai para publication controller no teste Add a new article twitting it
            handleSavingError(periodicoInstance, 'periodico.filesaving.failure')
            return false
        }

        return true

    }

    def handleSavingError(Periodico periodicoInstance, String message) {
        periodicoInstance.discardMembers()
        flash.message = message
        render(view: "create", model: [periodicoInstance: periodicoInstance])
    }

    def show() {
        accessArticle()
    }

    def edit() {
        accessArticle()
    }


    def accessArticle() {
        def periodicoInstance = Periodico.get(params.id)
        if (!periodicoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'periodico.label', default: 'Periodico'), params.id])
            redirect(action: "list")
            return
        }
        [periodicoInstance: periodicoInstance]
    }

    def update() {
        def periodicoInstance = Periodico.get(params.id)
        if (!periodicoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'periodico.label', default: 'Periodico'), params.id])
            redirect(action: "list")
            return
        }

        if (!checkPeriodicoVersion(periodicoInstance)) {
            return
        }

        periodicoInstance.properties = params

        if (!periodicoInstance.save(flush: true)) {
            upload(periodicoInstance)
            render(view: "edit", model: [periodicoInstance: periodicoInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'periodico.label', default: 'Periodico'), periodicoInstance.id])
        redirect(action: "show", id: periodicoInstance.id)
    }

    def checkPeriodicoVersion(Periodico periodicoInstance) {

        if (params.version) {
            def version = params.version.toLong()
            if (periodicoInstance.version > version) {
                periodicoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'periodico.label', default: 'Periodico')] as Object[],
                        'default.article.checkVersion.message')
                render(view: "edit", model: [periodicoInstance: periodicoInstance])
                return false
            }
        }

        return true

    }

    def delete() {
        def periodicoInstance = Periodico.get(params.id)
        if (!periodicoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'periodico.label', default: 'Periodico'), params.id])
            redirect(action: "list")
            return
        }

        deletePeriodico(periodicoInstance)
    }

    def deletePeriodico(Periodico periodicoInstance) {

        try {
            periodicoInstance.removeFromPublications()
            periodicoInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'periodico.label', default: 'Periodico'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'periodico.label', default: 'Periodico'), params.id])
            redirect(action: "show", id: params.id)
        }

    }
    //#if( $Facebook )
    def share() {
        def periodicoInstance = Periodico.get(params.id)
        def member = User.findByUsername(SecurityUtils.subject?.principal).author
        if (!member.getFacebook_id()?.equals(""))
            PublicationController.sendPostFacebook(member, periodicoInstance.toString())
        redirect(action: "show", id: params.id)
    }
    //#end

    //#if( $contextualInformation )
    def ajaxJournalFinder = {
        def periodicalsFound = Periodico.withCriteria {
            ilike 'journal', params.term + '%'
        }
        Set journalsFound = new ListOrderedSet()
        periodicalsFound?.each { journalsFound.add(it.journal) }
        render(journalsFound as JSON)
    }
    //#end
}
//#end
