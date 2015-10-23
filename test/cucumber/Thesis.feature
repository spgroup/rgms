@i9n
Feature: Thesis Tests
  As a member of a research group
  I want to add, remove and modify theses I have added

  Scenario: order thesis list by date
    Given at least two thesis is stored in the system
    And I am at the thesis list page
    When I click in order thesis by date
    Then the returned thesis list has the same items but it is sorted by date

  Scenario: search an existing thesis
    Given the system has one thesis entitled "Software Product Lines" with publication year "1998" and school "UFPE"
    And I am at the thesis search page
    When I search for "Software Product Lines" with publication year "1998" and school "UFPE"
    And I select to view the entry that has title "Software Product Lines"
    Then the thesis "Software Product Lines" with publication year "1998" and school "UFPE" appears in the thesis view page

  Scenario: create thesis without a file
    Given I am at the create thesis page
    When I fill the thesis fields with "My Thesis", "2014", "5", "16", "UFPE", "Address"
    And I try to create a new thesis
    Then I am still on the create thesis page with the error message

#if($contextualInformation)
  Scenario: search an existing thesis filled by default
    Given the system has one thesis entitled "Software Product Lines 2" with publication year "2014" and school "UFPE"
    And I have already done a search about "Software Product Lines 2" previously
    And I am at the thesis search page
    When I enter "Soft" in the title field
    And I choose "Software Product Lines 2" in the displayed list
    And I fill the year "2014" and school "UFPE"
    And I search
    Then the thesis "Software Product Lines 2" appears in the thesis view page
#end

  Scenario: edit thesis title
    Given the system has thesis entitled "Thesis"
    And The system has no thesis entitled "Thesis Renamed"
    When I change the title from "Thesis" to "My Thesis Renamed"
    Then the thesis entitled "My Thesis Renamed" is properly renamed by the system
    And the other theses are not changed by the system

  Scenario: edit thesis with invalid data
    Given the system has thesis entitled "My Thesis 2014"
    When I try to change the title from "My Thesis 2014" to ""
    Then the existing thesis are not changed by the system

  Scenario: search a thesis
    Given the system has thesis entitled "My Thesis"
    When I search for thesis entitled "My Thesis"
    Then the existing thesis are not changed by the system

  Scenario: upload thesis with a file
    Given The system has no thesis entitled "Semantics and Refinement for a Concurrent Object Oriented Language"
    When I upload the file "curriculo.xml" with thesis entitled "Semantics and Refinement for a Concurrent Object Oriented Language"
    Then the existing thesis are not changed by the system
    And the system properly stores the thesis entitled "Semantics and Refinement for a Concurrent Object Oriented Language"

  Scenario: new thesis duplicated
    Given The thesis "Thesis duplicated" is stored in the system with file name "Thesisduplicated.txt"
    When  I create the thesis "Thesis duplicated" with file name "Thesisduplicated2.txt" and school "UFPE"
    Then  The thesis "Thesis duplicated" is not stored twice

  Scenario: new thesis
    Given The system has no thesis entitled "New thesis"
    When  I create the thesis "New thesis" with file name "Newthesis.txt" and school "UFPE"
    Then  The thesis "New thesis" is properly stored by the system

  Scenario: remove existing thesis
    Given   the system has thesis entitled "New thesis2"
    When    I delete the thesis "New thesis2"
    Then    the thesis "New thesis2" is properly removed by the system

  Scenario: create thesis web
    Given I am at the create thesis page
    When  I fill the thesis details with "Software Engineering", "10", "8", "1998", "UFPE" and "Recife"
    Then  I am on the thesis show page
    And   The thesis "Software Engineering" is properly stored by the system

  Scenario: create thesis web with partial information
    Given I am at the create thesis page
    When  I fill some thesis details with "Tese002", "10", "8", "1998", "UFPE" and "Recife"
    Then  I am still on the create thesis page with the error message

  Scenario: remove existing thesis web
    Given I am at the thesis page and the thesis "Software Enginnering2" is stored in the system
    When I select to view thesis "Software Enginnering2" in resulting list
    And I select the remover option at the thesis show page
    Then the thesis "Software Enginnering2" is removed from the system

#if ($contextualInformation)
  Scenario: Add a new thesis with user data already filled by default
    Given I am at the publications menu
    When I select the "Tese" option at the publications menu
    And I select the new thesis option at the thesis page
    Then I see my user listed as an author member of thesis by default
    And I see my school name as school of thesis by default
#end

# editar dados de uma tese, ordenar lista de teses, filtrar lista de teses,
# criar tese com dados inválidos, a chave é mesmo o título da tese?, tamanho
# dos campos, o dia e o arquivo deveriam ser opcional, deveria poder adicionar
# o arquivo depois, address deveria ser opcional, deveria ter universidade e
# centro/departamento, deveria ter apenas um autor e a possibilidade de um
# orientador e co-orientador
