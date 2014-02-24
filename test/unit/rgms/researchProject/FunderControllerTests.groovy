package rgms.researchProject



import org.junit.*
import grails.test.mixin.*

@TestFor(FunderController)
@Mock(Funder)
class FunderControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/funder/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.funderInstanceList.size() == 0
        assert model.funderInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.funderInstance != null
    }

    void testSave() {
        controller.save()

        assert model.funderInstance != null
        assert view == '/funder/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/funder/show/1'
        assert controller.flash.message != null
        assert Funder.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/funder/list'

        populateValidParams(params)
        def funder = new Funder(params)

        assert funder.save() != null

        params.id = funder.id

        def model = controller.show()

        assert model.funderInstance == funder
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/funder/list'

        populateValidParams(params)
        def funder = new Funder(params)

        assert funder.save() != null

        params.id = funder.id

        def model = controller.edit()

        assert model.funderInstance == funder
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/funder/list'

        response.reset()

        populateValidParams(params)
        def funder = new Funder(params)

        assert funder.save() != null

        // test invalid parameters in update
        params.id = funder.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/funder/edit"
        assert model.funderInstance != null

        funder.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/funder/show/$funder.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        funder.clearErrors()

        populateValidParams(params)
        params.id = funder.id
        params.version = -1
        controller.update()

        assert view == "/funder/edit"
        assert model.funderInstance != null
        assert model.funderInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/funder/list'

        response.reset()

        populateValidParams(params)
        def funder = new Funder(params)

        assert funder.save() != null
        assert Funder.count() == 1

        params.id = funder.id

        controller.delete()

        assert Funder.count() == 0
        assert Funder.get(funder.id) == null
        assert response.redirectedUrl == '/funder/list'
    }
}
