package rgms



import org.junit.*
import grails.test.mixin.*

@TestFor(PublicationController)
@Mock(Publication)
class PublicationControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/publication/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.publicationInstanceList.size() == 0
        assert model.publicationInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.publicationInstance != null
    }

    void testSave() {
        controller.save()

        assert model.publicationInstance != null
        assert view == '/publication/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/publication/show/1'
        assert controller.flash.message != null
        assert Publication.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/publication/list'


        populateValidParams(params)
        def publication = new Publication(params)

        assert publication.save() != null

        params.id = publication.id

        def model = controller.show()

        assert model.publicationInstance == publication
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/publication/list'


        populateValidParams(params)
        def publication = new Publication(params)

        assert publication.save() != null

        params.id = publication.id

        def model = controller.edit()

        assert model.publicationInstance == publication
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/publication/list'

        response.reset()


        populateValidParams(params)
        def publication = new Publication(params)

        assert publication.save() != null

        // test invalid parameters in update
        params.id = publication.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/publication/edit"
        assert model.publicationInstance != null

        publication.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/publication/show/$publication.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        publication.clearErrors()

        populateValidParams(params)
        params.id = publication.id
        params.version = -1
        controller.update()

        assert view == "/publication/edit"
        assert model.publicationInstance != null
        assert model.publicationInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/publication/list'

        response.reset()

        populateValidParams(params)
        def publication = new Publication(params)

        assert publication.save() != null
        assert Publication.count() == 1

        params.id = publication.id

        controller.delete()

        assert Publication.count() == 0
        assert Publication.get(publication.id) == null
        assert response.redirectedUrl == '/publication/list'
    }
}
