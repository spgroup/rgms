@i9n
Feature: Dissertation Tests
  As a member of a research group
  I want to add, remove and modify dissertations I have added

  Scenario: new dissertation without school
    Given the system has no dissertation entitled "Dissertacao Teste3"
    When I create the dissertation "Dissertacao Teste3" with file name "teste3Dissertacao.pdf" without school
    Then the system has no dissertation entitled "Dissertacao Teste3"

  Scenario: new dissertation
    Given the system has no dissertation entitled "Dissertacao Teste2"
    When I create the dissertation "Dissertacao Teste2" with file name "teste2.pdf" and school "UFPE"
    Then the dissertation "Dissertacao Teste2" is properly stored by the system
    
  Scenario: new dissertation duplicated
    Given the system has a dissertation entitled "Nova Dissertacao"
    When I create the dissertation "Dissertacao Teste3" with file name "teste6.txt" and school "UFPE"
    Then the dissertation "Dissertacao Teste3" is not stored twice

  Scenario: new dissertation without file
    Given I am at the publications menu
    When I select the "Dissertacao" option at the program menu
    And I select the new dissertation option at the dissertation page
    And I cant add the dissertation without a file
    Then the system has no dissertation entitled "Dissertacao Teste 1"

  Scenario: edit dissertation
    Given the system has a dissertation entitled "Nova Dissertacao edit"
    Given I am at the publications menu
    When I select the "Dissertacao" option at the program menu
    And I select "Nova Dissertacao edit" at the dissertation page
    And I click on edit
    And I edit the school to "UFPB"
    Then the school name is "UFPB"

  Scenario: delete dissertation
  	Given the system has a dissertation entitled "Nova Dissertacao delete"
    Given I am at the publications menu
    When I select the "Dissertacao" option at the program menu
    And I select "Nova Dissertacao delete" at the dissertation page
    And I delete it
    Then the system has no dissertation entitled "Nova Dissertacao delete"

  Scenario: upload dissertation without a file
    Given I am at the publications menu
    When I select the "Dissertacao" option at the program menu
    And I select the upload button at the dissertation page
    Then I'm still on dissertation page

  Scenario: upload dissertation with a file
    Given the system has some dissertation stored
    When I upload a new dissertation "C:\testelattes.xml"
    Then the system has more dissertations now

#if ($Autofill)
Scenario: create a new dissertation with user data already filled by default
    Given I am at the publications menu
    When I select the "Dissertacao" option at the program menu
    And I select the new dissertation option at the dissertation page
    Then I see my user listed as an author member of dissertation by default
    And I see my school name as school of dissertation by default
#end
