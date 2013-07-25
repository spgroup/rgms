@i9n
Feature: technical report
  As a member of a research group
  I want to add, remove and modify technical report I have published
  so that I can generate web pages and reports containing these technical reports

#Controller tests
  #created by Gabriel
  Scenario: new valid technical report
    Given 	The system has no technical report entitled "Evaluating Natural Languages System"
    When 	I create the technical report "Evaluating Natural Languages System" with file name "EvaluateNLS.txt"
    Then 	The technical report "Evaluating Natural Languages System" is properly stored by the system.

  #modified by Gabriel
  Scenario: new invalid technical report (empty institution)
    Given 	The system has no technical report entitled "Evaluating Natural Languages System"
    When 	I create the technical report "Evaluating Natural Languages System" with file name "TCS-99.pdf" and empty institution
    Then 	The technical report "Evaluating Natural Languages System" is not properly stored by the system

  Scenario: edit existing technical report with empty title
    Given    The system has an technical report entitled "NFL Languages System" with file name "NLS.pdf"
    When    I edit the technical report title from "NFL Languages System" to ""
    Then    The technical report "NFL Languages System" is not updated by the system

#GUI tests
  #created by Gabriel
  Scenario: new valid technical report
    Given   I am at the technical reports page
    And 	The system has no technical report entitled "Evaluating Natural Languages System"
    When 	I select the new technical report button
    And     I fill the technical report details with title "Evaluating Natural Languages System" file name "TCS-88.pdf" and institution "UFPE"
    And 	I select the save technical report button
    Then    The technical report "Evaluating Natural Languages System" details page is shown

  Scenario: edit existing technical report with invalid title web
    Given  I am at the technical reports page and the technical report "Joe-E" is stored in the system with file name "Joee.pdf"
    When   I select to view "Joe-E" in resulting list and I change the technical report title to a blank one
    Then   I cannot select the "Alterar" option

#modified by Gabriel
  Scenario: remove existing technical report
    Given  I am at the technical reports page
    And    The system has an technical report entitled "Joe-E" with file name "Joee.pdf"
    When   I select to view the technical report "Joe-E" in resulting list
    And    I select the option to remove
    Then   The technical report "Joe-E" is properly removed by the system
    And    The system go to the technical reports page

#if ($Autofill)

  Scenario: new technical report web has user data already filled by default
    Given I am at the publications menu
    And I select the "Technical Report" option at the publications menu
    When I click on "New TechnicalReport" option at Technical Report list
    Then I see my user listed as an author member of technical report by default
#end

