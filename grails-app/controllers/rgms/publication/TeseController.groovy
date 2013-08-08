package rgms.publication

import org.apache.shiro.SecurityUtils
import org.springframework.dao.DataIntegrityViolationException
import rgms.member.Member

import org.apache.shiro.SecurityUtils

import rgms.member.Member;
//import Tese;

class TeseController {

    def grailsApplication
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    public def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [teseInstanceList: Tese.list(params), teseInstanceTotal: Tese.count()]
    }

    def create() {
        Tese teseInstance = new Tese(params)
        //#if($publicationContext)
        def publcContextOn = grailsApplication.getConfig().getProperty("publicationContext")
        if(publcContextOn){
            if(SecurityUtils.subject?.principal != null){
                def user = PublicationController.addAuthor(teseInstance)
                if(!user.university.isEmpty()){
                    teseInstance.school = user.university
                }
            }
        }
        //#end
        [teseInstance: teseInstance]
    }

    def save() {
        def teseInstance = new Tese(params)
        PublicationController pb = new PublicationController()
        if (Tese.findByTitle(params.title)) {
            flash.message = message(code: 'tese.duplicatetitle.failure', args: [message(code: 'tese.label', default: 'Tese'), teseInstance.id])
            render(view: "create", model: [teseInstance: teseInstance])
            return
        }
        if (!pb.upload(teseInstance) || !teseInstance.save(flush: true)) {
            render(view: "create", model: [teseInstance: teseInstance])
            return
        }
        //#if($facebook)
        //def user = Member.findByUsername(SecurityUtils.subject?.principal)
        //pb.sendPostFacebook(user, teseInstance.toString())
        //#end
        flash.message = message(code: 'default.created.message', args: [message(code: 'tese.label', default: 'Tese'), teseInstance.id])
        redirect(controller: "tese", action: "show", id: teseInstance.id)
    }

    def show() {
        ShowOrEdit()
    }

    def edit() {
        ShowOrEdit()
    }

    def update() {
        def teseInstance = Tese.get(params.id)
        if (!teseInstance) {
            messageGenerator()
            return
        }
        if (params.version) {
            def version = params.version.toLong()
            if (teseInstance.version > version) {
                teseInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'tese.label', default: 'Tese')] as Object[],
                          "Another user has updated this Tese while you were editing")
                render(view: "edit", model: [teseInstance: teseInstance])
                return
            }
        }
        teseInstance.properties = params
        if (!teseInstance.save(flush: true)) {
            render(view: "edit", model: [teseInstance: teseInstance])
            return
        }
        flash.message = message(code: 'default.updated.message', args: [message(code: 'tese.label', default: 'Tese'), teseInstance.id])
        redirect(action: "show", id: teseInstance.id)
    }

    def delete() {
        def teseInstance = Tese.get(params.id)
        if (!teseInstance) {
            messageGenerator()
            return
        }
        try {
            teseInstance.delete(flush: true)
            messageGenerator()
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'tese.label', default: 'Tese'), params.id])
            redirect(action: "show", id: params.id)
        }
    }

    def ShowOrEdit(){   
        def teseInstance = Tese.get(params.id)
        if (!teseInstance) {
            messageGenerator()
            return
        }
        [teseInstance: teseInstance]
    }

    def messageGenerator()
    {
        flash.message = message(code: 'default.not.found.message', args: [message(code: 'tese.label', default: 'Tese'), params.id])
        redirect(action: "list")
    }
}