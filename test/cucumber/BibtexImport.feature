@i9n
Feature: BibtexImport
  As a member of a research group
  I want to import a bibtex file
  So that the system register yours corresponding publications 

  Scenario: import bibtex
    Given the bibtex file
    When  I try to upload the bibtex file
    Then is created all corresponding publications
    And all of then are stored

  Scenario: bibtex file unformatted
    Given the file bibtex unformatted
    When I try to upload the bibtex file unformatted
    Then the system output the message error "bibtex file unformatted"
    And the file is not accepted*

  Scenario: bibtex file with several publication types
    Given the bibtex file with one Book Chapter and two Technical Report 
    When I try to upload the bibtex file 
    Then is created one Book Chapter publication
    And is created two Technical Report publications
    And all of then are stored*