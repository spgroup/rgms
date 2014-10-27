@i9n
Feature: Book
  As a member of a research group
  I want to add, remove and modify books I have published
  so that I can generate web pages and reports containing these books

  Scenario: new book
    Given the system has no book entitled "SPL Development"
    When  I create the book "SPL Development" with file name "HSPLE.pdf" and author name "James"
    Then  the book "SPL Development" is properly stored by the system

  Scenario: remove book
    Given the book "SPL Development" is stored in the system with file name "NGSPL-2.pdf"
    When I remove the book "SPL Development"
    Then the book "SPL Development" is properly removed by the system
   
#if($book)     
  Scenario: list book
    Given the book "SPL Development" is stored in the system with file name "NGSPL-2.pdf"
    When I create the book "Next Generation Software Product Line Engineering" with file name "NGSPLE.pdf"
    Then The system list "SPL Development" and "Next Generation Software Product Line Engineering" 
#end

  Scenario: duplicate book
    Given the book "SPL Development" is stored in the system with file name "NGSPL-0.pdf"
    When I create the book "SPL Development" with file name "NGSPL-0.pdf"
    Then the book "SPL Development" is not stored twice

  Scenario: edit existing book
    Given the book "SPL Development" is stored in the system with file name "HSPLE.pdf"
    When I edit the book title from "SPL Development" to "New Title"
    Then the book "New Title" is properly updated by the system
#if($book)
  Scenario: edit existing book with duplicate title
    Given the book "Book 1" is stored in the system with file name "B1.pdf"
    And the book "Book 2" is stored in the system with file name "B2.pdf"
    When I edit the book title from "Book 1" to "Book 2"
    Then the book "Book 2" is not updated by the system
#end

  Scenario: upload book with a file
    Given the system has no books stored
    When I upload the books of "curriculo.xml"
    Then the system has all the books of the xml file

  Scenario: new book web
    Given I am at the book page
    And the system has no book entitled "Next Generation Software Product Line Engineering"
    When I go to new book page
    And I use the webpage to create the book "Next Generation Software Product Line Engineering" with file name "Ngs.pdf" and author name "James"
    Then the book "Next Generation Software Product Line Engineering" was stored by the system
  #if($book)
    And the message "the book was sucessful stored" is displayed
  #end
