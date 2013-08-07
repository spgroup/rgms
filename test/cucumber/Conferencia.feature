@i9n
Feature: conferencia
  As a member of a research group
  I want to add, remove and modify conferencias I have published
  so that I can generate web pages and reports containing these conferencias

  Scenario: new conferencia
    Given the system has no conferencia entitled "IV Conference on Software Product Lines"
    When I create the conferencia "IV Conference on Software Product Lines" with file name "SPLC.pdf"
    Then the conferencia "IV Conference on Software Product Lines" is properly stored by the system

  Scenario: duplicate conferencia
    Given the conferencia "I International Conference on Software Engineering" is stored in the system with file name "IICSE-0.pdf"
    When I create the conferencia "I International Conference on Software Engineering" with file name "IICSE-0.pdf"
    Then the conferencia "I International Conference on Software Engineering" is not stored twice


  Scenario: remove conferencia
    Given the conferencia "IV Conference on Software Product Lines" is stored in the system with file name "IICSE-1.pdf"
    When I remove the conferencia "IV Conference on Software Product Lines"
    Then the conferencia "IV Conference on Software Product Lines" is properly removed by the system


  Scenario: new conferencia web
    Given I am at the publications
    When I select the conferencia option at the publications menu
    And I select the new conferencia option at the conferencia page
    Then I can fill the conferencia details

  Scenario: list conferencia web
    Given I am at the publications menu
    When I select the conferencia option at the publications menu
    Then a list of conferencias stored by the system is displayed at the conferencia page

#if ($Autofill)

  Scenario: new conferencia web has user data filled by default
    Given I am at the publications
    When I select the conferencia option at the publications menu
    And I select the new conferencia option at the conferencia page
    Then I see my user listed as an author member of conferencia by default

#end


  Scenario: back to main menu web
    Given I am at the publications
    When I select the conferencia option at the publications menu
    And I select the home option at the conferencia page
    Then I am back at the publications and conferencias menu

  Scenario: remove conferencia that does not exist
    Given the system has no conferencia entitled "IV Conference on Software Product Lines"
    When I try to remove the conferencia "IV Conference on Software Product Lines"
    Then nothing happens

  Scenario: remove and create the same conferencia
    Given the conferencia "V Conference on Software Product Lines" is stored in the system with file name "IICSE-10.pdf"
    When I remove the conferencia "V Conference on Software Product Lines"
    And I create the conferencia "V Conference on Software Product Lines" with file name "IICSE-12.pdf"
    Then the conferencia "V Conference on Software Product Lines" is properly stored by the system

  Scenario: remove conferencia web
    Given I am at the publications menu
    When I select the conferencia option at the publications menu
    And a list of conferencias stored by the system is displayed at the conferencia page
    Then I can remove one conferencia