@i9n
Feature: BookChapter
  As a member of a research group
  I want to add, remove and modify book chapters I have published
  so that I can generate web pages and reports containing these book chapters

  Scenario: new book chapter
    Given the system has no book chapter entitled "Next Generation Software Product Line Engineering"
    When  I create the book chapter "Next Generation Software Product Line Engineering" with file name "NGSPL.pdf"
    Then  the book chapter "Next Generation Software Product Line Engineering" is properly stored by the system

  Scenario: duplicate book chapter
    Given the book chapter "Next Generation Software Product Line Engineering" is stored in the system with file name "NGSPL-0.pdf"
    When I create the book chapter "Next Generation Software Product Line Engineering" with file name "NGSPL-0.pdf"
    Then the book chapter  "Next Generation Software Product Line Engineering" is not stored twice

  Scenario: remove book chapter
    Given the book chapter "Next Generation Software Product Line Engineering" is stored in the system with file name "NGSPL-0.pdf"
    When I remove the book chapter "Next Generation Software Product Line Engineering"
    Then the book chapter "Next Generation Software Product Line Engineering" is properly removed by the system

  Scenario: register book chapter with invalid data
    Given I am at the publication menu
    When I select the "Book Chapter" option at the publication menu
    And I select the Novo BookChapter option at the book chapter page
    And I fill only the title field at book chapter create page
    Then I still on the book chapter create page

  Scenario: new book chapter web
    Given I am at the publication menu
    When I select the "Book Chapter" option at the publication menu
    And I select the Novo BookChapter option at the book chapter page
    Then I can fill the book chapter details
