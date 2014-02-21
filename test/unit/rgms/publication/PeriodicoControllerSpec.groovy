package rgms.publication

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * Created by Joao on 2/20/14.
 */

@TestFor(PeriodicoController)
class PeriodicoControllerSpec extends Specification{

    def "Null pointer to checkPeriodicoVersion" (){
        setup:
        mockController(PeriodicoController)

        when:
        def periodicoCont = novoCont

        then:
        periodicoCont.checkPeriodicoVersion(nullPointer)

        where:
        novoCont = new PeriodicoController()
        nullPointer = null

    }

}
