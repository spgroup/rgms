@i10n
Feature: XMLHTMLPDF

  Scenario: export member report to pdf
    Given I am at the Member list page
    When I select the "1" option at the Member list
    And I select the option Export to PDF at the Member show
    Then I can generate a PDF report about Member "1"

  Scenario: export member report to html
    Given I am at the Member list page
    When I select the "1" option at the Member list
    And I select the option Export to HTML at the Member show
    Then I can generate a HTML report about Member "1"

  Scenario: export member report to xml
    Given I am at the Member list page
    When I select the "1" option at the Member list
    And I select the option Export to XML at the Member show
    Then I can generate a XML report about that Member

  Scenario: export research group report to pdf
    Given I am at the Research Group list page
    When I select the "X" option at the Research Group list
    And I select the option Export to PDF at the Research Group show
    Then I can generate a PDF report about that Research Group

  Scenario: export research group report to html
    Given I am at the Research Group list page
    When I select the "X" option at the Research Group list
    And I select the option Export to HTML at the Research Group show
    Then I can generate a HTML report about that Research Group

  Scenario: export research group report to xml
    Given I am at the Research Group list page
    When I select the "X" option at the Research Group list
    And I select the option Export to XML at the Research Group show
    Then I can generate a XML report about that Research Group

