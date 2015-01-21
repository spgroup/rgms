package rgms.publication

class TeseController extends ThesisOrDissertationController {

    def grailsApplication
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def list() {
        listThesisOrDissertation("Tese", params)
    }

    def create() {
        createThesisOrDissertation("Tese", params)
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
    
    def search = {
        def teseList = []
        if(params.title){
            def teses = Tese.findAllByTitle(params.title)
            for (i in teses) {
                    teseList.add([tese: i])
            }
        }

        [teseInstanceList: teseList, teseInstanceTotal: Tese.count()]
    }

}
