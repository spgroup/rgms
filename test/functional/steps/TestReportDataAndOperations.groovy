package steps

import rgms.publication.TechnicalReport
import rgms.publication.TechnicalReportController

/**
 * Created with IntelliJ IDEA.
 * User: Hugo
 * Date: 03/03/13
 * Time: 22:04
 * To change this template use File | Settings | File Templates.
 */
class TestReportDataAndOperations {

    static reports = [
            [journal: "Theoretical Computer Science", volume: 455, number: 1, pages: "2-30",
                    title: "A theory of software product line refinement",
                    publicationDate: (new Date("12 October 2012"))],
            [journal: "Science of Computer Programming", volume: 455, pages: "2-30",
                    title: "Algebraic reasoning for object-oriented programming",
                    publicationDate: (new Date("12 October 2012"))]
    ]

    static public def findByTitle(String title) {
        reports.find { report ->
            report.title == title
        }
    }

    static public void createReport(filename) {
        createReport("", filename)
    }

    static public void createReport(String title, filename) {
        def cont = new TechnicalReportController()
        def date = new Date()
        cont.params << TestReportDataAndOperations.findByTitle(title) << [file: filename]
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.create()
        cont.save()
        cont.response.reset()
    }

    static public void updateReportWithNoTitleAndInst(String title) {
        def cont = new TechnicalReportController()
        def date = new Date()
        def tempReport = TestReportDataAndOperations.findByTitle(title)
        tempReport.title = null
        tempReport.institution = null
        cont.params << tempReport
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.save()
        cont.response.reset()
    }

    static public void removeReport(String title) {
        def cont = new TechnicalReportController()
        def date = new Date()
        TechnicalReport.findByTitle(title).delete(flush: true)
    }
}
