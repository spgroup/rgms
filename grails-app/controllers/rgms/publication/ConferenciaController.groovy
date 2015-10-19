package rgms.publication

import org.hibernate.collection.PersistentSet
import org.hibernate.validator.jtype.Generics

//#if($XMLUpload && $Conferencia)
import rgms.XMLService
//#end
import rgms.member.Member

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
        //#if($managingAuthors)
        moveAuthorListToParams()
        //#end

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
        //#if($manageAuthors)
        moveAuthorListToParams()
        //#end
        returnConferenciaActualVersion();
    }

    def delete() {
		def conferenciaInstance = Conferencia.get(params.id)
		aux.delete(params.id, conferenciaInstance, 'conferencia.label', 'Conferencia');
	}

    //#if($managingAuthors)
    private moveAuthorListToParams(){
        params.authors = session["authors"]
        session.removeAttribute("authors")

        if ( params.id != null )
        {
            def conferenciaInstance = Conferencia.get(params.id)
            conferenciaInstance.authors = params.authors
        }
    }

    def static String getLoggedMemberName()
    {
        String loggedMemberName = ""
        Member loggedMember = PublicationController.getLoggedMember()

        if ( loggedMember != null )
            loggedMemberName = loggedMember.name

        return loggedMemberName
    }

    def static List getDefaultAuthors()
    {
        Set<Member> members = PublicationController.membersOrderByUsually()
        Collection defaultAuthors = new ArrayList()

        for (Member m : members.iterator())
           defaultAuthors.add(m.name)

        return defaultAuthors
    }

    def addAuthor()
    {
      Collection authors = (Collection) session["authors"]
      String addedAuthor = params.addAuthor

      if ( authors == null )
          authors = defaultAuthors

      if ( addedAuthor.length() > 0 && !authors.contains(addedAuthor) )
          authors.add(addedAuthor)

      session["authors"] = authors
      render(template: "authorTab")
    }

    def removeAuthor()
    {
        Collection authors = (Collection) session["authors"]
        String removeAuthor = params.removeAuthor

        if (authors != null && !removeAuthor.equals(loggedMemberName)) {
            authors.remove(removeAuthor)
            session["authors"] = authors
            render(template: "authorTab")
        }
    }

    def findMemberByName()
    {
        String input = params.addAuthor

        if ( input.length() > 0 ){
             Member member = Member.findByNameIlike('%'+input+'%')

            if (member != null && !member.name.equals(input))
                render(member.name)
        }
    }
    //#end
}
