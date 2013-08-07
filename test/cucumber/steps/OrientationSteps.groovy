import pages.*
import rgms.member.Orientation
import rgms.publication.Periodico
import rgms.tool.FacebookTool
import rgms.tool.TwitterTool
import steps.TestDataAndOperations

import static cucumber.api.groovy.EN.*

// create
Given(~'^the system has no orientations entitled "([^"]*)"$') { String tituloTese ->
    // Express the Regexp above with the code you wish you had
    orientation = Orientation.findByTituloTese(tituloTese)
    assert orientation == null
}

When(~'^I create a orientation for the thesis "([^"]*)"$') { String tituloTese ->
    // Express the Regexp above with the code you wish you had
    TestDataAndOperations.createOrientation(tituloTese)
}

Then(~'^the orientation "([^"]*)" is properly stored by the system$') { String tituloTese ->
    // Express the Regexp above with the code you wish you had
    orientation = Orientation.findByTituloTese(tituloTese)
    assert TestDataAndOperations.compatibleTo(orientation, tituloTese)
}


//delete
Given(~'^the system has thesis entitled "([^"]*)" supervised for someone$') { String tituloTese ->

    TestDataAndOperations.createOrientation(tituloTese)
    orientation = TestDataAndOperations.findOrientationByTitle(tituloTese)
    assert orientation != null

}

When(~'^I delete the orientation for "([^"]*)"$') { String tituloTese ->
    TestDataAndOperations.removeOrientation(tituloTese)
}

Then(~'^the orientation for "([^"]*)" is properly removed by the system$') { String tituloTese ->
    orientation = Orientation.findByTituloTese(tituloTese)
    assert orientation == null

}

//create web
Given(~'^I am at the create orientation page$') {->

    Login()

    to OrientationCreatePage
    at OrientationCreatePage
}

When(~'^I fill the orientation title with "([^"]*)"$') { String title ->

    page.fillOrientationDetails(title)
    page.selectCreateOrientation()
}

Then(~'^I am on the orientation show page$') {->
    at OrientationShowPage
}

//edit web
Given(~'^I am at the orientation page and the orientation "([^"]*)" is stored in the system$') { String title ->

    Login()
    at PublicationsPage
    page.select("Orientation")
    at OrientationsPage
    page.selectNewOrientation()

    at OrientationCreatePage
    page.fillOrientationDetails()
    page.selectCreateOrientation()
    orientation = Orientation.findByTituloTese(title)
    assert orientation != null

    to OrientationsPage
    at OrientationsPage


}

When(~'^I select to view orientation "([^"]*)" in resulting list$') { String oldtitle ->

    at OrientationsPage
    page.selectViewOrientation(oldtitle)

    to OrientationShowPage
    at OrientationShowPage
    page.select()
}

When(~'^I change the orientation tituloTese to "([^"]*)"$') { String newtitle ->

    at OrientationEditPage
    page.edit(newtitle)
}

When(~'^I select the "([^"]*)" option$') { String option ->
    at OrientationEditPage
    page.select(option)
}

//FUNCOES AUXILIARES
// o problema de duplicação que este método resolve não foi identificado pela ferramenta de detecção de clones
def Login(){
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
}

