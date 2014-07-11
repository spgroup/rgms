package rgms.researchProject.aspect

import rgms.researchProject.ResearchProject
import rgms.researchProject.ResearchProjectController

/**
 * Created by Vilmar Nepomuceno on 10/07/2014.
 */
class ResearchProjectControllerAspect {

    def mc = ResearchProjectController.metaClass
    def init() {
        mc.save = { ->

            def researchProjectInstance = new ResearchProject(params)

            if (!researchProjectInstance.save(flush: true)) {
                return
            }

            flash.message = message(code: 'default.created.message', args: [message(code: 'researchProject.label', default: 'ResearchProject'), researchProjectInstance.id])
            redirect(action: "show", id: researchProjectInstance.id)
        }
    }
}
