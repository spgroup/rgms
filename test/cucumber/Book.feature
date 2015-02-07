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

  # @author droa
  # BEGIN

  #MODIFIED
  Scenario: new book web
    Given I am at the publications menu
    When I select the "Book" option at the publications menu
    And I select the new book option at the book page
    Then I can fill the book details

  Scenario: remove book web
    Given I am logged in as admin
    And I am at the Book Page
    When I go to the page of the "Next Generation Software Product Line Engineering" book
    And I follow the delete button confirming with OK
    Then the book "Next Generation Software Product Line Engineering" is properly removed by the system

  Scenario: Add a new book and tweet it
    Given I am logged in as admin
    And I am at the Book Page
    When I try to create a book named "Next Generation Software Product Line Engineering" with filename "NGS.pdf"
    And I click on Share to share the book on Twitter with "rgms_ufpe" and "rgmsadmin2013"
    Then a pop-up window with a tweet regarding the new book "Next Generation Software Product Line Engineering" is shown

  Scenario: edit book web
    Given I am logged in as admin
    And I am at the Book Page
    And the book "Next Generation Software Product Line Engineering" is in the book list with file name "NGS.pdf"
    When I select to edit the book "Next Generation Software Product Line Engineering" in resulting list
    Then I can change the book details

  # END #

 Scenario: download book file
   Given I am logged in as admin
    And I am at the Book Page
    And the book "Next Generation Software Product Line Engineering" is in the book list with file name "NGS.pdf"
    When I select the download button
    Then the download the file named "Ngs.pdf" is properly filed

