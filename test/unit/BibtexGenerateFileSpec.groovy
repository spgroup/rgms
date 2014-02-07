import grails.test.mixin.TestFor
import rgms.publication.BibtexGenerateFile
import rgms.publication.BibtexParse
import spock.lang.Specification

/**
 * Created by JoaoGFarias on 2/7/14.
 */

@TestFor(BibtexGenerateFile)
class BibtexGenerateFileSpec extends Specification{

    def "Equal Publications"() {
        setup:
        mockDomain(BibtexGenerateFile)

        when:
        def bibTexGenFile = new BibtexGenerateFile(file:file)

        then:
        bibTexGenFile.getPublications() ==  BibtexParse.generatePublications(file)

        where:
        file = null //Change to a valid file
    }
}
