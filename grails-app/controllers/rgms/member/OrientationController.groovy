//#if($Orientation)
package rgms.member

class OrientationController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [orientationInstanceList: Orientation.list(params), orientationInstanceTotal: Orientation.count()]
    }

    def create() {
        [orientationInstance: new Orientation(params)]
    }

    def save() {
        def orientationInstance = new Orientation(params)

        if(!compareOrientationWithRender(orientationInstance, "create")) {
            return false
        }
        if (!orientationInstance.save(flush: true)) {
            render(view: "create", model: [orientationInstance: orientationInstance])
            return false
        }

        showFlashMessage(orientationInstance.id, "show", 'default.created.message')

    }

    def compareOrientationWithRender(orientationInstance, tipoRender) {
        if(orientationInstance.orientador.name.equalsIgnoreCase(orientationInstance.orientando)) {
            render(view: tipoRender, model: [orientationInstance: orientationInstance])
            //noinspection InvalidI18nProperty
            flash.message = message(code: 'orientation.same.members', args: [message(code: 'orientation.label', default: 'Orientation'), orientationInstance.id])
            return false
        }
        return true

    }

    def show = {
        _processOrientation()
    }

    def edit = {
        _processOrientation()
    }

    def showFlashMessage(Long id, String action, String code){
        //noinspection InvalidI18nProperty
        flash.message = message(code: code, args: [message(code: 'orientation.label', default: 'Orientation'), id])
        redirect(action: action, id: id)
    }

    def _processOrientation()
    {
        //noinspection GroovyAssignabilityCheck
        def orientationInstance = Orientation.get(params.id)
        if (!orientationInstance) {
            showFlashMessage(null, "list",'default.not.found.message')
            return
        }

        [orientationInstance: orientationInstance]
    }

    def isOrientationInstance(Long id){

        def orientationInstance = Orientation.get(id)

        if (!orientationInstance) {
            showFlashMessage(id, "list",'default.not.found.message')
            return null
        }

        return orientationInstance
    }

    def update(Long id, Long version) {

        def orientationInstance = isOrientationInstance(id)

        if(orientationInstance != null){
            if(!checkOrientationVersion(orientationInstance, version)) {return }
            orientationInstance.properties = params
            if(!checkOrientationOrientando(orientationInstance)){return }

            if (!orientationInstance.save(flush: true)) {
                render(view: "edit", model: [orientationInstance: orientationInstance])
                return
            }

            showFlashMessage(orientationInstance.id, "show",'default.updated.message')
        }
    }

    def checkOrientationOrientando(Orientation orientationInstance){

        if(!compareOrientationWithRender(orientationInstance, "edit")) {
            return false
        }
        return true
    }

    def checkOrientationVersion(Orientation orientationInstance, Long version){

        if (version != null) {
            if (orientationInstance.version > version) {
                //noinspection InvalidI18nProperty
                orientationInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'orientation.label', default: 'Orientation')] as Object[],
                        'default.orientation.checkVersion.message')
                render(view: "edit", model: [orientationInstance: orientationInstance])
                return false
            }
        }
        return true
    }

    def delete(Long id) {
        def orientationInstance = isOrientationInstance(id)

        if(orientationInstance != null){
            //noinspection GroovyUnusedCatchParameter
            try {
                orientationInstance.delete(flush: true)
                showFlashMessage(id,"list",'default.deleted.message')
            }

            catch (DataIntegrityViolationException) {
                showFlashMessage(id, "show", 'default.not.deleted.message')

            }
        }
    }
}

//#end
