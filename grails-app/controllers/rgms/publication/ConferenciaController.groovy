package rgms.publication

//#if($XMLUpload && $Conferencia)
import rgms.XMLService
//#end
import org.apache.shiro.SecurityUtils
import org.springframework.web.multipart.MultipartHttpServletRequest

class ConferenciaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    AuxiliarController aux = new AuxiliarController()

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [conferenciaInstanceList: Conferencia.list(params), conferenciaInstanceTotal: Conferencia.count()]
    }

    def create() {
        def conferenciaInstance = new Conferencia(params)
        //#if($contextualInformation)
        PublicationController.addAuthor(conferenciaInstance)
        //#end
        [conferenciaInstance: conferenciaInstance]
    }

    private alertMessage(String typeMessage, Conferencia conferenciaInstance){
        flash.message = message(code: 'default.'+ typeMessage +'.message', args: [message(code: 'conferencia.label', default: 'Conferencia'), conferenciaInstance.id])
        redirect(action: "show", id: conferenciaInstance.id)
    }

    def save() {
        def conferenciaInstance = new Conferencia(params)
        PublicationController pb = new PublicationController()
        if (!pb.upload(conferenciaInstance) || !conferenciaInstance.save(flush: true)) {
            render(view: "create", model: [conferenciaInstance: conferenciaInstance])
            return
        }
        alertMessage('created',conferenciaInstance)
    }

    private returnConferenciaInstance(){
        def conferenciaInstance = Conferencia.get(params.id)
        boolean isReturned = aux.check(params.id, conferenciaInstance, 'conferencia.label', 'Conferencia');
        if(!isReturned){
            [conferenciaInstance: conferenciaInstance]
        }

    }

    private returnConferenciaActualVersion()
    {
        def conferenciaInstance = Conferencia.get(params.id)
        boolean isReturned = aux.check(params.id, conferenciaInstance, 'conferencia.label', 'Conferencia');
        if(!isReturned)
        {
            if (params.version) {
                def version = params.version.toLong()
                if (conferenciaInstance.version > version) {
                    conferenciaInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                            [message(code: 'conferencia.label', default: 'Conferencia')] as Object[],
                            message(code: 'default.updateError.message'))
                    render(view: "edit", model: [conferenciaInstance: conferenciaInstance])
                    return  }  }
            conferenciaInstance.properties = params
            if (!conferenciaInstance.save(flush: true)) {
                render(view: "edit", model: [conferenciaInstance: conferenciaInstance])
                return
            }
            alertMessage('updated',conferenciaInstance)
        }
    }

    def show() {
        returnConferenciaInstance()
    }

    def edit() {
        returnConferenciaInstance()
    }

    def update() {
        returnConferenciaActualVersion();
    }

    def delete() {
		def conferenciaInstance = Conferencia.get(params.id)
		aux.delete(params.id, conferenciaInstance, 'conferencia.label', 'Conferencia');
	}
}
