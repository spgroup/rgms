@i9n
Feature: conference
  As a member of a research group
  I want to add, remove and modify conferences I have published
  so that I can generate web pages and reports containing these conferences

  @ignore
  Scenario: new conference
    Given the system has no conference entitled "IV Conference on Software Product Lines"
    When I create the conference "IV Conference on Software Product Lines" with file name "SPLC.pdf"
    Then the conference "IV Conference on Software Product Lines" is properly stored by the system

  @ignore
  Scenario: duplicate conference
    Given the conference "I International Conference on Software Engineering" is stored in the system with file name "IICSE-0.pdf"
    When I create the conference "I International Conference on Software Engineering" with file name "IICSE-0.pdf"
    Then the conference "I International Conference on Software Engineering" is not stored twice

  @ignore
  Scenario: remove conference
    Given the conference "IV Conference on Software Product Lines" is stored in the system with file name "IICSE-1.pdf"
    When I remove the conference "IV Conference on Software Product Lines"
    Then the conference "IV Conference on Software Product Lines" is properly removed by the system

  @ignore
  Scenario: new conference web
    Given I am at the publications
    When I select the conference option at the publications menu
    And I select the new conference option at the conference page

#    And I select the "new conference" option at the conference page

    Then I can fill the conference details

  @ignore
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
  @ignore
  Scenario: back to main menu web
    Given I am at the publications
    When I select the conference option at the publications menu

#   When I select the "conference" option at the publications menu

    And I select the home option at the conference page

#    And I select the "home" option at the conference page

    Then I am back at the publications and conferences menu

  @ignore
  Scenario: remove conference that does not exist
    Given the system has no conference entitled "IV Conference on Software Product Lines"
    When I try to remove the conference "IV Conference on Software Product Lines"
    Then nothing happens

  @ignore
  Scenario: remove and create the same conference
    Given the conference "V Conference on Software Product Lines" is stored in the system with file name "IICSE-10.pdf"
    When I remove the conference "V Conference on Software Product Lines"
    And I create the conference "V Conference on Software Product Lines" with file name "IICSE-12.pdf"
    Then the conference "V Conference on Software Product Lines" is properly stored by the system

  @ignore
  Scenario: remove conference web
    Given I am at the publications menu
    When I select the conference option at the publications menu

#   When I select the "conference" option at the publications menu

    And a list of conferences stored by the system is displayed at the conference page
    Then I can remove one conference

  @ignore
  Scenario: upload conference with a file
    Given the system has some conferences stored
    When I upload the conferences of "curriculo_conferencias.xml"
    Then the system has all the conferences of the xml file

  @ignore
  Scenario: upload conferences without a file
    Given I am at the publications menu
    When I select the "Conferencia" option at the program menu
    And I select the upload button at the conference page
    Then I'm still on conference page
    And the conferences are not stored by the system

#if ($managingAuthors)

  Scenario: insert a member author into a new conference
    Given the member "Rubens Lopes" is registered in the system
    When I fill the author name with "Rubens Lopes"
    And I click on "add author" at the create conference article page
    Then I see the new member author "Rubens Lopes" at the last position of members authors list

  @ignore
  Scenario: insert an already member author to a new conference
    Given the member "John Smith" is registered in the system
    When I click on "add author" at the create conference article page
    And I fill the author name with "John Smith"
    And I click on "add author" at the create conference article page
    Then the member "John Smith" is no more listed in possible authors list

  @ignore
  Scenario: insert a member author into an existing conference
    Given I am at the conferences page
    And the conference article entitled "On agent-based software engineering" is stored in the system
    And the member "Adam Sandman" is registered in the system
    When I select the conference article entitled "I International Conference on Software Engineering"
    And I fill the author name with "Adam Sandman"
    And I click on "add author" at the edit conference article page
    Then I see the new member author "Adam Sandman" at the last position of members authors list

  @ignore
  Scenario: insert an already member author to an existing conference
    Given I am at the conferences page
    And the conference article entitled "On agent-based software engineering" is stored in the system
    And the member "Adam Sandman" is member author of the conference article "On agent-based software engineering"
    When I select the conference article entitled "On agent-based software engineering"
    And I click on "edit" at the show conference article page
    And I click on "add author" at the edit conference article page
    Then  the member "Adam Sandman" is no more listed in possible authors list

  @ignore
  Scenario: remove a member author from an existing conference
    Given I am at the conferences page
    And the conference article entitled "On agent-based software engineering" is stored in the system
    And the member "Adam Sandman" is member author of the conference article "On agent-based software engineering"
    When I select the conference article entitled "On agent-based software engineering"
    And I select the author "Adam Sandman" at the member authors list in the show conference article page
    And I click on "remove" at the show member page
    Then the member "Adam Sandman" is not member author of the conference article "On agent-based software engineering"

#end