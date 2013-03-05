@i9n
Feature: TechnicalReport

  Scenario: new technical report
    Given the system has no technical report entitled "X"
    When I create the technical report "X" with file name "XFN.pdf"
    Then the technical report "X" is properly stored by the system

  Scenario: new invalid technical report
    Given the system has no technical report without a title
    When I create a technical report with no title and file name "XFN.pdf"
    Then the technical report will not be properly stored by the system because it's invalid

  Scenario: update report with no title or institution
    Given the system has a technical report "X" with institution "Y" stored
    When I update that technical report "X" with no title and no institution
    Then the technical report will not be updated by the system because it's invalid