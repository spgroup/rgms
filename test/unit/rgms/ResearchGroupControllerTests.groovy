package rgms



import org.junit.*
import grails.test.mixin.*

@TestFor(ResearchGroupController)
@Mock(ResearchGroup)
class ResearchGroupControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/researchGroup/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.researchGroupInstanceList.size() == 0
        assert model.researchGroupInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.researchGroupInstance != null
    }

    void testSave() {
        controller.save()

        assert model.researchGroupInstance != null
        assert view == '/researchGroup/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/researchGroup/show/1'
        assert controller.flash.message != null
        assert ResearchGroup.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/researchGroup/list'


        populateValidParams(params)
        def researchGroup = new ResearchGroup(params)

        assert researchGroup.save() != null

        params.id = researchGroup.id

        def model = controller.show()

        assert model.researchGroupInstance == researchGroup
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/researchGroup/list'


        populateValidParams(params)
        def researchGroup = new ResearchGroup(params)

        assert researchGroup.save() != null

        params.id = researchGroup.id

        def model = controller.edit()

        assert model.researchGroupInstance == researchGroup
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/researchGroup/list'

        response.reset()


        populateValidParams(params)
        def researchGroup = new ResearchGroup(params)

        assert researchGroup.save() != null

        // test invalid parameters in update
        params.id = researchGroup.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/researchGroup/edit"
        assert model.researchGroupInstance != null

        researchGroup.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/researchGroup/show/$researchGroup.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        researchGroup.clearErrors()

        populateValidParams(params)
        params.id = researchGroup.id
        params.version = -1
        controller.update()

        assert view == "/researchGroup/edit"
        assert model.researchGroupInstance != null
        assert model.researchGroupInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/researchGroup/list'

        response.reset()

        populateValidParams(params)
        def researchGroup = new ResearchGroup(params)

        assert researchGroup.save() != null
        assert ResearchGroup.count() == 1

        params.id = researchGroup.id

        controller.delete()

        assert ResearchGroup.count() == 0
        assert ResearchGroup.get(researchGroup.id) == null
        assert response.redirectedUrl == '/researchGroup/list'
    }
}
