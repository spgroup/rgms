package rgms.publication

import org.springframework.web.multipart.MultipartHttpServletRequest
import javax.servlet.http.HttpServletRequest
import rgms.XMLService

class DissertacaoController extends ThesisOrDissertationController {


   
    def grailsApplication
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def list() {
        listThesisOrDissertation("Dissertacao", params)
    }

    def create() {
        createThesisOrDissertation("Dissertacao", params)
    }

    def save() {
        saveThesisOrDissertation("Dissertacao", params)
    }
    def getDissertacaoInstance(def id){
        //noinspection GroovyAssignabilityCheck
        def dissertacaoInstance = Dissertacao.get(id)
        if (!dissertacaoInstance) {
            flash.message = messageGenerator('','default.not.found.message', id)
            redirect(action: "list")
            return
        }

        [dissertacaoInstance: dissertacaoInstance]
    }

    def show() {
        showOrEdit("Dissertacao", params.id)
    }
    
    def edit() {
        showOrEdit("Dissertacao", params.id)
}

    def update() {
        updateThesisOrDissertation("Dissertacao", params)
    }

    def delete() {
        deleteThesisOrDissertation("Dissertacao", params)
    }
}
