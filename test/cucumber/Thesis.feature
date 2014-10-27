@i9n
Feature: Thesis Tests
  As a member of a research group
  I want to add, remove and modify theses I have added

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

  Scenario: order thesis list by date
    Given at least one thesis is stored in the system
    And I am at the thesis list page
    When I click in order thesis by date
    Then the returned thesis list has the same items but it is sorted by date

    #if($Thesis Tests)
      Scenario: order thesis list by name
        Given at least one thesis is stored in the system
        And I am at the thesis list page
        When I click in order thesis by name
        Then the returned thesis list has the same items but it is sorted by name

    Scenario: order thesis list by author
      Given at least one thesis is stored in the system
      And I am at the thesis list page
      When I click in order thesis by author
      Then the returned thesis list has the same items but it is sorted by author
    #end

  Scenario: search an existing thesis
    Given the system has one thesis entitled "Software Engineering" with author name "Pressman", year of publication "1998" and university "UFPE"
    And I am at the thesis search page
    When I search for "Software Enginnering" by "Pressman"
    And I select to view the entry that has university "UFPE" and publication year "1998"
    Then the thesis "Software Enginnering" by "Pressman" appears in the thesis view page

  Scenario: create thesis web without a file
    Given I am at the create thesis page
    When I fill the thesis fields with "My Thesis", "2014/05/16", "UFPE","Address", "Author","Advisor"
    And I click in create button
    Then the system shows a warning message "Thesis without file, it is mandatory"

#if($contextualInformation)
  @ignore
  Scenario: search an existing thesis filled by default
    Given the system has at least one thesis entitled "Software Engineering"
    And I am at the thesis search page
    And I have already done a search about "Software Enginnering" previously
    When I press "S"
    And I choose "Software Enginnering" in dropdown search list
    And I click in search button
    Then all theses entitled "Software Engineering" are shown
#end

  @ignore
  Scenario: edit thesis title
    Given the system has thesis entitled "My Thesis"
    When I change the title from "My Thesis" to "My Thesis Renamed"
    Then the thesis entitled "My Thesis Renamed" is properly renamed by the system
    And the other theses are not changed by the system

  @ignore
  Scenario: edit thesis with invalid data
    Given the system has thesis entitled "My Thesis"
    When I change the title from "My Thesis" to ""
    Then the existing thesis are not changed by the system

  @ignore
  Scenario: search a thesis
    Given the system has one thesis entitled "My Thesis"
    When I search for thesis entitled "My Thesis"
    Then the existing thesis are not changed by the system

  @ignore
  Scenario: upload thesis with a file
    Given The system has no thesis entitled "My Thesis"
    When I upload the file "My Thesis.xml"
    Then the existing thesis are not changed by the system
    And the system stores properly the thesis entitled "My Thesis"
    
# editar dados de uma tese, ordenar lista de teses, filtrar lista de teses,
# criar tese com dados inválidos, a chave é mesmo o título da tese?, tamanho
# dos campos, o dia e o arquivo deveriam ser opcional, deveria poder adicionar
# o arquivo depois, address deveria ser opcional, deveria ter universidade e
# centro/departamento, deveria ter apenas um autor e a possibilidade de um
# orientador e co-orientador
