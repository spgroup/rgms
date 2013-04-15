package rgms.member

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(Orientation)
class OrientationSpecification extends Specification{
    Orientation orientation = new Orientation()
    Orientation orientation1 = new Orientation()

    def "An orientation's type must be Master's Degree, Doctorate or Undergraduate Research"() {
        expect: orientation.tipo == "Mestrado" || orientation.tipo == "Doutorado" || orientation.tipo == "Iniciação Científica"
    }

    def "The system cannot have two equal orientations"(){
        expect: !(orientation.Equals(orientation1))
    }
}
