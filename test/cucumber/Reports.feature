@i10n
Feature: Reports
  I want to generate PDF, HTML or XML report files of Members, Research Groups and News

  Scenario: export existent member report to html
    Given I am at the Member list page
    When I select the "1" option at the Member list
    And I can select the option Export to HTML at the Member show
    Then I can generate a HTML report about Member "1"

  Scenario: export existent member report to xml
    Given I am at the Member list page
    When I select the "1" option at the Member list
    And I can select the option Export to XML at the Member show
    Then I can generate a XML report about Member "1"

  Scenario: export recently created member report to pdf
    Given I am at the publications menu
    When I select the Novo Member option
    Then I fill the Member details with "John Smith" "JohnSmith" "JohnSmith@gmail.cin.ufpe.br" "UFPE" and create a new one
    Then I select the "2" option at the Member list
    And I select the option Export to PDF at the Member show
    Then I can generate a PDF report about Member "2"

  Scenario: export report to pdf of recently created research group
    Given I am at the publications menu
    When I select the "Research Group" option at the publications menu
    And I select the new research group option at research group list page
    Then I can fill the research group details with name "RGroup" and create a new one
    And I select the "RGroup" option at the Research Group list
    And I can select the option Export to PDF at the Research Group show
    And I can generate a PDF report about Research Group "RGroup"


  Scenario: export report to html of recently created research group
    Given I am at the publications menu
    When I select the "Research Group" option at the publications menu
    And I select the new research group option at research group list page
    Then I can fill the research group details with name "RGroup" and create a new one
    And I select the "RGroup" option at the Research Group list
    And I can select the option Export to HTML at the Research Group show
    And I can generate a HTML report about Research Group "RGroup"

  Scenario: export report to xml of recently created research group
    Given I am at the publications menu
    When I select the "Research Group" option at the publications menu
    And I select the new research group option at research group list page
    Then I can fill the research group details with name "RGroup" and create a new one
    And I select the "RGroup" option at the Research Group list
    And I can select the option Export to XML at the Research Group show
    And I can generate a XML report about Research Group "RGroup"

  Scenario: export existent member report to html and access bibtex from him
    Given I am at the Member list page
    When I select the "1" option at the Member list
    And I can select the option Export to HTML at the Member show
    And I can generate a HTML report about Member "1"
    Then I can select the Member "1" option
    And I can see the bibtex details

  Scenario: export recently created member report to html and access bibtex from him
    Given I am at the Publications page
    When I select the Conferencia option at the Publications menu
    And I select the new Conferencia option at the Conferencia page
    And I can fill the Conferencia details
    And I select the home option at the Conferencia page
    And I am at the Publications
    And I select the Novo Member option
    And I fill the Member details with "Eduardo" "eduardo" "j_ear@cin.ufpe.br" "UFPE" and create a new one
    And I select the "3" option at the Member list
    And I can select the option Export to HTML at the Member show
    And I can generate a HTML report about Member "3"
    Then I can select the Member "3" option
    And I can see the bibtex details

  Scenario: export existent member report to pdf
    Given I am at the Member list page
    When I select the "3" option at the Member list
    And I can select the option Export to PDF at the Member show
    Then I can generate a PDF report about Member "3"

#if ($news && $HTML)
  Scenario: export report link not enabled when there are no news stored in the system
    Given I am at the publications menu
    And the system has no stored news
    When I select the "News" option at the publications menu
    Then I can not select the option Export to HTML at the News list page

  Scenario: export report to HTML containing any stored news
    Given the system has a Research Group named "News Group" stored in the system
    And the system has a news stored with description "The first news"
    And I am at the initial RGMS page
    When I select the "News" option at the publications menu
    And I select the option Export to HTML at the News list page
    Then The system generate a HTML report with the news "The first news" in it
#end
