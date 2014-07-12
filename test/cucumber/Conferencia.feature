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
    Then a list of conferencias stored by the system is displayed at the conferencia page by ascending alphabetic order

#if ($contextualInformation)

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

  Scenario: upload conferencia with a file
    Given the system has some conferencias stored
    When I upload the conferencias of "curriculo_conferencias.xml"
    Then the system has all the conferencias of the xml file

  Scenario: upload conferencias without a file
    Given I am at the publications menu
    When I select the "Conferencia" option at the program menu
    And I select the upload button at the conferencia page
    Then I'm still on conferencia page
    And the conferencias are not stored by the system

 Scenario: edit existing conferencia
    Given the system has conferencia entitled "IV Conference on Software Product Lines" with file name "SPLC.pdf"
    When I change the conferencia title from "IV Conference on Software Product Lines" to "IV Conference on Software Product Lines REVIEWED"
    Then the conferencia "IV Conference on Software Product Lines" is properly stored by the system

Scenario: edit existing conferencia web
    Given I am at the conferencia page 
    And the conferencia entitled "IV Conference on Software Product Lines" is stored in the system with file name "SPLC.pdf"
    When I select to view the conferencia "IV Conference on Software Product Lines" in resulting list
    And I change the conferencia title to "IV Conference on Software Product Lines REVIEWED"
    And I select the "Alterar" option in Conferencia Registration Page
    And A success message is displayed
    Then I am at Conferencia page

Scenario: new invalid conferencia web (fields blank)
	Given I am at the conferencia registration page
	When I create the conferencia with some field blank
	Then the conferencia is not stored by the system because it is invalid
        And an error message is displayed

Scenario: list existing conferencia
    Given the system has conferencia entitled "IV Conference on Software Product Lines" with file name "SPLC.pdf"
    When I view the conferencia list
    Then the conferencia list contains "IV Conference on Software Product Lines"

Scenario: Order conferencia web by title
    Given I am at the conferencia page
    When I click on the column title at the conferencia list table
    Then a list of conferencias stored by the system is displayed at the conferencia page by ascending alphabetic order

Scenario: Order conferencia web by conferencia data
    Given I am at the conferencia page
    When I click on the column date at the conferencia list table
    Then a list of conferencias stored by the system is displayed at the conferencia page by publication ascending date order

Scenario: Search for conferencia
    Given the system has conferencia entitled "IV Conference on Software Product Lines"
    When I search for the conferencia entitled "IV Conference on Software Product Lines"
    Then theres no change in the data stored by the system

Scenario: Search for conferencia web
    Given I am at the conferencia page    
    And the system has conferencia dated "2007"
    When I write "2007" at the date field
    And I select the option Search for conferencia at the conferencia page
    Then a list of all conferencias containing the date "2007" will be presented in the conferencia screen
