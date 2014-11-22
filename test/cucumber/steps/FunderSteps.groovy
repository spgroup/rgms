import pages.LoginPage
import pages.funder.FunderCreatePage
import pages.funder.FunderPage
import rgms.researchProject.Funder
import steps.FunderTestDataAndOperations

import static cucumber.api.groovy.EN.*

/**
 * Created by Rubens on 24/02/14.
 */

//new Funder
Given(~'^the system has no funder with code "([^"]*)"$') { String code ->
    funder = Funder.findByCode(code)
    assert funder == null
}
When(~'^I create a funder with code "([^"]*)"$') { String code ->
     FunderTestDataAndOperations.createFunderWithCode(code)
}
Then(~'^the funder with code "([^"]*)" is properly stored by the system$') { String code ->
    checkIfFunderExists(code)
}

//remove funder
Given(~'^the system has funder with code "([^"]*)"$'){String code ->
    funder = Funder.findByCode(code)
    if(!funder){
        FunderTestDataAndOperations.createFunderWithCode(code)
        funder = Funder.findByCode(code)
    }
    assert funder != null
}
When(~'^I remove a funder with code "([^"]*)"$'){ String code ->
    funder = Funder.findByCode(code)
    funder.delete()
}


//duplicate funder
Then(~'^there is only one funder with code "([^"]*)" in the system$') { String code ->
    assert Funder.findAllByCode(code).size() == 1
}


//new funder web
Given(~'^I am at the create funder page$'){ ->
    goToFunderCreatePage()
}

//remove funder web
Given(~'^I am at the remove funder page$'){
    goToFunderCreatePage()
}
When(~'^I fill the funder code with "([^"]*)"$'){String code ->
    funder = Funder.findByCode(code)
    if(!funder){
        FunderTestDataAndOperations.createFunderWithCode(code)
        funder = Funder.findByCode(code)
    }
    assert funder != null
}
Then(~'^the funder with code "([^"]*)" is properly removed by the system$') { String code ->
    funder = Funder.findByCode(code)
    funder.delete()
}
//----------------------------------------

private void checkIfFunderExists(String code){
    funder = Funder.findByCode(code)
    assert funder != null
}

private void goToFunderCreatePage() {
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")

    to FunderPage
    at FunderPage
    page.selectNewFunder()
}

private void fillCodefield(String code){
    at FunderCreatePage
    page.fillFunderCode(code)
}

private void clickSave(){
    at FunderCreatePage
    page.selectSave()
}