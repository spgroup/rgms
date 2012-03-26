package rgms



import org.junit.*
import grails.test.mixin.*

@TestFor(MemberController)
@Mock(Member)
class MemberControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/member/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.memberInstanceList.size() == 0
        assert model.memberInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.memberInstance != null
    }

    void testSave() {
        controller.save()

        assert model.memberInstance != null
        assert view == '/member/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/member/show/1'
        assert controller.flash.message != null
        assert Member.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/member/list'


        populateValidParams(params)
        def member = new Member(params)

        assert member.save() != null

        params.id = member.id

        def model = controller.show()

        assert model.memberInstance == member
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/member/list'


        populateValidParams(params)
        def member = new Member(params)

        assert member.save() != null

        params.id = member.id

        def model = controller.edit()

        assert model.memberInstance == member
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/member/list'

        response.reset()


        populateValidParams(params)
        def member = new Member(params)

        assert member.save() != null

        // test invalid parameters in update
        params.id = member.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/member/edit"
        assert model.memberInstance != null

        member.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/member/show/$member.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        member.clearErrors()

        populateValidParams(params)
        params.id = member.id
        params.version = -1
        controller.update()

        assert view == "/member/edit"
        assert model.memberInstance != null
        assert model.memberInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/member/list'

        response.reset()

        populateValidParams(params)
        def member = new Member(params)

        assert member.save() != null
        assert Member.count() == 1

        params.id = member.id

        controller.delete()

        assert Member.count() == 0
        assert Member.get(member.id) == null
        assert response.redirectedUrl == '/member/list'
    }
}
