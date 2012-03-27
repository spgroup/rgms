package rgms



import org.junit.*
import grails.test.mixin.*

@TestFor(MembershipController)
@Mock(Membership)
class MembershipControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/membership/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.membershipInstanceList.size() == 0
        assert model.membershipInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.membershipInstance != null
    }

    void testSave() {
        controller.save()

        assert model.membershipInstance != null
        assert view == '/membership/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/membership/show/1'
        assert controller.flash.message != null
        assert Membership.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/membership/list'


        populateValidParams(params)
        def membership = new Membership(params)

        assert membership.save() != null

        params.id = membership.id

        def model = controller.show()

        assert model.membershipInstance == membership
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/membership/list'


        populateValidParams(params)
        def membership = new Membership(params)

        assert membership.save() != null

        params.id = membership.id

        def model = controller.edit()

        assert model.membershipInstance == membership
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/membership/list'

        response.reset()


        populateValidParams(params)
        def membership = new Membership(params)

        assert membership.save() != null

        // test invalid parameters in update
        params.id = membership.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/membership/edit"
        assert model.membershipInstance != null

        membership.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/membership/show/$membership.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        membership.clearErrors()

        populateValidParams(params)
        params.id = membership.id
        params.version = -1
        controller.update()

        assert view == "/membership/edit"
        assert model.membershipInstance != null
        assert model.membershipInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/membership/list'

        response.reset()

        populateValidParams(params)
        def membership = new Membership(params)

        assert membership.save() != null
        assert Membership.count() == 1

        params.id = membership.id

        controller.delete()

        assert Membership.count() == 0
        assert Membership.get(membership.id) == null
        assert response.redirectedUrl == '/membership/list'
    }
}
