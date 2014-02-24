package rgms.researchProject



import org.junit.*
import grails.test.mixin.*

@TestFor(ProjectMemberController)
@Mock(ProjectMember)
class ProjectMemberControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/projectMember/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.projectMemberInstanceList.size() == 0
        assert model.projectMemberInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.projectMemberInstance != null
    }

    void testSave() {
        controller.save()

        assert model.projectMemberInstance != null
        assert view == '/projectMember/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/projectMember/show/1'
        assert controller.flash.message != null
        assert ProjectMember.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/projectMember/list'

        populateValidParams(params)
        def projectMember = new ProjectMember(params)

        assert projectMember.save() != null

        params.id = projectMember.id

        def model = controller.show()

        assert model.projectMemberInstance == projectMember
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/projectMember/list'

        populateValidParams(params)
        def projectMember = new ProjectMember(params)

        assert projectMember.save() != null

        params.id = projectMember.id

        def model = controller.edit()

        assert model.projectMemberInstance == projectMember
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/projectMember/list'

        response.reset()

        populateValidParams(params)
        def projectMember = new ProjectMember(params)

        assert projectMember.save() != null

        // test invalid parameters in update
        params.id = projectMember.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/projectMember/edit"
        assert model.projectMemberInstance != null

        projectMember.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/projectMember/show/$projectMember.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        projectMember.clearErrors()

        populateValidParams(params)
        params.id = projectMember.id
        params.version = -1
        controller.update()

        assert view == "/projectMember/edit"
        assert model.projectMemberInstance != null
        assert model.projectMemberInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/projectMember/list'

        response.reset()

        populateValidParams(params)
        def projectMember = new ProjectMember(params)

        assert projectMember.save() != null
        assert ProjectMember.count() == 1

        params.id = projectMember.id

        controller.delete()

        assert ProjectMember.count() == 0
        assert ProjectMember.get(projectMember.id) == null
        assert response.redirectedUrl == '/projectMember/list'
    }
}
