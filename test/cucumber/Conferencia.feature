@i9n
Feature: conference
  As a member of a research group
  I want to add, remove and modify conferences I have published
  so that I can generate web pages and reports containing these conferences

  Scenario: new conference
    Given the system has no conference entitled "IV Conference on Software Product Lines"
    When I create the conference "IV Conference on Software Product Lines" with file name "SPLC.pdf"
    Then the conference "IV Conference on Software Product Lines" is properly stored by the system

  Scenario: duplicate conference
    Given the conference "I International Conference on Software Engineering" is stored in the system with file name "IICSE-0.pdf"
    When I create the conference "I International Conference on Software Engineering" with file name "IICSE-0.pdf"
    Then the conference "I International Conference on Software Engineering" is not stored twice


  Scenario: remove conference
    Given the conference "IV Conference on Software Product Lines" is stored in the system with file name "IICSE-1.pdf"
    When I remove the conference "IV Conference on Software Product Lines"
    Then the conference "IV Conference on Software Product Lines" is properly removed by the system


  Scenario: new conference web
    Given I am at the publications
    When I select the conference option at the publications menu
    And I select the new conference option at the conference page

#    And I select the "new conference" option at the conference page

    Then I can fill the conference details

  Scenario: list conference web
    Given I am at the publications menu
    When I select the conference option at the publications menu
    Then a list of conferences stored by the system is displayed at the conference page

#if ($contextualInformation)

  Scenario: new conference web has user data filled by default
    Given I am at the publications
    When I select the conference option at the publications menu
    And I select the new conference option at the conference page

#    And I select the "new conference" option at the conference page

    Then I see my user listed as an author member of conference by default in the first position

#end

  Scenario: back to main menu web
    Given I am at the publications
    When I select the conference option at the publications menu

#   When I select the "conference" option at the publications menu

    And I select the home option at the conference page

#    And I select the "home" option at the conference page

    Then I am back at the publications and conferences menu

  Scenario: remove conference that does not exist
    Given the system has no conference entitled "IV Conference on Software Product Lines"
    When I try to remove the conference "IV Conference on Software Product Lines"
    Then nothing happens

  Scenario: remove and create the same conference
    Given the conference "V Conference on Software Product Lines" is stored in the system with file name "IICSE-10.pdf"
    When I remove the conference "V Conference on Software Product Lines"
    And I create the conference "V Conference on Software Product Lines" with file name "IICSE-12.pdf"
    Then the conference "V Conference on Software Product Lines" is properly stored by the system

  Scenario: remove conference web
    Given I am at the publications menu
    When I select the conference option at the publications menu

#   When I select the "conference" option at the publications menu

    And a list of conferences stored by the system is displayed at the conference page
    Then I can remove one conference

  Scenario: upload conference with a file
    Given the system has some conferences stored
    When I upload the conferences of "curriculo_conferencias.xml"
    Then the system has all the conferences of the xml file

  Scenario: upload conferences without a file
    Given I am at the publications menu
    When I select the "conference" option at the program menu
    And I select the upload button at the conference page
    Then I'm still on conference page
    And the conferences are not stored by the system

#if ($managingAuthors)

  Scenario: insert a member author into a new conference
    Given the member "John Smith" is registered
    And I am at the conferences page
    When I select the "new conference" option at the conference page
    And I click on "add author" at the create conference page
    Then I select the member "John Smith"
    And  I see the new member author "John Smith" at the last position of members authors

  Scenario: insert an member author that is already in the member authors list of a new conference
    Given the member "John Smith" is registered
    And I am at the conferences page
    When I select the "new conference" option at the conference page
    And I click on "add author" at the create conference page
    And I select the member "John Smith"
    And I click on "add author" at the create conference page
    And I select the member "John Smith"
    Then  I see the new member author "John Smith" only one time

  Scenario: insert a member author into an existing conference
    Given the conference "I International Conference on Software Engineering" is stored in the system
    And the member "Adam Sandman" is registered
    When I select the conference "I International Conference on Software Engineering"
    And I click on "add author" at the edit page
    Then I select the member "Adam Sandman"
    And I see the new member author "Adam Sandman" at the last position of members authors

  Scenario: remove a member author from an existing conference
    Given the conference "I International Conference on Software Engineering" is stored in the system
    And the member "Adam Sandman" is member author of the conference "I International Conference on Software Engineering"
    When I select the conference "I International Conference on Software Engineering" at the conference page
    And I select the author "Adam Sandman" at the member authors list in the show conference page
    Then I click on "remove" at the show member page
    And "Adam Sandman" is removed from the member authors list of conference "I International Conference on Software Engineering"

  Scenario: insert a member author into an existing conference
    Given the conference "I International Conference on Software Engineering" is stored in the system
    And the member "Adam Sandman" is member author of the conference "I International Conference on Software Engineering"
    When I select the conference "I International Conference on Software Engineering" at the conference page
    And I click on edit
    And I click on "add author" at the edit page
    Then I select the member "Adam Sandman"
    And the member authors list of the conference "I International Conference on Software Engineering" remain the same

#end