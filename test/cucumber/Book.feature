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
    Then the system lists "SPL Development" and "Next Generation Software Product Line Engineering"
#end

  Scenario: duplicate book
    Given the book "SPL Development" is stored in the system with file name "NGSPL-0.pdf"
    When I create the book "SPL Development" with file name "NGSPL-0.pdf"
    Then the book "SPL Development" is not stored twice
    And an error message is displayed

  Scenario: edit existing book
    Given the book "SPL Development" is stored in the system with file name "HSPLE.pdf"
    When I edit the book title from "SPL Development" to "New Title"
    Then the book "New Title" is properly updated by the system
  #if($book)
    And the message "The edition has been successful" is displayed
  #end
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
    
   Scenario: upload book without a file
     Given the system has no books stored
      When I create a book "SPL Development"
      And I upload no file
      Then the system has no book called "SPL Development"
      And an error message is displayed

  Scenario: search book by tags
    Given the book "Redes Neurais" is stored in the system with tag "Inteligencia Artificial"
    And I am in the books menu
    When I click search button
    And I write "Inteligencia Artificial" in the bar
    Then a list containing "Redes Neurais" is displayed
    
  Scenario: mouse over information
    Given I am on the books menu
    When I put the mouse over a book
    And I wait 1 second
    Then a window with information about the book is displayed until I remove the mouse from over the book
    
 #if($book)
  Scenario: new book web
    Given I am at the book page
    And the system has no book entitled "Next Generation Software Product Line Engineering"
    When I go to new book page
    And I use the webpage to create the book "Next Generation Software Product Line Engineering" with file name "Ngs.pdf" and author name "James"
    Then the book "Next Generation Software Product Line Engineering" was stored by the system
    And the message "the book was sucessfully stored" is displayed
#end
#if($searchBookByVolume)
  Scenario: Search Book by Volume web
    Given I am at the book page
    And The system has a book entitled "Next Generation Software Product Line Engineering" volume "1"
    When I fill the webpage search for a book with volume "1" and click the search button
    Then The system displays the book entitled "Next Generation Software Product Line Engineering" volume "1"

  Scenario: Search Book by Volume
    Given The book entitled "Next Generation Software Product Line Engineering" volume "1" is stored on the system
    When I search for a book with volume "1"
    Then the system lists all books with volume "1"
#end

#if($searchBookByPublisher)
  Scenario: Search Book by Publisher web
    Given I am at the book page
    And The system has a book with publisher "James"
    When I fill the webpage search for a book with publisher "James" and click the search button
    Then The system displays the book with publisher "James"

  Scenario: Search Book by Publisher
    Given The book with publisher "James" is stored on the system
    When I search for a book with publisher "James"
    Then the system lists all books with publisher "James"
#end


#if($searchBookByTitle)
  Scenario: Search Book by Title web
    Given I am at the book page
    And The system has a book entitled "Next Generation Software Product Line Engineering"
    When I fill the webpage search for a book with title "Next Generation Software Product Line Engineering" and click the search button
    Then The system displays the book entitled "Next Generation Software Product Line Engineering"

  Scenario: Search Book by Title
    Given The book entitled "Next Generation Software Product Line Engineering" is stored on the system
    When I search for a book entitled "Next Generation Software Product Line Engineering"
    Then the system lists all books entitled "Next Generation Software Product Line Engineering"
#end