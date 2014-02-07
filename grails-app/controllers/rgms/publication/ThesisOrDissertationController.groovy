package rgms.publication

import org.apache.shiro.SecurityUtils
import org.springframework.dao.DataIntegrityViolationException
import rgms.member.Member

class ThesisOrDissertationController {

    public def index() {
        redirect(action: "list", params: params)
    }

    def listThesisOrDissertation(String thesisOrDissertation, params) {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        if (thesisOrDissertation == "Tese") {
            //noinspection GroovyAssignabilityCheck
            [teseInstanceList: Tese.list(params), teseInstanceTotal: Tese.count()]
        } else if (thesisOrDissertation == "Dissertacao") {
            //noinspection GroovyAssignabilityCheck
            [dissertacaoInstanceList: Dissertacao.list(params), dissertacaoInstanceTotal: Dissertacao.count()]
        }
    }

    def createThesisOrDissertation(String thesisOrDissertation, params) {
        //noinspection GroovyAssignabilityCheck
        def instance = getClassByName(thesisOrDissertation).newInstance(params)
        //#if($contextualInformation)
        def user = PublicationController.addAuthor(instance as Publication)
        if (user && !user.university.isEmpty()){
            instance.school = user.university
        }
        //#end
        returnInstance(thesisOrDissertation, instance)
    }

    def saveThesisOrDissertation(String thesisOrDissertation, params) {
        //noinspection GroovyAssignabilityCheck
        def instance = getClassByName(thesisOrDissertation).newInstance(params)
        PublicationController pb = new PublicationController()
        def duplicated
        if (thesisOrDissertation == "Tese") {
            //noinspection GroovyAssignabilityCheck
            duplicated = Tese.findByTitle(params.title)
        } else if (thesisOrDissertation == "Dissertacao") {
            //noinspection GroovyAssignabilityCheck
            duplicated = Dissertacao.findByTitle(params.title)
        }
        if (duplicated) {
            messageGenerator(thesisOrDissertation, 'tese.duplicatetitle.failure', instance.id)
            render(view: "create", model: [instance: instance])
            return
        }
        if (!pb.upload(instance as Publication) || !instance.save(flush: true)) {
            render(view: "create", model: [instance: instance])
            return
        }
        //#if($facebook)
        //def user = Member.findByUsername(SecurityUtils.subject?.principal)
        //pb.sendPostFacebook(user, teseInstance.toString())
        //#end
        messageGenerator(thesisOrDissertation, 'default.created.message', instance.id)
        redirect(controller: thesisOrDissertation, action: "show", id: instance.id)
    }

    def showOrEdit(String thesisOrDissertation, id){   
        def instance = getClassByName(thesisOrDissertation).get(id)
        if (!instance) {
            messageGenerator(thesisOrDissertation, 'default.not.found.message', id)
            return
        }
        returnInstance(thesisOrDissertation, instance)
    }

    def updateThesisOrDissertation(String thesisOrDissertation, params) {
        def instance = getThesisOrDissertationControllerInstance(thesisOrDissertation,params)
        if(instance == null) return
        if (params.version) {
            def version = params.version.toLong()
            if (instance.version > version) {
                //noinspection GroovyUnusedAssignment
                def lower = thesisOrDissertation.toLowerCase()
                //noinspection InvalidI18nProperty
                instance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: '${lower}.label', default: thesisOrDissertation)] as Object[],
                          messageGenerator(thesisOrDissertation, "default.optimistic.locking.failure", params.id))
                render(view: "edit", model: [instance: instance])
                return
            }
        }
        instance.properties = params
        if (!instance.save(flush: true)) {
            if (thesisOrDissertation == "Tese") {
                render(view: "edit", model: [teseInstance: instance])
            } else if (thesisOrDissertation == "Dissertacao") {
                render(view: "edit", model: [dissertacaoInstance: instance])
            }
            return
        }
        messageGenerator(thesisOrDissertation, 'default.updated.message', instance.id)
        redirect(action: "show", id: instance.id)
    }

    def deleteThesisOrDissertation(String thesisOrDissertation, params) {
       def instance = getThesisOrDissertationControllerInstance(thesisOrDissertation, params)
       if(instance == null) return
        try {
            instance.removeFromPublications()
            instance.delete(flush: true)
            messageGenerator(thesisOrDissertation, 'default.deleted.message', instance.id)
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException ignored) {
            messageGenerator(thesisOrDissertation, 'default.not.deleted.message', instance.id)
            redirect(action: "show", id: params.id)
        }
    }

    def getThesisOrDissertationControllerInstance(String thesisOrDissertation, params) {
        def instance = getClassByName(thesisOrDissertation).get(params.id)
        if (!instance) {
            messageGenerator(thesisOrDissertation, 'default.not.found.message', params.id)
            redirect(action: "list")
        }
        return instance
    }

    def getClassByName(String thesisOrDissertation) {
        Thread.currentThread().contextClassLoader.loadClass("rgms.publication.${thesisOrDissertation}")
    }

    def returnInstance(String thesisOrDissertation, instance) {
        if (thesisOrDissertation == "Tese") {
            [teseInstance: instance]
        } else if (thesisOrDissertation == "Dissertacao") {
            [dissertacaoInstance: instance]
        }
    }

    def messageGenerator(String thesisOrDissertation, String code, id) {
        def lower = thesisOrDissertation.toLowerCase()
        flash.message = message(code: code, args: [message(code: "${lower}.label",
                                default: thesisOrDissertation), id])
    }

 }