package rgms



import org.junit.*
import grails.test.mixin.*

@TestFor(GroupController)
@Mock(Group)
class GroupControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/group/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.groupInstanceList.size() == 0
        assert model.groupInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.groupInstance != null
    }

    void testSave() {
        controller.save()

        assert model.groupInstance != null
        assert view == '/group/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/group/show/1'
        assert controller.flash.message != null
        assert Group.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/group/list'


        populateValidParams(params)
        def group = new Group(params)

        assert group.save() != null

        params.id = group.id

        def model = controller.show()

        assert model.groupInstance == group
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/group/list'


        populateValidParams(params)
        def group = new Group(params)

        assert group.save() != null

        params.id = group.id

        def model = controller.edit()

        assert model.groupInstance == group
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/group/list'

        response.reset()


        populateValidParams(params)
        def group = new Group(params)

        assert group.save() != null

        // test invalid parameters in update
        params.id = group.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/group/edit"
        assert model.groupInstance != null

        group.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/group/show/$group.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        group.clearErrors()

        populateValidParams(params)
        params.id = group.id
        params.version = -1
        controller.update()

        assert view == "/group/edit"
        assert model.groupInstance != null
        assert model.groupInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/group/list'

        response.reset()

        populateValidParams(params)
        def group = new Group(params)

        assert group.save() != null
        assert Group.count() == 1

        params.id = group.id

        controller.delete()

        assert Group.count() == 0
        assert Group.get(group.id) == null
        assert response.redirectedUrl == '/group/list'
    }
}
