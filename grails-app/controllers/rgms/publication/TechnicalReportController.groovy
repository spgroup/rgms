package rgms.publication

import org.apache.shiro.SecurityUtils
import org.springframework.dao.DataIntegrityViolationException
import rgms.GoogleScholarService

class TechnicalReportController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [technicalReportInstanceList: TechnicalReport.list(params), technicalReportInstanceTotal: TechnicalReport.count()]
    }

    //#if($Report)
    //
    def report() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def GoogleScholarService gss = new GoogleScholarService()
        gss.findCitations(TechnicalReport.list(params))
        [technicalReportInstanceList: TechnicalReport.list(params), technicalReportInstanceTotal: TechnicalReport.count()]
    }
    //#end

    def create() {
        def technicalReportInstance = new TechnicalReport(params)
        //#if($publicationContext)
        def publcContextOn = grailsApplication.getConfig().getProperty("publicationContext");
        if (publcContextOn) {
            if (SecurityUtils.subject?.principal != null) {
                def user = PublicationController.addAuthor(technicalReportInstance)
                if (!user.university.isEmpty()) {
                    technicalReportInstance.institution = user.university
                }
            }
        }
        //#end
        [technicalReportInstance: technicalReportInstance]
    }

    def save() {
        def technicalReportInstance = new TechnicalReport(params)
        if (!PublicationController.newUpload(technicalReportInstance, flash, request)) {
            handleSavingError(technicalReportInstance, 'technicalReport.filesaving.failure')
            return
        }
        if (!technicalReportInstance.save(flush: true)) {
            handleSavingError(technicalReportInstance, 'technicalReport.saving.failure')
            return
        }
        //#if($facebook)
        //def user = Member.findByUsername(SecurityUtils.subject?.principal)
        //PublicationController.sendPostFacebook(user, technicalReportInstance.toString())
        //#end
        flash.message = message(code: 'default.created.message', args: [message(code: 'technicalReport.label', default: 'TechnicalReport'), technicalReportInstance.id])
        redirect(action: "show", id: technicalReportInstance.id)
    }

    def handleSavingError(TechnicalReport trInstance, String message) {
        trInstance.discardMembers()
        flash.message = message
        render(view: "create", model: [technicalReportInstance: trInstance])
    }

    def show(Long id) {
        def technicalReportInstance = TechnicalReport.get(id)
        checkTechnicalReportInstance(id, technicalReportInstance)
    }

    def edit(Long id) {
        def technicalReportInstance = TechnicalReport.get(id)
        checkTechnicalReportInstance(id, technicalReportInstance)
    }

    def update(Long id, Long version) {
        def technicalReportInstance = TechnicalReport.get(id)
        if(!checkTechnicalReportInstance(id, technicalReportInstance))
            return

        if (version != null) {
            if (technicalReportInstance.version > version) {
                technicalReportInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'technicalReport.label', default: 'TechnicalReport')] as Object[],
                        "Another user has updated this TechnicalReport while you were editing")
                render(view: "edit", model: [technicalReportInstance: technicalReportInstance])
                return
            }
        }

        technicalReportInstance.properties = params

        if (!technicalReportInstance.save(flush: true)) {
            render(view: "edit", model: [technicalReportInstance: technicalReportInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'technicalReport.label', default: 'TechnicalReport'), technicalReportInstance.id])
        redirect(action: "show", id: technicalReportInstance.id)
    }

    def delete(Long id) {
        def technicalReportInstance = TechnicalReport.get(id)
        if(!checkTechnicalReportInstance(id, technicalReportInstance))
            return

        try {
            technicalReportInstance.members.each {
                it.publications.remove(technicalReportInstance)
            }
            technicalReportInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'technicalReport.label', default: 'TechnicalReport'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'technicalReport.label', default: 'TechnicalReport'), id])
            redirect(action: "show", id: id)
        }
    }

    def checkTechnicalReportInstance(Long id, TechnicalReport technicalReportInstance) {
        if (!technicalReportInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'technicalReport.label', default: 'TechnicalReport'), id])
            redirect(action: "list")
            return
        }

        [technicalReportInstance: technicalReportInstance]
    }

}
