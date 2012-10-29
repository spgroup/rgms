package rgms



import org.junit.*

import rgms.publication.Dissertacao;
import rgms.publication.DissertacaoController;
import grails.test.mixin.*

@TestFor(DissertacaoController)
@Mock(Dissertacao)
class DissertacaoControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/dissertacao/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.dissertacaoInstanceList.size() == 0
        assert model.dissertacaoInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.dissertacaoInstance != null
    }

    void testSave() {
        controller.save()

        assert model.dissertacaoInstance != null
        assert view == '/dissertacao/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/dissertacao/show/1'
        assert controller.flash.message != null
        assert Dissertacao.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/dissertacao/list'


        populateValidParams(params)
        def dissertacao = new Dissertacao(params)

        assert dissertacao.save() != null

        params.id = dissertacao.id

        def model = controller.show()

        assert model.dissertacaoInstance == dissertacao
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/dissertacao/list'


        populateValidParams(params)
        def dissertacao = new Dissertacao(params)

        assert dissertacao.save() != null

        params.id = dissertacao.id

        def model = controller.edit()

        assert model.dissertacaoInstance == dissertacao
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/dissertacao/list'

        response.reset()


        populateValidParams(params)
        def dissertacao = new Dissertacao(params)

        assert dissertacao.save() != null

        // test invalid parameters in update
        params.id = dissertacao.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/dissertacao/edit"
        assert model.dissertacaoInstance != null

        dissertacao.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/dissertacao/show/$dissertacao.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        dissertacao.clearErrors()

        populateValidParams(params)
        params.id = dissertacao.id
        params.version = -1
        controller.update()

        assert view == "/dissertacao/edit"
        assert model.dissertacaoInstance != null
        assert model.dissertacaoInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/dissertacao/list'

        response.reset()

        populateValidParams(params)
        def dissertacao = new Dissertacao(params)

        assert dissertacao.save() != null
        assert Dissertacao.count() == 1

        params.id = dissertacao.id

        controller.delete()

        assert Dissertacao.count() == 0
        assert Dissertacao.get(dissertacao.id) == null
        assert response.redirectedUrl == '/dissertacao/list'
    }
}
