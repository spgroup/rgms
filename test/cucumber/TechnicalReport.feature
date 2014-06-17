@i9n
Feature: technical report
  As a member of a research group
  I want to add, remove and modify technical report I have published
  so that I can generate web pages and reports containing these technical reports

  Scenario: new valid technical report
    Given    The system has no technical report entitled "Evaluating Natural Languages System"
    When    I create the technical report "Evaluating Natural Languages System" with file name "EvaluateNLS.txt"
    Then    The technical report "Evaluating Natural Languages System" is properly stored by the system.

  Scenario: new invalid technical report (empty institution)
    Given    The system has no technical report entitled "Evaluating Natural Languages System"
    When    I create the technical report "Evaluating Natural Languages System" with file name "TCS-99.pdf" and empty institution
    Then    The technical report "Evaluating Natural Languages System" is not properly stored by the system

  Scenario: edit existing technical report with empty title
    Given    The system has an technical report entitled "NFL Languages System" with file name "NLS.pdf"
    When    I edit the technical report title from "NFL Languages System" to ""
    Then    The technical report "NFL Languages System" is not updated by the system

  Scenario: new valid technical report
    Given   I am at the technical reports page
    And    The system has no technical report entitled "TechRepo"
    When    I select the new technical report button
    And     I fill the technical report details with title "TechRepo" file name "TechRepo.pdf" and institution "UFPE"
    And    I select the save technical report button
    Then    The technical report "TechRepo" details page is shown

  Scenario: edit existing technical report with invalid title web
    Given  I am at the technical reports page
    And    the technical report "Joe-E" is stored in the system with file name "Joee.pdf"
    When   I select to view "Joe-E" in technical reports resulting list
    And    I select the option to edit
    And    I change the technical report title to a blank one
    And    I press the button alterar
    Then   The technical report is not saved by the system
    And    I remain at the technical report edit page

  @ignore
  Scenario: edit existing technical report with valid title, a valid filename and valid institution
    Given The system has an technical report entitled "TechRepo" with file name "TechRepo.pdf" and institution "UFPE"
    And   I am at the technical reports list page
    When  I select to view "TechRepo" in technical reports resulting list
    And   I select the option to edit
    And   I change the technical report title to "TC88" filename to "TCS-88.pdf" and institution to "UFRPE"
    And   I press the button alterar
    Then  The technical report "TC88" with filename "TCS-88.pdf" and institution "UFRPE" is properly updated.
    And   The technical report "TC88" details page is shown

  Scenario: remove existing technical report
    Given  I am at the technical reports page
    And    The system has an technical report entitled "Joe-E" with file name "Joee.pdf"
    When   I select to view the technical report "Joe-E" in resulting list
    And    I select the option to remove
    Then   The technical report "Joe-E" is properly removed by the system
    And    The system goes to the technical reports page

#if ($contextualInformation)
  Scenario: new technical report web has user data already filled by default
    Given I am at the publications menu
    And I select the "Technical Report" option at the publications menu
    When I click on "New TechnicalReport" option at Technical Report list
    Then I see my user listed as an author member of technical report by default
    And I see my school name as institution of technical report by default
#end

