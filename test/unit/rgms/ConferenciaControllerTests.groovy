package rgms



import org.junit.*
import grails.test.mixin.*

@TestFor(ConferenciaController)
@Mock(Conferencia)
class ConferenciaControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/conferencia/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.conferenciaInstanceList.size() == 0
        assert model.conferenciaInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.conferenciaInstance != null
    }

    void testSave() {
        controller.save()

        assert model.conferenciaInstance != null
        assert view == '/conferencia/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/conferencia/show/1'
        assert controller.flash.message != null
        assert Conferencia.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/conferencia/list'


        populateValidParams(params)
        def conferencia = new Conferencia(params)

        assert conferencia.save() != null

        params.id = conferencia.id

        def model = controller.show()

        assert model.conferenciaInstance == conferencia
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/conferencia/list'


        populateValidParams(params)
        def conferencia = new Conferencia(params)

        assert conferencia.save() != null

        params.id = conferencia.id

        def model = controller.edit()

        assert model.conferenciaInstance == conferencia
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/conferencia/list'

        response.reset()


        populateValidParams(params)
        def conferencia = new Conferencia(params)

        assert conferencia.save() != null

        // test invalid parameters in update
        params.id = conferencia.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/conferencia/edit"
        assert model.conferenciaInstance != null

        conferencia.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/conferencia/show/$conferencia.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        conferencia.clearErrors()

        populateValidParams(params)
        params.id = conferencia.id
        params.version = -1
        controller.update()

        assert view == "/conferencia/edit"
        assert model.conferenciaInstance != null
        assert model.conferenciaInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/conferencia/list'

        response.reset()

        populateValidParams(params)
        def conferencia = new Conferencia(params)

        assert conferencia.save() != null
        assert Conferencia.count() == 1

        params.id = conferencia.id

        controller.delete()

        assert Conferencia.count() == 0
        assert Conferencia.get(conferencia.id) == null
        assert response.redirectedUrl == '/conferencia/list'
    }
}
