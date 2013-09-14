//#if($Orientation)
package rgms.member

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile
import rgms.XMLService
import rgms.authentication.User

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

        if(orientationInstance.orientador.name .equalsIgnoreCase(orientationInstance.orientando))
        {
            render(view: "create", model: [orientationInstance: orientationInstance])
            flash.message = message(code: 'orientation.same.members', args: [message(code: 'orientation.label', default: 'Orientation'), orientationInstance.id])
            return
        }
        if (!orientationInstance.save(flush: true)) {
            render(view: "create", model: [orientationInstance: orientationInstance])
            return
        }

        showFlashMessage(orientationInstance.id, "show", 'default.created.message')

    }

    def show = {
        _processOrientation()
    }

    def edit = {
        _processOrientation()
    }

    def showFlashMessage(Long id, String action, String code){
        flash.message = message(code: code, args: [message(code: 'orientation.label', default: 'Orientation'), id])
        redirect(action: action, id: id)
    }

    def _processOrientation()
    {
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

        if(orientationInstance.orientador.name.equalsIgnoreCase(orientationInstance.orientando)) {
            render(view: "edit", model: [orientationInstance: orientationInstance])
            flash.message = message(code: 'orientation.same.members', args: [message(code: 'orientation.label', default: 'Orientation'), orientationInstance.id])
            return false
        }

        return true
    }

    def checkOrientationVersion(Orientation orientationInstance, Long version){

        if (version != null) {
            if (orientationInstance.version > version) {
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
