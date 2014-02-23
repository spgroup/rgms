Feature: Book
  As a member of a research group
  I want to add, remove and modify books I have published
  so that I can generate web pages and reports containing these books

  Scenario: new book
    Given the system has no book entitled "SPL Development"
    When  I create the book "SPL Development" with file name "HSPLE.pdf"
    Then  the book "SPL Development" is properly stored by the system

  Scenario: remove book
    Given the book "Next Generation Software Product Line Engineering" is stored in the system with file name "NGSPL-2.pdf"
    When I remove the book "Next Generation Software Product Line Engineering"
    Then the book "Next Generation Software Product Line Engineering" is properly removed by the system

  Scenario: duplicate book
    Given the book "Next Generation Software Product Line Engineering" is stored in the system with file name "NGSPL-0.pdf"
    When I create the book "Next Generation Software Product Line Engineering" with file name "NGSPL-0.pdf"
    Then the book "Next Generation Software Product Line Engineering" is not stored twice

  Scenario: edit existing book
    Given the book "SPL Development" is stored in the system with file name "HSPLE.pdf"
    When I edit the book title from "SPL Development" to "New Title"
    Then the book "SPL Development" is properly updated by the system