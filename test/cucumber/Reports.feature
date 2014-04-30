@i10n
Feature: Reports
  I want to generate PDF, HTML or XML report files of Members, Research Groups and News

  Scenario: export existent member report to html
    Given I am at the Member list page
    When I select the "view details" option at the Member list
    And I select the option Export to HTML at the Member view
    Then I can generate a HTML report about the selected Member
    And View a HTML file with all memeber's personal details

  Scenario: export existent member report to xml
    Given I am at the Member list page
    When I select the "view details" option at the Member list
    And I can select the option Export to XML at the Member view
    Then I can generate a XML report about the selected Member
    And View a XML file with all memeber's personal details

  Scenario: export recently created member report to pdf
    Given I am at the publications menu
    When I select the Novo Member option
    Then I fill the Member details with "John Smith" "JohnSmith" "JohnSmith@gmail.cin.ufpe.br" "UFPE" and create a new one
    Then I select the "2" option at the Member list
    And I can select the option Export to PDF at the Member show
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
    When I select the "view details" option at the Member list
    And I can select the option Export to PDF at the Member view
    Then I can generate a PDF report about the selected Member details
    And View a PDF file with all memeber's personal details

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

#if ($multipleMembers)
  Scenario: export multiple members reports to pdf
    Given I am at the Member list page
    When I select members checkboxes' at the Member list
    And I select the option "Export all to PDF" at the Members list
    Then I can generate a PDF report about all selected Members

  Scenario: export multiple members reports to XML
    Given I am at the Member list page
    When I select members checkboxes' at the Member list
    And I select the option "Export all to XML" at the Members list
    Then I can generate a XML report about all selected Members

  Scenario: export multiple members reports to HTML
    Given I am at the Member list page
    When I select members checkboxes' at the Member list
    And I select the option "Export all to HTML" at the Members list
    Then I can generate a HTML report about all selected Members

  Scenario: export multiple members link to PDF not enabled when there are no member selected
    Given I am at the member list page
    And the system has no checked member
    Then I can not select the option Export to PDF at the member list page

  Scenario: export multiple members to XML link not enabled when there are no member selected
    Given I am at the member list page
    And the system has no checked member
    Then I can not select the option Export to XML at the member list page

  Scenario: export multiple members to HTML link not enabled when there are no member selected
    Given I am at the member list page
    And the system has no checked member
    Then I can not select the option Export to HTML at the member list page

#end

#if ($newsExport)
  Scenario: export existent news report to pdf
    Given I am at the News list page
    When I select the description option at the News list
    And I can select the option Export to PDF at the News view
    Then I can generate a PDF report about the selected News

  Scenario: export existent news report to XML
    Given I am at the News list page
    When I select the description option at the News list
    And I can select the option Export to XML at the News view
    Then I can generate a XML report about the selected News

  Scenario: export existent news report to HTML
    Given I am at the News list page
    When I select the description option at the News list
    And I can select the option Export to HTML at the News view
    Then I can generate a HTML report about the selected News
#end

#if ($controller_ReportGeneration)
  Scenario: Generate member details report in PDF
    Given the system has some member details
    When I request to export to PDF those details
    Then a formatted PDF file containing the member details named "User name - report.pdf" will be generated and stored in the computer

  Scenario: Generate member details report in HTML
    Given the system has some member details
    When I request to export to HTML those details
    Then a formatted HTML file containing the member details will be generated and displayed on the browser

  Scenario: Generate member details report in XML
    Given the system has some member details
    When I request to export to XML those details
    Then a formatted XML containing the member details in appropriate tags named "User name - report.xml" will be generated and stored in the computer
#end