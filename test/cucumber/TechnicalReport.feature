@i9n
Feature: technical report
  As a member of a research group
  I want to add, remove and modify technical report I have published
  so that I can generate web pages and reports containing these technical reports

  Scenario: new invalid technical report
    Given    The system has no technical report entitled "NFL Languages System"
    When    I create the technical report "NFL Languages System" with file name "NLS.pdf"
    Then    The technical report "NFL Languages System" is not properly stored by the system

  Scenario: edit existing technical report with empty title
    Given    The system has an technical report entitled "NFL Languages System" with file name "NFL.pdf"
    When    I edit the technical report title from "NFL Languages System" to ""
    Then    The technical report "NFL Languages System" is not updated by the system

  Scenario: edit existing technical report with invalid title web
    Given  I am at the technical reports page and the technical report "Joe-E" is stored in the system with file name "Joee.pdf"
    When   I select to view "Joe-E" in resulting list and I change the technical report title to a blank one
    Then   I cannot select the "Alterar" option

  Scenario: remove existing technical report web
    Given  I am at the technical reports page and the technical report "Joe-E1" is stored in the system with file name "Joee1.pdf"
    When   I select to view the technical report "Joe-E1" in resulting list
    Then   the technical report details are showed and I can select the option to remove
    And    the technical report "Joe-E1" is properly removed by the system

#if ($Autofill)

  Scenario: new technical report web has user data already filled by default
    Given I am at the publications menu
    And I select the "Technical Report" option at the publications menu
    When I click on "New TechnicalReport" option at Technical Report list
    Then I see my user listed as an author member of technical report by default
#end
