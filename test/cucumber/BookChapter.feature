@i9n
Feature: BookChapter
  As a member of a research group
  I want to add, remove and modify book chapters I have published
  so that I can generate web pages and reports containing these book chapters

  Scenario: new book chapter
    Given the system has no book chapter entitled "SPL Development"
    When  I create the book chapter "SPL Development" with file name "HSPLE.pdf"
    Then  the book chapter "SPL Development" is properly stored by the system
    And the other book chapters are still stored in the system

  Scenario: duplicate book chapter
    Given the book chapter "Next Generation Software Product Line Engineering" is stored in the system with file name "NGSPL-0.pdf"
    When I create the book chapter "Next Generation Software Product Line Engineering" with file name "NGSPL-0.pdf"
    Then the book chapter "Next Generation Software Product Line Engineering" is not stored twice

  Scenario: remove book chapter
    Given the book chapter "Next Generation Software Product Line Engineering" is stored in the system with file name "NGSPL-2.pdf"
    When I remove the book chapter "Next Generation Software Product Line Engineering"
    Then the book chapter "Next Generation Software Product Line Engineering" is properly removed by the system
    And the other book chapters are still stored in the system

  Scenario: register book chapter with invalid data web
    Given I am at the book chapter page
    And I select the new book chapter option at the book chapter page
    And I fill only the title field with the value "Next Generation Software Product Line Engineering"
    Then A failure message is displayed
    And I am still on the book chapter create page
    And the book chapter is not stored by the system

  Scenario: new book chapter web
    Given I am at the book chapter page
    And the system has no book chapter entitled "Next Generation Software Product Line Engineering"
    When I go to new book chapter page
    And I use the webpage to create the book chapter "Next Generation Software Product Line Engineering" with file name "Ngs.pdf"
    Then the book chapter "Next Generation Software Product Line Engineering" is stored by the system
    And it is shown in the book chapter list with title "Next Generation Software Product Line Engineering"

  #4
  Scenario: new duplicate book chapter web
    Given I am at the book chapter page
    And the system has a book chapter entitled "Next Generation Software Product Line Engineering" with file name "Ngs.pdf"
    When I go to new book chapter page
    And I use the webpage to create the book chapter "Next Generation Software Product Line Engineering" with file name "Ngs.pdf"
    Then the book chapter "Next Generation Software Product Line Engineering" is not stored twice
    And the system shows an error message

#if ($contextualInformation)

  Scenario: new book chapter filled with user data by default
    Given I am at the book chapter page
    And I select the new book chapter option at the book chapter page
    Then I see my user listed as a member of book chapter by default
#end

  Scenario: list existing book chapter
    Given the book chapter "Artificial Neural Networks" is stored in the system with file name "ANN.pdf"
    When I view the book chapter list
    Then my book chapter list contains "Artificial Neural Networks"

  Scenario: list existing book chapter web
    Given I am at the book chapter page
    And the book chapter "Next Generation Software Product Line Engineering" with file name "Ngs.pdf" was created before
    Then My resulting book chapter list contains "Next Generation Software Product Line Engineering"

  Scenario: upload book chapter with a file
    Given the system has some book chapters stored
    When I upload the book chapters of "curriculo.xml"
    Then the system has all the book chapters of the xml file
    And the other book chapters are still stored in the system

  Scenario: upload book chapters without a file web
    Given I am at the publications menu
    When I select the Book Chapter option at the program menu
    And I select the upload button at the book chapter page
    Then no book chapters are added to the system
    And the existing book chapters are not changed
    And the book chapters are not stored by the system

  Scenario: upload book chapter with a file web
    Given I am at the publications menu
    And The system does not have a book chapter "Capitulo 1" with file “Book chapters 1.xml"
    When I select the Book Chapter option at the program menu
    And I select the upload button at the book chapter page
    And I add the book chapters with a file “Book chapters 1.xml"
    Then the book chapters in the file are stored by the system
    And the existing book chapters are not changed

  Scenario: edit existing book chapter  web
    Given I am at the book chapters page and the book chapter "Basic Concepts, Classification, and Quality Criteria" is stored in the system with the file name "chapter3.pdf"
    When I select to view "Basic Concepts, Classification, and Quality Criteria" in resulting list
    And I change the book chapter title to "Chapter 3"
    Then I select the "Alterar" option in Book Chapter Show Page
    And I am at Book Chapter Show Page

  Scenario: order existing book chapters by title web
    Given I am at the book chapters page
    When I select to view all book chapters ordered by title in resulting list
    Then The resulting book chapter list contains all book chapters ordered by title

  Scenario: filter existing book chapters by author web
    Given I am at the book chapters page
    When I select to view all my book chapters filtered by author “Larissa Falcao” in resulting list
    Then The resulting book chapter list contains only book chapters filtered by author "Larissa Falcao"

  Scenario: upload book chapter without a file
    Given the system has some book chapters stored
    When I upload the book chapters of "chapters.xml"
    Then book chapters are not stored by the system
    And the system has the same number of book chapters

  Scenario: edit book chapter
    Given the book chapter "Basic Concepts, Classification, and Quality Criteria" is stored in the system with file name "chapter3.pdf"
    And the system has no book chapter entitled "Chapter 3"
    When I edit the book chapter title from "Basic Concepts, Classification, and Quality Criteria" to "Chapter 3"
    Then the book chapter "Basic Concepts, Classification, and Quality Criteria" is properly updated by the system

  Scenario: order book chapters by title
    Given the system has some book chapters stored
    When I view the book chapter list
    And I choose to view the book chapter list ordered by title
    Then the system book chapter list content is not modified

  Scenario: filter book chapters by author
    Given the system has some book chapters stored
    When I view the book chapter list
    And I choose to filter my book chapter list by author "Larissa Falcao"
    Then the system book chapter list content is not modified