package rgms.publication



import org.junit.*
import grails.test.mixin.*

@TestFor(BookChapterController)
@Mock(BookChapter)
class BookChapterControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/bookChapter/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.bookChapterInstanceList.size() == 0
        assert model.bookChapterInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.bookChapterInstance != null
    }

    void testSave() {
        controller.save()

        assert model.bookChapterInstance != null
        assert view == '/bookChapter/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/bookChapter/show/1'
        assert controller.flash.message != null
        assert BookChapter.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/bookChapter/list'

        populateValidParams(params)
        def bookChapter = new BookChapter(params)

        assert bookChapter.save() != null

        params.id = bookChapter.id

        def model = controller.show()

        assert model.bookChapterInstance == bookChapter
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/bookChapter/list'

        populateValidParams(params)
        def bookChapter = new BookChapter(params)

        assert bookChapter.save() != null

        params.id = bookChapter.id

        def model = controller.edit()

        assert model.bookChapterInstance == bookChapter
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/bookChapter/list'

        response.reset()

        populateValidParams(params)
        def bookChapter = new BookChapter(params)

        assert bookChapter.save() != null

        // test invalid parameters in update
        params.id = bookChapter.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/bookChapter/edit"
        assert model.bookChapterInstance != null

        bookChapter.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/bookChapter/show/$bookChapter.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        bookChapter.clearErrors()

        populateValidParams(params)
        params.id = bookChapter.id
        params.version = -1
        controller.update()

        assert view == "/bookChapter/edit"
        assert model.bookChapterInstance != null
        assert model.bookChapterInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/bookChapter/list'

        response.reset()

        populateValidParams(params)
        def bookChapter = new BookChapter(params)

        assert bookChapter.save() != null
        assert BookChapter.count() == 1

        params.id = bookChapter.id

        controller.delete()

        assert BookChapter.count() == 0
        assert BookChapter.get(bookChapter.id) == null
        assert response.redirectedUrl == '/bookChapter/list'
    }
}
