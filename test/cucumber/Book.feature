@i9n
Feature: Book
  As a member of a research group
  I want to add, remove and modify books I have published
  so that I can generate web pages and reports containing these books

  Scenario: new book
    Given the system has no book entitled "SPL Development"
    When  I create the book "SPL Development" with file name "HSPLE.pdf"
    Then  the book "SPL Development" is properly stored by the system

  Scenario: remove book
    Given the book "SPL Development" is stored in the system with file name "NGSPL-2.pdf"
    When I remove the book "SPL Development"
    Then the book "SPL Development" is properly removed by the system

  Scenario: duplicate book
    Given the book "SPL Development" is stored in the system with file name "NGSPL-0.pdf"
    When I create the book "SPL Development" with file name "NGSPL-0.pdf"
    Then the book "SPL Development" is not stored twice

  Scenario: edit existing book
    Given the book "SPL Development" is stored in the system with file name "HSPLE.pdf"
    When I edit the book title from "SPL Development" to "New Title"
    Then the book "New Title" is properly updated by the system

  Scenario: upload book with a file
    Given the system has no books stored
    When I upload the books of "curriculo.xml"
    Then the system has all the books of the xml file

  Scenario: new book web
    Given I am at the book page
    And the system has no book entitled "Next Generation Software Product Line Engineering"
    When I go to new book page
    And I use the webpage to create the book "Next Generation Software Product Line Engineering" with file name "Ngs.pdf"
    Then the book "Next Generation Software Product Line Engineering" was stored by the system

  Scenario: List existing books by title in alphabetical order web
    Given I am on the "Book" page
    When I select to sort the books by "Title"
    Then the books are ordered by "Title" in alphabetical order    Then the book "Next Generation Software Product Line Engineering" was stored by the system

  Scenario teste