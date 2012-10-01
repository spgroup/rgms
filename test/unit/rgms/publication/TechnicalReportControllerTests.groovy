package rgms.publication



import org.junit.*
import grails.test.mixin.*

@TestFor(TechnicalReportController)
@Mock(TechnicalReport)
class TechnicalReportControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/technicalReport/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.technicalReportInstanceList.size() == 0
        assert model.technicalReportInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.technicalReportInstance != null
    }

    void testSave() {
        controller.save()

        assert model.technicalReportInstance != null
        assert view == '/technicalReport/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/technicalReport/show/1'
        assert controller.flash.message != null
        assert TechnicalReport.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/technicalReport/list'

        populateValidParams(params)
        def technicalReport = new TechnicalReport(params)

        assert technicalReport.save() != null

        params.id = technicalReport.id

        def model = controller.show()

        assert model.technicalReportInstance == technicalReport
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/technicalReport/list'

        populateValidParams(params)
        def technicalReport = new TechnicalReport(params)

        assert technicalReport.save() != null

        params.id = technicalReport.id

        def model = controller.edit()

        assert model.technicalReportInstance == technicalReport
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/technicalReport/list'

        response.reset()

        populateValidParams(params)
        def technicalReport = new TechnicalReport(params)

        assert technicalReport.save() != null

        // test invalid parameters in update
        params.id = technicalReport.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/technicalReport/edit"
        assert model.technicalReportInstance != null

        technicalReport.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/technicalReport/show/$technicalReport.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        technicalReport.clearErrors()

        populateValidParams(params)
        params.id = technicalReport.id
        params.version = -1
        controller.update()

        assert view == "/technicalReport/edit"
        assert model.technicalReportInstance != null
        assert model.technicalReportInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/technicalReport/list'

        response.reset()

        populateValidParams(params)
        def technicalReport = new TechnicalReport(params)

        assert technicalReport.save() != null
        assert TechnicalReport.count() == 1

        params.id = technicalReport.id

        controller.delete()

        assert TechnicalReport.count() == 0
        assert TechnicalReport.get(technicalReport.id) == null
        assert response.redirectedUrl == '/technicalReport/list'
    }
}
