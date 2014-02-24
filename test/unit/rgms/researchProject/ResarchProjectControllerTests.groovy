package rgms.researchProject

import grails.test.mixin.*

@TestFor(ResarchProjectController)
@Mock(ResearchProject)
class ResarchProjectControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/resarchProject/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.resarchProjectInstanceList.size() == 0
        assert model.resarchProjectInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.resarchProjectInstance != null
    }

    void testSave() {
        controller.save()

        assert model.resarchProjectInstance != null
        assert view == '/resarchProject/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/resarchProject/show/1'
        assert controller.flash.message != null
        assert ResearchProject.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/resarchProject/list'

        populateValidParams(params)
        def resarchProject = new ResearchProject(params)

        assert resarchProject.save() != null

        params.id = resarchProject.id

        def model = controller.show()

        assert model.resarchProjectInstance == resarchProject
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/resarchProject/list'

        populateValidParams(params)
        def resarchProject = new ResearchProject(params)

        assert resarchProject.save() != null

        params.id = resarchProject.id

        def model = controller.edit()

        assert model.resarchProjectInstance == resarchProject
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/resarchProject/list'

        response.reset()

        populateValidParams(params)
        def resarchProject = new ResearchProject(params)

        assert resarchProject.save() != null

        // test invalid parameters in update
        params.id = resarchProject.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/resarchProject/edit"
        assert model.resarchProjectInstance != null

        resarchProject.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/resarchProject/show/$resarchProject.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        resarchProject.clearErrors()

        populateValidParams(params)
        params.id = resarchProject.id
        params.version = -1
        controller.update()

        assert view == "/resarchProject/edit"
        assert model.resarchProjectInstance != null
        assert model.resarchProjectInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/resarchProject/list'

        response.reset()

        populateValidParams(params)
        def resarchProject = new ResearchProject(params)

        assert resarchProject.save() != null
        assert ResearchProject.count() == 1

        params.id = resarchProject.id

        controller.delete()

        assert ResearchProject.count() == 0
        assert ResearchProject.get(resarchProject.id) == null
        assert response.redirectedUrl == '/resarchProject/list'
    }
}
