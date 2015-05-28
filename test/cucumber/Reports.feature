@i10n
Feature: Reports
  I want to generate PDF, HTML or XML report files of Members, Research Groups and News



  Scenario: export existent member report to xml
    Given I am at the Member list page
    When I select the "1" option at the Member list
    And I can select the option Export to XML at the Member show
    Then I can generate a XML report about Member "1"

  Scenario: export existent member report to pdf
    Given I am at the Member list page
    When I select the "1" option at the Member list
    And I can select the option Export to PDF at the Member show
    Then I can generate a PDF report about Member "1"

  Scenario: create a new Member
    Given I am at the Member list page
    When I select the "New Member" option
    And I can fill the Member "Name" with "John Smith"
    And I can fill the Member "Username" with "JohnSmith"
    And I can fill the Member "Email" with "JohnSmith@gmail.cin.ufpe.br"
    And I can fill the Member "University" "UFPE"
    And I can select "Criar" option
    Then I can see the new user at the Member List

  Scenario: missing field error when creating a new Member
    Given I am at the Member list page
    When I select the Novo Member option
    And I dont fill a field with * symbol
    And I can select Criar option
    Then I can see a error message

  Scenario: invalid value in field error when creating a new Member
    Given I am at the Member list page
    When I select the "Novo Member" option
    And I can fill a field with an invalid value "&%(#@"
    And I select "Create" option
    Then I can see a error message

  Scenario: export recently created member report to pdf
    Given I am at the Member list page
    When I can create a new Member named "João Paulo Silva"
    Then I can export to PDF the existent member named "João Paulo Silva"

  Scenario: export recently created member report to xml
    Given I am at the Member list page
    When I can create a new Member named "João Paulo Silva"
    Then I can export to XML the existent member named "João Paulo Silva"

  Scenario: export recently created member report to html
    Given I am at the Member list page
    When I can create a new Member named "João Paulo Silva"
    Then I can export to HTML the existent member named "João Paulo Silva"

  Scenario: create a new research group
    Given I am at the publications menu page
    When I select the "Research Group" option at the publications menu page
    And I select the "New Research Group" at research group list page
    Then I can fill the field "Nome" with value "Grupo1"
    And I can fill the field "Twitter" with value "@Grupo1"
    And I can fill the field "Descrição" with value "Grupo de pesquisa 1"
    And I can fill the field "Sigla" with value "G1"
    And I select a member "1" at member list
    And I select "Criar" option
    Then I should see the new research group named "Grupo1" in Research Group list


  Scenario: missing field error when creating a research group
    Given I am at the publications menu
    When I select the "Research Group" option at the publications menu
    And I select the new research group option at research group list page
    And I dont fill a field with "*" symbol
    And I can select "Criar" option
    Then I can see a error message


  Scenario: invalid value in field error when creating a research group
    Given I am at the publications menu
    When I select the "Research Group" option at the publications menu
    And I select the new research group option at research group list page
    And I can fill a field with an invalid value
    And I can select "Criar" option
    Then I can see a error message

  Scenario: export report to pdf of existent research group
    Given I am at the publications menu
    And I select the "RGroup" option at the Research Group list
    And I can select the option Export to PDF at the Research Group show page
    Then I can generate a PDF report about Research Group "RGroup"

  Scenario: export report to xml of existent research group
    Given I am at the publications menu
    And I select the "RGroup" option at the Research Group list
    And I can select the option Export to PDF at the Research Group show page
    Then I can generate a XML report about Research Group "RGroup"

  Scenario: export report to html of existent research group
    Given I am at the publications menu
    And I select the "RGroup" option at the Research Group list
    And I can select the option Export to PDF at the Research Group show page
    Then I can generate a HMTL report about Research Group "RGroup"

  Scenario: export report to pdf of recently created research group
    Given I am at the publications menu
    When I create a new Research Group named "RGroup"
    Then I can generate a PDF report about existent Research Group "RGroup"


  Scenario: export report to html of recently created research group
    Given I am at the publications menu
    When I create a new Research Group named "RGroup"
    Then I can generate a HTML report about existent Research Group "RGroup"

  Scenario: export report to xml of recently created research group
    Given I am at the publications menu
    When I create a new Research Group named "RGroup"
    Then I can generate a XML report about existent Research Group "RGroup"

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



  Scenario: export a existent news report to html
    Given I am in News list page
    When I select "RGMSNews" option at the News list
    And I select the option "export to html" at the News show
    Then I export a html report about News "RGMSNews"

  Scenario: export a existent research group report to pdf
    Given I am in research group list page
    When I select "RGMSGroup" option at the research group list
    And I select the option "export to PDF" at the resourch group show
    Then I export a PDF report about research group "RGMSGroup"

  Scenario: export a existent news report to PDF
    Given I am in News list page
    When I select "RGMSNews" option at the News list
    And I select the option "export to PDF" at the News show
    Then I export a PDF report about News "RGMSNews"

  Scenario: export a existent research group report to xml
    Given I am in research group list page
    When I select "RGMSGroup" option at the resourch group list
    And I select the option "export to XML" at the research group show
    Then I export a XML report about resourch group "RGMSGroup"

  Scenario: export a existent news report to xml
    Given I am in News list page
    When I select "RGMSNews" option at the News list
    And I select the option "export to XML" at the News show
    Then I export a XML report about News "RGMSNews"

  Scenario: export report to html link not enable when there is not research group created
    Given I am at "publications" menu
    And there is not research group created
    When I select the "Research Group" option
    Then I view that the research group list is empty
    And I can not select the option "Export to html"

  Scenario: export report to html link not enable when there is not members report created
    Given I am at "publications" menu
    And there is not member created
    When I select the "Member" option
    Then I view that the member list is empty
    And I can not select the option "Export to html"


    #iniciado aqui

  Scenario: export research group report to HTML
    Given I have a ResearchGroup registered in the system
    And I am at ResearchGroupShowPage
    When I select HTML Export option
    Then I go to ResearchGroupReportHTMLPage


  Scenario: export research group report to XML
    Given I have a ResearchGroup registered in the system
    And I am at ResearchGroupShowPage
    When I select XML Export option
    Then I receive a download link to XML Report

  Scenario: export research group report to PDF
    Given I have a ResearchGroup registered in the system
    And I am at ResearchGroupShowPage
    When I select PDF Export option
    Then I receive a download link to PDF Report

  Scenario: Include basic ResearchGroup information in ResearchGroup Report
    Given I have a ResearchGroup registered in the system
    When I go to ResearchGroupReportHTMLPage
    Then I can see the ResearchGroup basic information on page

