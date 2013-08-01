@i10n
Feature: Reports
  I want to generate PDF, HTML or XML report files of Members and Research Groups

  Scenario: export existent member report to pdf
    Given I am at the Member list page
    When I select the "1" option at the Member list
    And I can select the option Export to PDF at the Member show
    Then I can generate a PDF report about Member "1"

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
    Given I am at the Publications page
	When I select the Novo Member option
	Then I fill the Member details with "John Smith" "JohnSmith" "JohnSmith@gmail.cin.ufpe.br" "UFPE" and create a new one
	Then I select the "2" option at the Member list
    And I can select the option Export to PDF at the Member show
    Then I can generate a PDF report about Member "2"

  Scenario: export report to pdf of recently created research group
    Given I am at the Publications page
    When i select the "Research Group" option at publications menu
    And i select the new research group option at research group list page
    Then i can fill the research group details with name "RGroup" and create a new one
    And I select the "RGroup" option at the Research Group list
    And I can select the option Export to PDF at the Research Group show
    And I can generate a PDF report about Research Group "RGroup"


  Scenario: export report to html of recently created research group
    Given I am at the Publications page
    When i select the "Research Group" option at publications menu
    And i select the new research group option at research group list page
    Then i can fill the research group details with name "RGroup" and create a new one
    And I select the "RGroup" option at the Research Group list
    And I can select the option Export to HTML at the Research Group show
    And I can generate a HTML report about Research Group "RGroup"

  Scenario: export report to xml of recently created research group
    Given I am at the Publications page
    When i select the "Research Group" option at publications menu
    And i select the new research group option at research group list page
    Then i can fill the research group details with name "RGroup" and create a new one
    And I select the "RGroup" option at the Research Group list
    And I can select the option Export to XML at the Research Group show
    And I can generate a XML report about Research Group "RGroup"


