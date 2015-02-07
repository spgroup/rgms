package steps

import rgms.publication.TechnicalReport
import rgms.publication.TechnicalReportController

class TechnicalReportTestDataAndOperations {
    static reports = [
            [title: 'Evaluating Natural Languages System',
                    publicationDate: (new Date('13 November 2012')), institution: 'UFPE'],
            [title: 'NFL Languages System',
                    publicationDate: (new Date('27 October 2011')), institution: 'NFL'],
            [title: 'Joe-E',
                    publicationDate: (new Date('1 May 2013')), institution: 'TG'],
            [title: 'RC77-1',
                    publicationDate: (new Date('15 May 2013')), institution: 'MIT', file: "TCS-77.pdf"]
    ]

    static public def findTechnicalReportByTitle(String title) {
        reports.find { report ->
            report.title == title
        }
    }

    static public boolean technicalReportCompatibleTo(tech, title) {
        def testtech = findTechnicalReportByTitle(title)
        def compatible = false
        if (testtech == null && tech == null) {
            compatible = true
        } else if (testtech != null && tech != null) {
            compatible = true
            testtech.each { key, data ->
                compatible = compatible && (tech."$key" == data)
            }
        }
        return compatible
    }

    static public TechnicalReport editTech(oldtitle, newtitle) {
        def tech = TechnicalReport.findByTitle(oldtitle)
        tech.setTitle(newtitle)
        def cont = new TechnicalReportController()
        cont.params << tech.properties
        cont.update()
        def updatedtech = TechnicalReport.findByTitle(newtitle)
        return updatedtech
    }

    static public void createTechnicalReport(String title, filename) {
        def params = TechnicalReportTestDataAndOperations.findTechnicalReportByTitle(title)
        prepareCreateSaveAndResetTechnicalReportController(params, filename)
    }

    static public void createTechnicalReportWithEmptyInstitution(String title, filename) {
        def params = TechnicalReportTestDataAndOperations.findTechnicalReportByTitle(title)
        params["institution"] = ""
        prepareCreateSaveAndResetTechnicalReportController(params, filename)
    }

    static private void prepareCreateSaveAndResetTechnicalReportController(params, filename) {
        def cont = new TechnicalReportController()
        cont.params << params << [file: filename]
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.create()
        cont.save()
        cont.response.reset()
    }
    
    static private void removeMultipleTechReports (reports){
	    def cont = new TechnicalReportController()
	    cont.params.reports
	    cont.request.setContent(new byte[1000])
	    cont.delete()
	    cont.response.reset()
    }
    
}
