package steps

import rgms.member.ResearchGroup
import rgms.member.ResearchGroupController

/**
 * Created with IntelliJ IDEA.
 * User: Alberto Junior
 * Date: 27/08/13
 * Time: 21:43
 * To change this template use File | Settings | File Templates.
 */
class ResearchGroupTestDataAndOperations {

    static public void createResearchGroup(String name, description) {
        def researchGroupController = new ResearchGroupController()
        researchGroupController.params << [name: name] << [description: description]
        researchGroupController.request.setContent(new byte[1000]) // Could also vary the request content.
        researchGroupController.create()
        researchGroupController.save()
        researchGroupController.response.reset()
    }

    static public void editResearchGroup(def researchGroup, String newName, String newDescription) {
        def researchGroupController = new ResearchGroupController()
        researchGroupController.params << [name: newName] << [description: newDescription] << [id: researchGroup.getId()]
        researchGroupController.request.setContent(new byte[1000]) // Could also vary the request content.
        researchGroupController.edit()
        researchGroupController.save()
        researchGroupController.response.reset()
    }

    //#if($researchGroupHierarchy)
    static public void editResearchGroupChildOf(ResearchGroup researchGroup, ResearchGroup researchGroupParent) {
        def researchGroupController = new ResearchGroupController()
        researchGroupController.params << [name: researchGroup.name]
        researchGroupController.params << [description: researchGroup.description]
        researchGroupController.params << [id: researchGroup.id]
        researchGroupController.params << [childOf: researchGroupParent]
        researchGroupController.request.setContent(new byte[1000]) // Could also vary the request content.

        try {
            researchGroupController.update()
        } catch (Exception e) {}
    }
    //#end

    static public void deleteResearchGroup(def researchGroup) {
        def researchGroupController = new ResearchGroupController()
        researchGroupController.params << [id: researchGroup.getId()]
        researchGroupController.request.setContent(new byte[1000]) // Could also vary the request content.
        researchGroupController.delete()
        researchGroupController.response.reset()
    }
}
