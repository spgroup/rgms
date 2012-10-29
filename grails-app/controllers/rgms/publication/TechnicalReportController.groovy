package rgms.publication

import org.springframework.dao.DataIntegrityViolationException


class TechnicalReportController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [technicalReportInstanceList: TechnicalReport.list(params), technicalReportInstanceTotal: TechnicalReport.count()]
    }

    def create() {
        [technicalReportInstance: new TechnicalReport(params)]
    }

    def save() {
        def technicalReportInstance = new TechnicalReport(params)
		PublicationController pb = new PublicationController()
		if (!pb.upload(technicalReportInstance) || !technicalReportInstance.save(flush: true)) {
            render(view: "create", model: [technicalReportInstance: technicalReportInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'technicalReport.label', default: 'TechnicalReport'), technicalReportInstance.id])
        redirect(action: "show", id: technicalReportInstance.id)
    }

    def show(Long id) {
        def technicalReportInstance = TechnicalReport.get(id)
        if (!technicalReportInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'technicalReport.label', default: 'TechnicalReport'), id])
            redirect(action: "list")
            return
        }

        [technicalReportInstance: technicalReportInstance]
    }

    def edit(Long id) {
        def technicalReportInstance = TechnicalReport.get(id)
        if (!technicalReportInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'technicalReport.label', default: 'TechnicalReport'), id])
            redirect(action: "list")
            return
        }

        [technicalReportInstance: technicalReportInstance]
    }

    def update(Long id, Long version) {
        def technicalReportInstance = TechnicalReport.get(id)
        if (!technicalReportInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'technicalReport.label', default: 'TechnicalReport'), id])
            redirect(action: "list")
            return
        }

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
        if (!technicalReportInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'technicalReport.label', default: 'TechnicalReport'), id])
            redirect(action: "list")
            return
        }

        try {
            technicalReportInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'technicalReport.label', default: 'TechnicalReport'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'technicalReport.label', default: 'TechnicalReport'), id])
            redirect(action: "show", id: id)
        }
    }
}
