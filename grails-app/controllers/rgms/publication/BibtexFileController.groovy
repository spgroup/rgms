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

        System.out.println("********** " + file.getBytes().size());

        BibtexFile bibtexFile = transform(file)

        System.out.println("********** " + bibtexFile.getPublications().size())
        for (Publication publication : bibtexFile.getPublications()) {
            System.out.println("************* " + publication);
            System.out.println("************************** School : " + publication.getSchool());
            System.out.println("************************** Address: " + publication.getAddress());
            System.out.println("************************** Title  : " + publication.getTitle());
            System.out.println("************************** Date   : " + publication.getPublicationDate());
            if (publication.save(failOnError: true)) {
                System.out.println("salvando o objeto");
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
