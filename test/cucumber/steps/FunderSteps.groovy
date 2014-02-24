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
    funder = Funder.findByCode(code)
    checkIfFunderExists(code)
}

private void checkIfFunderExists(String code){
    funder = Funder.findByCode(code)
    assert funder != null
}