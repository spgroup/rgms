@i10n
Feature: Reports
  I want to generate PDF, HTML or XML report files of Members and Research Groups

  Scenario: export member report to pdf
    Given I am at the Member list page
    When I select the "1" option at the Member list
    And I can select the option Export to PDF at the Member show
    Then I can generate a PDF report about Member "1"

  Scenario: export member report to html
    Given I am at the Member list page
    When I select the "1" option at the Member list
    And I can select the option Export to HTML at the Member show
    Then I can generate a HTML report about Member "1"

  Scenario: export member report to xml
    Given I am at the Member list page
    When I select the "1" option at the Member list
    And I can select the option Export to XML at the Member show
    Then I can generate a XML report about Member "1"

  Scenario: export research group report to pdf
    Given I am at the Research Group list page and I select the "testehugo1" group
    When I select the "testehugo1" option at the Research Group list
    And I can select the option Export to PDF at the Research Group show
    Then I can generate a PDF report about Research Group "testehugo1"

  Scenario: export research group report to html
    Given I am at the Research Group list page and I select the "testehugo12" group
    When I select the "testehugo12" option at the Research Group list
    And I can select the option Export to HTML at the Research Group show
    Then I can generate a HTML report about Research Group "testehugo12"

  Scenario: export research group report to xml
    Given I am at the Research Group list page and I select the "testehugo123" group
    When I select the "testehugo123" option at the Research Group list
    And I can select the option Export to XML at the Research Group show
    Then I can generate a XML report about Research Group "testehugo123"

