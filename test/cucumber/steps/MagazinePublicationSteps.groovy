import pages.LoginPage
import pages.ArticlePages.ArticleShowPage
import rgms.publication.MagazinePublication
import steps.MagazinePublicationTestDataAndOperations

import static cucumber.api.groovy.EN.*

/**
 * Created by Gabriela on 10-Nov-14.
 */

Given(~'^The article has no magazine with title "([^"]*)"$') {String name ->
    magPublication = MagazinePublication.findByName(name)
    assert magazine == null
}

When(~'^I try to create a magazine publication as "([^"]*)" And publication number "([^"]*)" And publication place "([^"]*)" And publication year "([^"]*)"$')
        {String name, int number, String place, int year ->
            assert MagazinePublicationTestDataAndOperations.createMagPublication(name, number, "", place, "", year)
        }

Then(~'^the  "([^"]*)" is properly stored by the system$')
        {String title -> magazine = MagazinePublication.findByName(title)
            assert MagazinePublicationTestDataAndOperations.compatibleTo(magazine, title) }


//Given (~'^I want to add a magazine publication $'){ ->
//    Login()
//    at ArticleShowPage
//    to ArticleShowPage
//  page.select("Magazine Publication")
//  createNewMagazinePublication()
//  to MagazinePage
//  at MagazinePage
//    }
When (~'^I try to add a magazine publication in the magazine"([^"]*)"$') { String name, int number, String page, String place, String month, String year
    -> MagazinePublicationTestDataAndOperations.createMagPublication(name, number, page, place, month, year)}

Then(~'^the magazine publication is properly stored by the system$'){->
    to ArticleShowPage
    at ArticleShowPage
}

def Login(){
    to LoginPage
    at LoginPage
    page.fillLoginData("admin", "adminadmin")
}

def magPublicationCheckIfExists(String name, int number, String place, int year){
    mag = MagazinePublication.findByNameAndNumberAndPlaceAndYear(name, number, place, year);
    assert mag == null
}


