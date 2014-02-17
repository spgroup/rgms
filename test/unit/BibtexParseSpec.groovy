import spock.lang.Specification
import rgms.publication.BibtexParse
import grails.test.mixin.TestFor

/**
 * Created by Joao on 2/13/14.
 */

@TestFor(BibtexParse)
class BibtexParseSpec extends Specification{

      def "Null pointer to File"(){

          setup:
          mockDomain(BibtexParse)

          when:
          new BibtexParse().parseBibTeX(nullPointer)

          then:
          thrown(NullPointerException)

          where:
          nullPointer = null

      }

      def "Non File Object Passed"(){

          setup:
          mockDomain(BibtexParse)

          when:
          new BibtexParse().parseBibTeX(sampleObj)

          then:
          thrown(MissingMethodException)

          where:
          sampleObj = new Object()

      }

}
