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

  Scenario: create thesis web
    Given I am at the create thesis page
    When  I fill the thesis details with "Software Engineering", "10", "8", "1998", "UFPE" and "Recife"
    Then  I am on the thesis show page
    And   The thesis "Software Engineering" is properly stored by the system

  Scenario: create thesis web with partial information
    Given I am at the create thesis page
    When  I fill some thesis details with "Tese002", "10", "8", "1998", "UFPE" and "Recife"
    Then  I am still on the create thesis page with the error message

#if ($contextualInformation)
  Scenario: Add a new thesis with user data already filled by default
    Given I am at the publications menu
    When I select the "Tese" option at the publications menu
    And I select the new thesis option at the thesis page
    Then I see my user listed as an author member of thesis by default
    And I see my school name as school of thesis by default
#end