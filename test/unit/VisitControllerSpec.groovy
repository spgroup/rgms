import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import rgms.visit.Visit
import rgms.visit.VisitController
import rgms.visit.Visitor
import spock.lang.Specification;

@TestFor(VisitController)
@Mock([Visit, Visitor])
class VisitControllerSpec extends Specification {

    def 'save action: invalid visit'() {
        setup:
        controller.params.nameVisitor = visitorName
        controller.params.initialDate = beginDate
        controller.params.finalDate = endDate

        when:
        controller.save()

        then:
        view.endsWith "create"
        model.visitInstance.visitor == Visitor.findByName(visitorName)
        model.visitInstance.initialDate == beginDate
        model.visitInstance.finalDate == endDate

        where:
        visitorName = "Carlos"
        beginDate = new Date("14 August 2013")
        endDate = new Date("13 August 2013")
    }

    def 'save action: valid visit'() {
        setup:
        controller.params.nameVisitor = visitorName
        controller.params.initialDate = beginDate
        controller.params.finalDate = endDate

        when:
        controller.save()

        then:
        response.redirectUrl.endsWith "show/1"
        controller.flash.message != null

        where:
        visitorName = "Carlos"
        beginDate = new Date("13 August 2013")
        endDate = new Date("14 August 2013")
    }
}