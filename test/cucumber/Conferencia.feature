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

#if ($contextualInformation)

  Scenario: new conferencia web has user data filled by default
    Given I am at the publications
    When I select the conferencia option at the publications menu
    And I select the new conferencia option at the conferencia page
    Then I see my user listed as an author member of conferencia by default in the first position

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

#if ($managingAuthors)

  Scenario: new conferencia web add name of authors
    Given I am at the publications
    When I select the conferencia option at the publications menu
    And I select the new conferencia option at the conferencia page
    Then I can add an author by clicking in add author
    And I can fill the author name

  Scenario: new conferencia registering more than one author
    Given I am at the publications
    When I select the new conferencia option at the conferencia menu
    And I have filled more than one author
    Then the conferencia will be stored by the system
    And all authors will be stored in the same order they were registered

  Scenario: add an author in an existing conferencia
    Given I am at the publications
    When I select an existing conferencia
    Then I can add an author by clicking in add author
    And all authors will be stored in the same order they were registered

  Scenario: remove an author in an existing conferencia
    Given I am at the publications
    When I select an existing conferencia
    And I select one of authors
    Then I can remove the selected author by clicking in remove author
    And the conferencia will be updated by the system keeping the order of remaining authors

#end