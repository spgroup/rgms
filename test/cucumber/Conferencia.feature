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

  @ignore
  Scenario: list conferencia web
    Given I am at the publications menu
    When I select the conferencia option at the publications menu
    Then a list of conferencias stored by the system is displayed at the conferencia page by alphabetic order

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

  @ignore
  Scenario: edit existing conference
    Given the system has conference entitled "IV Conference on Software Product Lines" with file name "SPLC.pdf"
    When I change the conference file from "SPLC.pdf" to "SPLC2.pdf"
    Then the conference "IV Conference on Software Product Lines" is properly updated by the system

  @ignore
  Scenario: edit existing conference web
    Given I am at the conference page 
    And the conference "IV Conference on Software Product Lines" is stored in the system with file name "SPLC.pdf"
    When I select to view "IV Conference on Software Product Lines" in resulting list
    And I change the conference file to "SPLC2.pdf"
    And I select the "Alterar" option in Conference Registration Page
    And A success message is displayed
    Then I am at Conference page

  @ignore
  Scenario: new invalid conference web (fields blank)
	Given I am at the conference registration page
	When I create the conference with some field blank
	Then the conference is not stored by the system because it is invalid
        And an error menssage is displayed

  @ignore
  Scenario: list existing conference
    Given the system has conference entitled "IV Conference on Software Product Lines" with file name "SPLC.pdf"
    When I view the conference list
    Then my conference list contains "IV Conference on Software Product Lines"

  @ignore
  Scenario: Order conference web by title
    Given I am at the conference page
    When I click on the column "title" at the conference list table
    Then a list of conferences stored by the system is displayed at the conference page by ascending alphabetic order

  @ignore
  Scenario: Order conference web by conference data
    Given I am at the conference page
    When I click on the column "Date" at the conference list table
    Then a list of conferences stored by the system is displayed at the conference page by publication ascending date order

  @ignore
  Scenario: Order  conference web by research line
    Given I am at the conference page
    When I click on the column "Research Line" at the conference list table
    Then a list of conferences stored by the system is displayed at the conference page by ascending alphabetic order

  @ignore
  Scenario: Go to search page
    Given I am at the conference page
    When I select the Search Conference option
    Then I am at the search conference page

  @ignore
  Scenario: Search for conference
    Given the system has conference entitled "IV Conference on Software Product Lines"
    When I search for the conferencia entitled "IV Conference on Software Product Lines"
    Then theres no change in the data stored by the system.

  @ignore
  Scenario: Search for conference web by date
    Given I am at the Seach Conference page
    And  the system has conference dated "2007"
    When I write "2007" at the date field
    And I select the option Serach for Conference at the conference page
    Then a list of all conferences containing that date will be presented in the conference screen

# voces podem criar cenários para ordenar a lista de conferencia, filtrar a lista,  verificar se alguns campos podem ser opcionais, etc.

  Scenario: download a conference file
    Given I am at the Seach Conference page
    And the system has conference entitled "IV Conference on Software Product Lines" with file name "SPLC.pdf"
    When I select the download button
    Then I can download the file named "SPLC.pdf"