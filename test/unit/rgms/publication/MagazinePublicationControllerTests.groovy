package rgms.publication


import org.junit.*
import grails.test.mixin.*

@TestFor(MagazinePublicationController)
@Mock(MagazinePublication)
class MagazinePublicationControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/magazinePublication/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.magazinePublicationInstanceList.size() == 0
        assert model.magazinePublicationInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.magazinePublicationInstance != null
    }

    void testSave() {
        controller.save()

        assert model.magazinePublicationInstance != null
        assert view == '/magazinePublication/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/magazinePublication/show/1'
        assert controller.flash.message != null
        assert MagazinePublication.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/magazinePublication/list'

        populateValidParams(params)
        def magazinePublication = new MagazinePublication(params)

        assert magazinePublication.save() != null

        params.id = magazinePublication.id

        def model = controller.show()

        assert model.magazinePublicationInstance == magazinePublication
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/magazinePublication/list'

        populateValidParams(params)
        def magazinePublication = new MagazinePublication(params)

        assert magazinePublication.save() != null

        params.id = magazinePublication.id

        def model = controller.edit()

        assert model.magazinePublicationInstance == magazinePublication
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/magazinePublication/list'

        response.reset()

        populateValidParams(params)
        def magazinePublication = new MagazinePublication(params)

        assert magazinePublication.save() != null

        // test invalid parameters in update
        params.id = magazinePublication.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/magazinePublication/edit"
        assert model.magazinePublicationInstance != null

        magazinePublication.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/magazinePublication/show/$magazinePublication.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        magazinePublication.clearErrors()

        populateValidParams(params)
        params.id = magazinePublication.id
        params.version = -1
        controller.update()

        assert view == "/magazinePublication/edit"
        assert model.magazinePublicationInstance != null
        assert model.magazinePublicationInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/magazinePublication/list'

        response.reset()

        populateValidParams(params)
        def magazinePublication = new MagazinePublication(params)

        assert magazinePublication.save() != null
        assert MagazinePublication.count() == 1

        params.id = magazinePublication.id

        controller.delete()

        assert MagazinePublication.count() == 0
        assert MagazinePublication.get(magazinePublication.id) == null
        assert response.redirectedUrl == '/magazinePublication/list'
    }
}
