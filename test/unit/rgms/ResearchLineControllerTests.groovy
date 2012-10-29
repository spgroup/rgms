package rgms



import org.junit.*

import rgms.publication.ResearchLine;
import rgms.publication.ResearchLineController;
import grails.test.mixin.*

@TestFor(ResearchLineController)
@Mock(ResearchLine)
class ResearchLineControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/researchLine/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.researchLineInstanceList.size() == 0
        assert model.researchLineInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.researchLineInstance != null
    }

    void testSave() {
        controller.save()

        assert model.researchLineInstance != null
        assert view == '/researchLine/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/researchLine/show/1'
        assert controller.flash.message != null
        assert ResearchLine.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/researchLine/list'


        populateValidParams(params)
        def researchLine = new ResearchLine(params)

        assert researchLine.save() != null

        params.id = researchLine.id

        def model = controller.show()

        assert model.researchLineInstance == researchLine
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/researchLine/list'


        populateValidParams(params)
        def researchLine = new ResearchLine(params)

        assert researchLine.save() != null

        params.id = researchLine.id

        def model = controller.edit()

        assert model.researchLineInstance == researchLine
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/researchLine/list'

        response.reset()


        populateValidParams(params)
        def researchLine = new ResearchLine(params)

        assert researchLine.save() != null

        // test invalid parameters in update
        params.id = researchLine.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/researchLine/edit"
        assert model.researchLineInstance != null

        researchLine.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/researchLine/show/$researchLine.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        researchLine.clearErrors()

        populateValidParams(params)
        params.id = researchLine.id
        params.version = -1
        controller.update()

        assert view == "/researchLine/edit"
        assert model.researchLineInstance != null
        assert model.researchLineInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/researchLine/list'

        response.reset()

        populateValidParams(params)
        def researchLine = new ResearchLine(params)

        assert researchLine.save() != null
        assert ResearchLine.count() == 1

        params.id = researchLine.id

        controller.delete()

        assert ResearchLine.count() == 0
        assert ResearchLine.get(researchLine.id) == null
        assert response.redirectedUrl == '/researchLine/list'
    }
}
