//#if($researchProject)
package rgms.researchProject

import org.apache.shiro.SecurityUtils
import org.springframework.dao.DataIntegrityViolationException
import rgms.authentication.User

class ResearchProjectController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [researchProjectInstanceList: ResearchProject.list(params), researchProjectInstanceTotal: ResearchProject.count()]
    }

    def create() {
        [researchProjectInstance: new ResearchProject(params)]
    }

    def myProjects() {
        User user = User.findByUsername(SecurityUtils.subject.principal);
        List<ResearchProject> projectsList = ResearchProject.findAllByResponsible(user.getAuthor().getName());
        render(view: '/researchProject/list', model: [researchProjectInstanceList: projectsList, researchProjectInstanceTotal: projectsList.size()]);
    }

    //#if($Filter_ResearchProject)
    def filter(String projectName) {
        List<ResearchProject> projectsList = ResearchProject.findAllByProjectName(projectName);
        render(view: '/researchProject/list', model: [researchProjectInstanceList: projectsList, researchProjectInstanceTotal: projectsList.size()]);
    }
    //#end

    def save() {
        def researchProjectInstance = new ResearchProject(params)
        saveInstance(researchProjectInstance,"create",'default.created.message')
    }

    def show(Long id) {
        _processResearchProject(id)
    }

    def edit(Long id) {
        _processResearchProject(id)
    }

    private def _processResearchProject(Long id){

        def researchProjectInstance = getResearchProjectInstance(id)

        // If it's not possible to find this instance of researchProject we can conclude the redirection returning and
        // exit this function
        if(!researchProjectInstance) return

        [researchProjectInstance: researchProjectInstance]
    }

    private def getResearchProjectInstance(Long id){
        def researchProjectInstance = ResearchProject.get(id)

        if (!researchProjectInstance) {
            showFlashMessage(id,"list",'default.not.found.message')
            return null
        }

        return researchProjectInstance
    }

    def update(Long id, Long version) {
        def researchProjectInstance = getResearchProjectInstance(id)

        // If it's not possible to find this instance of researchProject we can conclude the redirection returning and
        // exit this function
        if(!researchProjectInstance) return

        if (version != null) {
            if (researchProjectInstance.version > version) {
                researchProjectInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'researchProject.label', default: 'ResearchProject')] as Object[],
                        "Another user has updated this ResearchProject while you were editing")
                render(view: "edit", model: [researchProjectInstance: researchProjectInstance])
                return
            }
        }

        researchProjectInstance.properties = params

        saveInstance(researchProjectInstance,"edit",'default.updated.message')
    }

    private def void saveInstance(ResearchProject researchProjectInstance, String view, String code) {
        if (!researchProjectInstance.save(flush: true)) {
            render(view: view, model: [researchProjectInstance: researchProjectInstance])
            return
        }

        flash.message = message(code: code, args: [message(code: 'researchProject.label', default: 'ResearchProject'), researchProjectInstance.id])
        redirect(action: "show", id: researchProjectInstance.id)
    }

    def delete(Long id) {
        def researchProjectInstance = getResearchProjectInstance(id)

        // If it's not possible to find this instance of researchProject we can conclude the redirection returning and
        // exit this function
        if(!researchProjectInstance) return

        try {
            researchProjectInstance.delete(flush: true)
            showFlashMessage(id,"list",'default.deleted.message')
        }
        catch (DataIntegrityViolationException e) {
            showFlashMessage(id,"show",'default.not.deleted.message')
        }
    }

    def showFlashMessage(Long id, String action, String code){
        flash.message = message(code: code, args: [message(code: 'researchProject.label', default: 'ResearchProject'), id])
        if(action.equals("show"))
            redirect(action: action, id: id)
        else
            redirect(action: action)
    }
}
//#end