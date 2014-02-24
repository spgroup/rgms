package rgms.researchProject



import org.junit.*
import grails.test.mixin.*

@TestFor(ResearchProjectController)
@Mock(ResearchProject)
class ResearchProjectControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/researchProject/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.researchProjectInstanceList.size() == 0
        assert model.researchProjectInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.researchProjectInstance != null
    }

    void testSave() {
        controller.save()

        assert model.researchProjectInstance != null
        assert view == '/researchProject/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/researchProject/show/1'
        assert controller.flash.message != null
        assert ResearchProject.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/researchProject/list'

        populateValidParams(params)
        def researchProject = new ResearchProject(params)

        assert researchProject.save() != null

        params.id = researchProject.id

        def model = controller.show()

        assert model.researchProjectInstance == researchProject
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/researchProject/list'

        populateValidParams(params)
        def researchProject = new ResearchProject(params)

        assert researchProject.save() != null

        params.id = researchProject.id

        def model = controller.edit()

        assert model.researchProjectInstance == researchProject
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/researchProject/list'

        response.reset()

        populateValidParams(params)
        def researchProject = new ResearchProject(params)

        assert researchProject.save() != null

        // test invalid parameters in update
        params.id = researchProject.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/researchProject/edit"
        assert model.researchProjectInstance != null

        researchProject.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/researchProject/show/$researchProject.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        researchProject.clearErrors()

        populateValidParams(params)
        params.id = researchProject.id
        params.version = -1
        controller.update()

        assert view == "/researchProject/edit"
        assert model.researchProjectInstance != null
        assert model.researchProjectInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/researchProject/list'

        response.reset()

        populateValidParams(params)
        def researchProject = new ResearchProject(params)

        assert researchProject.save() != null
        assert ResearchProject.count() == 1

        params.id = researchProject.id

        controller.delete()

        assert ResearchProject.count() == 0
        assert ResearchProject.get(researchProject.id) == null
        assert response.redirectedUrl == '/researchProject/list'
    }
}
