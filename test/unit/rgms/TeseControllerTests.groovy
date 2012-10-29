package rgms



import org.junit.*

import rgms.publication.Tese;
import rgms.publication.TeseController;
import grails.test.mixin.*

@TestFor(TeseController)
@Mock(Tese)
class TeseControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/tese/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.teseInstanceList.size() == 0
        assert model.teseInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.teseInstance != null
    }

    void testSave() {
        controller.save()

        assert model.teseInstance != null
        assert view == '/tese/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/tese/show/1'
        assert controller.flash.message != null
        assert Tese.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/tese/list'


        populateValidParams(params)
        def tese = new Tese(params)

        assert tese.save() != null

        params.id = tese.id

        def model = controller.show()

        assert model.teseInstance == tese
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/tese/list'


        populateValidParams(params)
        def tese = new Tese(params)

        assert tese.save() != null

        params.id = tese.id

        def model = controller.edit()

        assert model.teseInstance == tese
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/tese/list'

        response.reset()


        populateValidParams(params)
        def tese = new Tese(params)

        assert tese.save() != null

        // test invalid parameters in update
        params.id = tese.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/tese/edit"
        assert model.teseInstance != null

        tese.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/tese/show/$tese.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        tese.clearErrors()

        populateValidParams(params)
        params.id = tese.id
        params.version = -1
        controller.update()

        assert view == "/tese/edit"
        assert model.teseInstance != null
        assert model.teseInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/tese/list'

        response.reset()

        populateValidParams(params)
        def tese = new Tese(params)

        assert tese.save() != null
        assert Tese.count() == 1

        params.id = tese.id

        controller.delete()

        assert Tese.count() == 0
        assert Tese.get(tese.id) == null
        assert response.redirectedUrl == '/tese/list'
    }
}
