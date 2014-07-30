package rgms.publication

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
        session.removeAttribute("authors")
    }

    private alertMessage(String typeMessage, Conferencia conferenciaInstance){
        flash.message = message(code: 'default.'+ typeMessage +'.message', args: [message(code: 'conferencia.label', default: 'Conferencia'), conferenciaInstance.id])
        redirect(action: "show", id: conferenciaInstance.id)
    }

    def save() {

        print(params)
        def conferenciaInstance = new Conferencia(params)
        PublicationController pb = new PublicationController()
        if (!pb.upload(conferenciaInstance) || !conferenciaInstance.save(flush: true)) {
            render(view: "create", model: [conferenciaInstance: conferenciaInstance])
            return
        }
        alertMessage('created',conferenciaInstance)
        session.removeAttribute("authors")
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

    // $if($managingAuthors)

    def static String getLoggedMemberName()
    {
        String loggedMemberName = ""
        Member loggedMember = PublicationController.getLoggedMember()

        if ( loggedMember != null )
            loggedMemberName = loggedMember.name

        return loggedMemberName
    }

    def static List<String> getAuthors()
    {
        Set<Member> members = PublicationController.membersOrderByUsually()
        ArrayList<String> memberAuthors = new ArrayList<String>()

        for ( Member member : members )
            memberAuthors.add(member.name)

        return memberAuthors
    }

    def updateAuthorList()
    {
        List<String> authorList = (List<String>) session["authors"]
        String loggedMemberName = getLoggedMemberName()

        render("<table>")
        for ( String authorName : authorList )
        {
            if ( loggedMemberName.equals(authorName) )
                render("<tr><td>" + authorName + "</td></tr>")
            else
            {
                render("<tr><td>" + authorName + "</td>")
                render("<td><input id=\"${authorName}\" onclick=\"removeFromList(this)\" type=\"button\" value=\"${message(code: 'conferencia.removeAuthor.label', default: 'remove author')}\" /></td></tr>")
                render( "<td><g:field type=\"hidden\" name=\"authors\" value=\""+ authorName +"\"/></td></tr>" )
            }
        }
        render("</table>")
    }

    def addAuthor()
    {
      List<String> authorList = (List<String>) session["authors"]
      String addedAuthor = params.addAuthor

      if ( authorList == null )
      {
          Set<Member> members = PublicationController.membersOrderByUsually()
          authorList = new ArrayList<String>()

          for ( Member member : members )
            authorList.add( member.name )
      }

      if ( addedAuthor.length() > 0 )
        authorList.add(addedAuthor)

      session["authors"] = authorList
      updateAuthorList()
    }

    def removeAuthor()
    {
        List<String> authorList = (List<String>) session["authors"]
        String selectedAuthor = params.selectedAuthor

        if (authorList != null && !selectedAuthor.equals(loggedMemberName))
        {
            authorList.remove(selectedAuthor)
            session["authors"] = authorList
            updateAuthorList()
        }
    }
    //#end
}
