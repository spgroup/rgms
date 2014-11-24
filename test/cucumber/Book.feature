@i9n
Feature: Book
  As a member of a research group
  I want to add, remove and modify books I have published
  so that I can generate web pages and reports containing these books

#if ($newBook)
  Scenario: new book
    Given the system has no book entitled "SPL Development"
    When  I create the book "SPL Development" with its full description and file name "HSPLE.pdf"
    Then  the book "SPL Development" is properly stored by the system
#end

  Scenario: remove book
    Given the book "SPL Development" is stored in the system with file name "NGSPL-2.pdf"
    When I remove the book "SPL Development"
    Then the book "SPL Development" is properly removed by the system

  Scenario: duplicate book
    Given the book "SPL Development" is stored in the system with file name "NGSPL-0.pdf"
    When I create the book "SPL Development" with file name "NGSPL-0.pdf"
    Then the book "SPL Development" is not stored twice

#if ($editExisting)
  Scenario: edit existing book
    Given the book "SPL Development" is stored in the system with file name "HSPLE.pdf"
    When I edit the book informations: title or description
    Then the book "SPL Development" is properly updated by the system
#end

  Scenario: edit existing book with a invalid name
    Given the book "SPL Development" is stored in the system with file name "HSPLE.pdf"
    When I edit the book title from "SPL Development" to "ESS Book"
    And there is already a stored book named "ESS Book"
    Then the book "SPL Development" will not be modified

  Scenario: upload book with a file
    Given the system has no books stored
    When I upload the books of "curriculo.xml"
    Then the system has all the books of the xml file

#if ($newBookWeb)
  Scenario: new book web
    Given I am at the book page
    And the system has no book entitled "Next Generation Software Product Line Engineering"
    When I go to new book page
    And I fill in with the book title and description
    And I use the webpage to create the book "Next Generation Software Product Line Engineering" with file name "Ngs.pdf"
    Then the book "Next Generation Software Product Line Engineering" will be stored by the system along with its description
#end

#if ($listBooks)
Scenario: list existing books 
   Given the system has a book entitled "SP Development" with file name "SPD.pdf"
   When I view the book list
   Then tmy book chapter list contains "SP Development"

#end

#if ($readersComments)
Scenario: readers comments
   Given the book "SP Development" has comments
   When	I view the book "SP Develpment" information
   Then the system will show the comments about "SP Development"
#end

#if ($listBooksWeb)
Scenario: list existing books web
   Given that I'm at the book page
   And the book "SP Development" with file name "SPD.pdf" was created before
    Then My resulting book list contains "SP Development"
#end


#if ($readersCommentsWeb)
Scenario: reader's comments web
   Given that I'm at the book "SP Development" page
   When I click on the book comment option from the "SP Development" page
   Then the comments about "SP Development" will be show in the book "SP Development" page
#end


#if ($removeBookWeb)
Scenario: remove book web
   Given I am at the book page
   And the book "SP Development" is stored in the system
   And I select the option to remove the book
   Then the book "SP Development" is properly removed by the system
#end

