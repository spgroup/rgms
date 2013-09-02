@i9n
Feature: BibtexImport
  As a member of a research group
  I want to import a bibtex file
  So that the system register yours corresponding publications

  Scenario: simple import bibtex
    Given I am on Import Bibtex File Menu
    When  I click "Choose file"
    And selected a bibtex file and I click "Import"
    Then is created all corresponding publications
    And all of then are stored

  Scenario: bibtex file unformatted
    Given I am on Import Bibtex File Menu
    When  I click "Choose file"
    And selected a bibtex file unformatted and I click "Import"
    Then the system output the message error "bibtex file unformatted"
    And none publication is stored

  Scenario: bibtex file with several publication types
    Given I am on Import Bibtex File Menu
    When  I click "Choose file"
    And selected a bibtex file with one Dissertation and two Thesis and I click "Import"
    Then is created one Dissertation publication
    And is created two Thesis publications
    And one Dissertation is stored and two Thesis is stored