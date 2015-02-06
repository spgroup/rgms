@i9n
Feature: Thesis Tests
  As a member of a research group
  I want to add, remove and modify theses I have added

  Scenario: new thesis duplicated
    Given The thesis "Thesis duplicated" is stored in the system with file name "Thesisduplicated.txt"
    When  I create the thesis "Thesis duplicated" with file name "Thesisduplicated2.txt"
    Then  The thesis "Thesis duplicated" is not stored twice

	#if($thesis)
    @vddm
	Scenario: new thesis
		Given The system has no thesis entitled "New thesis"
		When  I create the thesis "New thesis" with file name "NewthesisGUI.txt"
		And I create the thesis "New thesis2" with file name "NewThesis.pdf"
        Then  The thesis "New thesis" not is properly stored by the system, but "New thesis2" is
	#end

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
    Given The thesis "My Thesis" is stored in the system with file name "Joee.pdf"
    When I change the title from "My Thesis" to "My Thesis Renamed"
    Then the thesis entitled "My Thesis Renamed" is properly renamed by the system
    And the other theses are not changed by the system

  @ignore
  Scenario: edit thesis with invalid data
    Given The thesis "My Thesis" is stored in the system with file name "Joee.pdf"
    When I change the title from "My Thesis" to ""
    Then the existing thesis are not changed by the system

	#if($search)
    @vddm
	Scenario: search a thesis
		Given The thesis "My Thesis" is stored in the system with file name "Joee.pdf"
		When I search for thesis entitled "My Thesis"
		Then the "My Thesis" thesis is returned by the system
	#end

	#if($fileThesis)
    @vddm
	Scenario: upload existing thesis with a file
		Given The thesis "New thesis" is stored in the system with file name "Joee.pdf"
		When I upload the file "Newthesis.pdf" to "New thesis"
		Then the file "Joee.pdf" associated with the existing thesis "New thesis" is replaced by "Newthesis.pdf"
	#end
	
# editar dados de uma tese, ordenar lista de teses, filtrar lista de teses,
# criar tese com dados inválidos, a chave é mesmo o título da tese?, tamanho
# dos campos, o dia e o arquivo deveriam ser opcional, deveria poder adicionar
# o arquivo depois, address deveria ser opcional, deveria ter universidade e
# centro/departamento, deveria ter apenas um autor e a possibilidade de um
# orientador e co-orientador
