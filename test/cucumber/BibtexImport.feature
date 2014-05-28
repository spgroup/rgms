@i9n
Feature: BibtexImport
  As a member of a research group
  I want to import a bibtex file
  So that the system register yours corresponding publications

  Scenario: simple import bibtex
    Given I am on Import Bibtex File Menu
    When  I click "Choose file"
    And I select a bibtex file and I click "Import"
    Then all corresponding publications are created
    And all of them are stored

  Scenario: bibtex file unformatted
    Given I am on Import Bibtex File Menu
    When  I click "Choose file"
    And I select a bibtex file unformatted and I click "Import"
    Then the system output the message error "bibtex file unformatted"
    And no publication is stored

  Scenario: bibtex file with several publication types
    Given I am on Import Bibtex File Menu
    When  I click "Choose file"
    And I select a bibtex file with one Dissertation and two Thesis and I click "Import"
    Then at least one Dissertation publication is created
    And at least two Thesis publications is created
    And one Dissertation is stored
    And two Thesis is stored