import grails.test.mixin.TestFor
import rgms.visit.Visitor
import spock.lang.Specification;

@TestFor(Visitor)
class VisitorSpec extends Specification {

    def "find visitor by name"() {
        setup:
        mockDomain(Visitor)

        when:
        new Visitor(name:visitorName).save()

        then:
        Visitor.findByName(visitorName) != null

        where:
        visitorName = "Carlos"
    }

    def "visitor name not null"() {
        setup:
        mockForConstraintsTests(Visitor)

        when:
        def visitor = new Visitor(name:visitorName)
        visitor.validate()

        then:
        visitor.errors.hasFieldErrors("name")

        where:
        visitorName = null
    }

    def "visitor name not blank"() {
        setup:
        mockForConstraintsTests(Visitor)

        when:
        def visitor = new Visitor(name:visitorName)
        visitor.validate()

        then:
        visitor.errors.hasFieldErrors("name")

        where:
        visitorName = ""
    }
}