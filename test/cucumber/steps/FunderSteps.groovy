import rgms.researchProject.Funder

/**
 * Created by Rubens on 24/02/14.
 */

//new Funder
Given(~'^the system has no funder with code "([^"]*)"$') { String code ->
    def funder = Funder.findByCode(code)
    assert funder == null
}
When(~'^I create a funder with code "([^"]*)"$') { String code ->
    (new Funder(code: code)).save()
}
Then(~'^the funder with code "([^"]*)" is properly stored by the system$') { String code ->
    def funder = Funder.findByCode(code)
    assert funder == null
}
