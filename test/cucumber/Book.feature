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
    
#if($updateExistingBook)
  Scenario: update existing book
    Given the book "SPL Development" is stored in the system with file name "HSPLE.pdf"
    When I upload the file in the system with name "newHSPLE.pdf"
    Then the archive file "newHSPLE.pdf" is properly updated by the system
#end

#if($updateExistingBookWeb)
   Scenario: update existing book web
    Given I am at the book page
    And the system has a book entitled "SPL Development" and file name "HSPLE.pdf"
    When I click on "Edit book"  //eu teria que ser redirecionado pra outra página uma de edição, como eu sei que essa ja existe?
    And I click on "Upload file"
    And I choose "newHSPLE.pdf"
    Then I have the book entitled "SPL Development" with file name "newHSPLE.pdf" stored on the system
#end

#if($newBookWithoutFile)
  Scenario: new book without file
    Given the system has no book entitled "SPL Development"
    When I try to create the book "SPL Development" without file
    Then the book is not created by the system
#end

#if($newBookWithoutFile)
  Scenario: new book without file
    Given the system has no book entitled "SPL Development"
    When I try to create the book "SPL Development" without file
    Then the book is not created by the system
#end

#if($newBookWithoutFileWeb)
  Scenario: new book without file web
    Given I am at the book page
    And the system has no book entitled "SPL Development"
    When I go to new book page
    And I try to create the book "SPL Development" without file
    Then the system shows a warning message "Book without file, it is mandatory"
#end

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
