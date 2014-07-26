package pages.thesis

import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import pages.FormPage
import pages.GetPageTitle

class ThesisSearchPage extends FormPage {
    static url = "tese/search"

    static at = {
        GetPageTitle gp = new GetPageTitle()
        def currentTese = gp.msg("default.tese.label")
        def currentTitle = gp.msg("default.search.label", [currentTese])
        title ==~ currentTitle
    }

    def fillTitleInSearchDetails(title) {
        $("form").title = title
    }

    def fillThesisSearchDetails(title, initialDay, initialMonth, initialYear, endDay, endMonth, endYear, school) {
        fillTitleInSearchDetails(title)
        fillSomeDetaisInSearch(initialDay, initialMonth, initialYear, endDay, endMonth, endYear, school)
    }

    def fillSomeDetaisInSearch(initialDay, initialMonth, initialYear, endDay, endMonth, endYear, school)  {
        $("form").publicationInitialDate_day= initialDay
        $("form").publicationInitialDate_month = initialMonth
        $("form").publicationInitialDate_year = initialYear
        $("form").publicationEndDate_day= endDay
        $("form").publicationEndDate_month = endMonth
        $("form").publicationEndDate_year = endYear
        $("form").school = school
    }

    def searchTheses() {
        $("input", id:"search").click()
    }

    def enterText(input){
        WebElement titleField = $("input",id:"title").firstElement()
        titleField.sendKeys(input)
        sleep(1000)
    }

    def chooseOption(option){
        List<WebElement> items = $("a",class:"ui-corner-all").allElements().asList()
        def index = 0
        def found = false

        while(index < items.size() && !found){
            if(items.get(index).getText().equalsIgnoreCase(option)){
                found = true
            }else{
                index ++
            }
        }

        if(found){
            def builder = new Actions(driver)
            def actions =  builder.moveToElement(items.get(index)).click().build()
            actions.perform()
        }
    }
}
