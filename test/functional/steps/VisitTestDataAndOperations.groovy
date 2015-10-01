package steps

import rgms.visit.Visit
import rgms.visit.VisitController
import rgms.visit.Visitor

class VisitTestDataAndOperations {

//#if ( $visit )

    static public def findVisit(String name, String initialDate, String finalDate) {
        def visitController = new VisitController()
        visitController.params.visitor = Visitor.findByName(name)
        visitController.params.initialDate = Date.parse("dd/MM/yyyy", initialDate)
        visitController.params.finalDate = Date.parse("dd/MM/yyyy", finalDate)
        def result = Visit.list(visitController.params)
        return result
    }

    static public void createVisitor(String nome) {
        def visitController = new VisitController()
        visitController.params.nameVisitor = nome
        visitController.request.setContent(new byte[1000]) // Could also vary the request content.
        visitController.createVisitor();
        visitController.response.reset()
    }

    /**
     * @author carloscemb
     */
    static public void createVisit(String name, String initialDate, String finalDate) {
        def visitController = new VisitController()
        visitController.params.nameVisitor = name
        visitController.params.initialDate = Date.parse("dd/MM/yyyy", initialDate)
        visitController.params.finalDate = Date.parse("dd/MM/yyyy", finalDate)
        visitController.request.setContent(new byte[1000]) // Could also vary the request content.
        visitController.create()
        visitController.save()
        visitController.response.reset()
    }

    /**
     * @author carloscemb
     */
    static public boolean containsVisit(Visit visit) {
        def visitController = new VisitController()
        def result = visitController.list(100).visitInstanceList
        return result.contains(visit)
    }

    /**
     * @author carloscemb
     */
    static public void removeVisit(String name, String initialDate, String finalDate) {
        def visitController = new VisitController()
        def visit = searchVisit(name, initialDate, finalDate)
        visitController.params << [id: visit.id]
        visitController.delete((Long)visitController.params.id)
    }

    /**
     * @author carloscemb
     */
    static public def editVisit(String oldVisitor, String oldInitialDate, String oldFinalDate, String newVisitorName) {
        def visit = searchVisit(oldVisitor, oldInitialDate, oldFinalDate)
        visit.setVisitor(getNewVisitor(newVisitorName))
        updateVisit(visit)
        def updatedVisit = searchVisit(newVisitorName, oldInitialDate, oldFinalDate)
        return updatedVisit
    }

    /**
     * @author penc
     */
    static public def editVisitChangeData(String visitorName, String initialDate, String oldFinalDate, String newFinalDate) {
        def visit = searchVisit(visitorName, initialDate, oldFinalDate)
        visit.setFinalDate(Date.parse("dd/MM/yyyy", newFinalDate))
        updateVisit(visit)
    }

    /**
     * @author carloscemb
     */
    static public def updateVisit(Visit visit) {
        def visitController = new VisitController()
        visitController.params << visit.properties
        visitController.update((Long)visitController.params.id, (Long)visitController.params.version)
    }

    /**
     * @author carloscemb
     */
    static public Visit searchVisit(String name, String initialDate, String finalDate) {
        def visitor = Visitor.findByName(name)
        Date day_1 = Date.parse("dd/MM/yyyy", initialDate)
        Date day_2 = Date.parse("dd/MM/yyyy", finalDate)
        def visit = Visit.findByVisitorAndInitialDateAndFinalDate(visitor, day_1, day_2)
        return visit
    }

    /**
     * @author carloscemb
     */
    static public def getNewVisitor(String newVisitorName) {
        def newVisitor = Visitor.findByName(newVisitorName)
        if(newVisitor == null) {
            createVisitor(newVisitorName)
            newVisitor = Visitor.findByName(newVisitorName)
        }
        return newVisitor
    }

//#end
}
