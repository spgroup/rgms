@i9n
Feature: Dissertation Tests
  As a member of a research group
  I want to add, remove and modify dissertations I have added
    
    
Scenario: new dissertation duplicated
    Given the system has a dissertation entitled "Nova Dissertacao"
    When I create the dissertation "Nova Dissertacao" with file name "teste6Dissertacao.pdf" and school "UFPE"
    Then the dissertation "Nova Dissertacao" is not stored twice
    
Scenario: new dissertation without school
    Given the system has no dissertation entitled "Dissertacao Teste3"
    When I create the dissertation "Dissertacao Teste3" with file name "teste3Dissertacao.pdf" without school
    Then the system has no dissertation entitled "Dissertacao Teste3"

Scenario: new dissertation
    Given the system has no dissertation entitled "Dissertacao Teste2"
    When I create the dissertation "Dissertacao Teste2" with file name "teste2.pdf" and school "UFPE"
    Then the dissertation "Dissertacao Teste2" is properly stored by the system

Scenario: new dissertation without file
    Given I am at the publications menu
    When I select the "Dissertacao" option at the program menu
    And I select the new dissertation option at the dissertation page
    And I cant add the dissertation without a file
    Then the system has no dissertation entitled "Dissertacao Teste 1"

Scenario: edit dissertation
    Given I am at the publications menu
    When I select the "Dissertacao" option at the program menu
    And I select "Nova Dissertacao" at the dissertation page
    And I click on edit
    And I edit the school to "UFPB"
    Then the school name is "UFPB"

Scenario: delete dissertation
    Given I am at the publications menu
    When I select the "Dissertacao" option at the program menu
    And I select "Dissertacao Teste4" at the dissertation page
    And I delete it
    Then the system has no dissertation entitled "Dissertacao Teste4"
    
    