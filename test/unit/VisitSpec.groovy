import grails.test.mixin.TestFor
import rgms.visit.Visit
import rgms.visit.Visitor
import spock.lang.Specification;

@TestFor(Visit)
class VisitSpec extends Specification {

    def "find visit by visitor, initialDate and finalDate"() {
        setup:
        mockDomain(Visit)
        mockDomain(Visitor)

        when:
        new Visit(initialDate:beginDate, finalDate:endDate, visitor:newVisitor.save()).save()

        then:
        Visit.findByVisitorAndInitialDateAndFinalDate(newVisitor, beginDate, endDate) != null

        where:
        newVisitor = new Visitor(name:"Carlos")
        beginDate = new Date("13 August 2013")
        endDate = new Date("14 August 2013")
    }

    def "initial date not null"() {
        setup:
        mockForConstraintsTests(Visit)
        mockDomain(Visitor)

        when:
        def visit = new Visit(initialDate:date, visitor:new Visitor(name:visitorName).save())
        visit.validate()

        then:
        visit.errors.hasFieldErrors("initialDate")

        where:
        visitorName = "Carlos"
        date = null
    }

    def "initial <= finalDate"() {
        setup:
        mockForConstraintsTests(Visit)
        mockDomain(Visitor)

        when:
        def visit = new Visit(initialDate:beginDate, finalDate:endDate, visitor:new Visitor(name:visitorName).save())
        visit.validate()

        then:
        visit.errors.hasFieldErrors("finalDate")

        where:
        visitorName = "Carlos"
        beginDate = new Date("14 August 2013")
        endDate = new Date("13 August 2013")
    }
}