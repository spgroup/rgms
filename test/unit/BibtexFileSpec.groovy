import grails.test.mixin.TestFor
import rgms.publication.BibtexFile
import rgms.publication.BibtexParse
import spock.lang.Specification

/**
 * Created by JoaoGFarias on 2/7/14.
 */

@TestFor(BibtexFile)
class BibtexFileSpec extends Specification{

    def "Equal Publications"() {
        setup:
        mockDomain(BibtexFile)

        when:
        def bibTexFile = new BibtexFile(file)

        then:
        bibTexFile.getPublications() ==  BibtexParse.generatePublications(file)

        where:
        file = new File("test//cucumber//steps//sample.bibtex")
    }
}
