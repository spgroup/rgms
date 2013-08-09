@i9n
Feature: Ferramenta
  As a member of a research group
  I want to add, remove and modify ferramentas I have added

  Scenario: new ferramenta without website
    Given the system has no ferramenta entitled "Target"
    When I create the ferramenta "Target" with file name "target.pdf" without its website
    Then the ferramenta "Target" is not stored

  Scenario: duplicate ferramenta
    Given the ferramenta "Emergo" is stored in the system with file name "emergo.pdf"
    When I create the ferramenta "Emergo" with file name "emergo.pdf"
    Then the ferramenta "Emergo" is not stored twice

  Scenario: edit existing ferramenta
    Given the system has a ferramenta entitled "CCFinder" with file name "ccfinder.pdf"
    When I edit the ferramenta title from "CCFinder" to "CCFinder REVIEWED"
    Then the ferramenta "CCFinder REVIEWED" is properly updated by the system

  Scenario: new ferramenta web
    Given I am at the publications menu
    When I select the "Ferramenta" option at the publications menu
    And I select the new ferramenta option at the ferramenta page
    Then I can create a ferramenta filling the details

  Scenario: new ferramenta without any information
    Given I am at the publications menu
    When I select the "Ferramenta" option at the program menu
    And I select the new ferramenta option at the ferramenta page
    And I select the create option at the ferramenta page
    Then The ferramenta is not stored

  Scenario: upload dissertation without a file
    Given I am at the publications menu
    When I select the "Ferramenta" option at the program menu
    And I select the upload button at the ferramenta page
    Then I am still on ferramenta page

  Scenario: upload dissertation with a file
    Given the system has some ferramenta stored
    When I upload a new ferramenta "testelattes.xml"
    Then the system has more ferramenta now

#if ($Autofill)

  Scenario: new ferramenta filled with user data by default
    Given I am at the publications menu
    When I select the "Ferramenta" option at the program menu
    And I select the new ferramenta option at the ferramenta page
    Then I see my user listed as an author member of ferramenta by default
#end

  @ignore
  Scenario: edit ferramenta
    Given I am at the publications menu
    When I select the "Ferramenta" option at the program menu
    And I select the new ferramenta option at the ferramenta page
    And I create a new ferramenta at ferramenta create page
    And I select the edit option at ferramenta show page
    And I can modify the name to "TestCucumber" at the edit ferramenta page
    Then I can see the new title "TestCucumber" at ferramenta show page

  Scenario: list ferramentas
    Given the ferramenta "Emergo" is stored in the system with file name "emergo.pdf"
    When I create the ferramenta "Target" with file name "target.pdf"
    Then The system list "Emergo" and "Target" ferramentas
