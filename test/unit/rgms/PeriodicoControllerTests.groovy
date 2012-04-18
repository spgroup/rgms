package rgms



import org.junit.*
import grails.test.mixin.*

@TestFor(PeriodicoController)
@Mock(Periodico)
class PeriodicoControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/periodico/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.periodicoInstanceList.size() == 0
        assert model.periodicoInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.periodicoInstance != null
    }

    void testSave() {
        controller.save()

        assert model.periodicoInstance != null
        assert view == '/periodico/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/periodico/show/1'
        assert controller.flash.message != null
        assert Periodico.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/periodico/list'


        populateValidParams(params)
        def periodico = new Periodico(params)

        assert periodico.save() != null

        params.id = periodico.id

        def model = controller.show()

        assert model.periodicoInstance == periodico
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/periodico/list'


        populateValidParams(params)
        def periodico = new Periodico(params)

        assert periodico.save() != null

        params.id = periodico.id

        def model = controller.edit()

        assert model.periodicoInstance == periodico
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/periodico/list'

        response.reset()


        populateValidParams(params)
        def periodico = new Periodico(params)

        assert periodico.save() != null

        // test invalid parameters in update
        params.id = periodico.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/periodico/edit"
        assert model.periodicoInstance != null

        periodico.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/periodico/show/$periodico.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        periodico.clearErrors()

        populateValidParams(params)
        params.id = periodico.id
        params.version = -1
        controller.update()

        assert view == "/periodico/edit"
        assert model.periodicoInstance != null
        assert model.periodicoInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/periodico/list'

        response.reset()

        populateValidParams(params)
        def periodico = new Periodico(params)

        assert periodico.save() != null
        assert Periodico.count() == 1

        params.id = periodico.id

        controller.delete()

        assert Periodico.count() == 0
        assert Periodico.get(periodico.id) == null
        assert response.redirectedUrl == '/periodico/list'
    }
}
