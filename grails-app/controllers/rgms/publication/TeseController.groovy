package rgms.publication

class TeseController extends ThesisOrDissertationController {

    def grailsApplication
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def list() {
        listThesisOrDissertation("Tese", params)
    }

    def create() {
        createThesisOrDissertation("Tese", grailsApplication, params)
    }

    def save() {
        saveThesisOrDissertation("Tese", params)
    }
    
    def show() {
        showOrEdit("Tese", params.id)
    }

    def edit() {
        showOrEdit("Tese", params.id)
    }

    def update() {
        updateThesisOrDissertation("Tese", params)
    }

    def delete() {
        deleteThesisOrDissertation("Tese", params)
    }

}