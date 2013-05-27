@i9n
Feature: Add Ferramenta
  As a member of a research group
  I want to add, remove and modify ferramentas I have added


  Scenario: new ferramenta without publication date
    Given The system has a ferramenta entitled "Nova Ferramenta"
    When I create the ferramenta "Nova Ferramenta" without website
    Then The ferramenta "Nova Ferramenta" is not stored

  Scenario: duplicate ferramenta
    Given The system has a ferramenta entitled "Nova Ferramenta"
    When I create the ferramenta "Nova Ferramenta"
    Then The ferramenta "Nova Ferramenta" is not stored twice

  Scenario: edit ferramenta
    Given The system has a ferramenta entitled "Ferramenta1"
    When I change the website to "websiteteste"
    Then The edited ferramenta entitled "Ferramenta1" is properly stored in the system with the new website "websiteteste"

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
    Then I'm still on ferramenta page

  Scenario: upload dissertation with a file
    Given the system has some ferramenta stored
    When I upload a new ferramenta "C:\testelattes.xml"
    Then the system has more ferramenta now
