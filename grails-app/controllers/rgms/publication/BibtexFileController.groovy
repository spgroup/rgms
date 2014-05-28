package rgms.publication

import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile

/**
 *
 * @author Diogo Vin�cius
 *
 */
class BibtexFileController {

    def index() {}

    def upload() {
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request
        CommonsMultipartFile cmf = (CommonsMultipartFile) multiRequest.getFile("file")
        byte[] bytes = cmf.bytes
        File file = new File("web-app//uploads//temp.bibtex")
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))
        bos.write(bytes)
        bos.close()

        BibtexFile bibtexFile = transform(file)

        for (Publication publication : bibtexFile.getPublications()) {
            if (publication.save(failOnError: true)) {
                System.out.println("Saving the Object");
            }
        }
        redirect(action: "home")

    }

    def home() {

    }

    def BibtexFile transform(file) {
        return new BibtexFile(file)
    }

}
