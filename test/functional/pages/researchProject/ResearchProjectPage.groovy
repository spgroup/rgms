package pages.researchProject

import org.apache.shiro.SecurityUtils
import org.apache.shiro.UnavailableSecurityManagerException
import pages.FormPage
import pages.GetPageTitle
import rgms.authentication.User
import rgms.researchProject.ResearchProject

/**
 * Created by Bruno Soares on 24/02/14.
 */
class ResearchProjectPage extends FormPage {
    static url = "researchProject/list"

    static at = {
        GetPageTitle gp = new GetPageTitle()
        def currentReseachProject = gp.msg("default.researchProject.label")
        def currentTitle = gp.msg("default.list.label", [currentReseachProject])
        title ==~ currentTitle
    }

    static content = {
        flashmessage {
            $("div", class: "message")
        }
    }

    def selectNewReseachGroup() {
        $('a', class: 'create').click()
    }

    def backToHome(){
        $('a', class:'home').click()
    }

    def submitXML(){
        $('input', class:'save').click()
    }

    def filter() {
        $("input", class: "filter").click();
    }

    def myProjects() {
        $("a", class: "my-research-projects").click();
    }

    def hasInvalidXMLSubmited(){
        GetPageTitle gp = new GetPageTitle()
        return gp.msg('default.xml.parserror.message') == $("div", class: "message", role: "status").text()
    }

    def selectReseachGroup(String name){
        $("a", text: name).click()
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize()
    }

    def fillFilterResearchProject(projectName) {
        $("form").filterProjectName = projectName;
    }

    def getResearchGroupRows() {
        def listDiv = $('div', id: 'list-researchProject')
        def researchGroupTable = (listDiv.find('table'))[0]
        def researchGroupRows = researchGroupTable.find('tbody').find('tr')
        researchGroupRows
    }

    def checkResearchGroupAtList(projectName) {
        def researchGroupRows = getResearchGroupRows()
        int size = researchGroupRows.size();
        def researchGroupColumns = researchGroupRows[size - 1].find('td')

        assert researchGroupColumns[0].text() == projectName
    }

    def checkResearchGroupDuplicatedAtList(projectName) {
        boolean check = true;
        def researchGroupRows = getResearchGroupRows()
        int size = researchGroupRows.size();
        if (size > 1) {
            def researchGroupColumns = researchGroupRows[size - 1].find('td')
            def researchGroupColumnsPreviews = researchGroupRows[size - 2].find('td')
            if ((researchGroupColumns[0].text() == projectName) &&
                    researchGroupColumnsPreviews[0].text() == projectName) {
                check = false
            }
        }
        assert check
    }

    def checkResearchGroupHasMeAsMember() {
        def User user;
        try {
            if(SecurityUtils.subject?.principal!=null) {
                user = User.findByUsername(SecurityUtils.subject.principal);
            }
        } catch(UnavailableSecurityManagerException e) {
            return false;
        }

        boolean check = true;
        def researchGroupRows = getResearchGroupRows()
        int size = researchGroupRows.size();

        if(size > 1) {
            for(int i=0; i<size; i++) {
                def researchGroupColumns = researchGroupRows[i].find('td');

                if(!researchGroupColumns[0].text().contains(user.getAuthor().getName())) {
                    check = false;
                }
            }
        }

        assert check;
    }

    def checkResearchGroupListFilteredByName(filter) {
        boolean check = true;
        def researchGroupRows = getResearchGroupRows()
        int size = researchGroupRows.size();

        if(size > 1) {
            for(int i=0; i<size; i++) {
                def researchGroupColumns = researchGroupRows[i].find('td');

                if(!researchGroupColumns[6].text().contains(filter)) {
                    check = false;
                }
            }
        }

        assert check;
    }

}
