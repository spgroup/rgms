package rgms.publication

import org.apache.shiro.SecurityUtils
import org.springframework.dao.DataIntegrityViolationException
import rgms.member.Member
import rgms.publication.Publication;


class PublicationController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        render(view: "publication")
    }
//#if($Bibtex)
    def generateBib() {
        def publication = Publication.get(params.id)
        render(text: publication.generateBib(), contentType: "text/txt", encoding: "UTF-8")
    }
//#end

  //#if($publicationContext)
    static Member addAuthor(Publication publication){
        Member user = Member.findByUsername(SecurityUtils.subject.principal)
        if(!publication.members){
            publication.members = new LinkedHashSet<Member>()
        }
        publication.members.add(user);
        return user
    }
    //#end

    def upload(Publication publicationInstance) {

        def originalName = publicationInstance.file
        def filePath = "web-app/uploads/${originalName}"
        publicationInstance.file = filePath

        def f = new File(filePath)

        if (f.exists()) {
            flash.message = message(code: 'file.already.exist.message', default: 'File already exists. Please try to use a different file name.')
            return false
        }

        InputStream inputStream = request.getInputStream()
        OutputStream outputStream = new FileOutputStream(f)
        byte[] buffer = new byte[1024 * 10] //buffer de 10MB
        int length

        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length)
        }
        outputStream.close()
        inputStream.close()

        return true
    }

    def static newUpload(Publication publicationInstance, flash, request) {

        def originalName = publicationInstance.file
        def filePath = "web-app/uploads/${originalName}"
        publicationInstance.file = filePath

        def f = new File(filePath)

        if (f.exists()) {
            flash.message = 'File already exists. Please try to use a different file name.'
            return false
        }

        InputStream inputStream = request.getInputStream()
        OutputStream outputStream = new FileOutputStream(f)
        byte[] buffer = new byte[1024 * 10] //buffer de 10MB
        int length

        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length)
        }
        outputStream.close()
        inputStream.close()

        return true
    }
}
