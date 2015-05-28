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
    Given I am on the book page
    When I select to sort the books by "title"
    Then the books are ordered by "title" in alphabetical order

  Scenario: List existing book
    Given the system has a book entitled "SPL Development" with file name "HSPLE.pdf"
    When I view the book list
    Then my book list contains the book "SPL Development"

  Scenario: Post an existing book on facebook
    Given the system has a book entitled "SPL Development" with file name "HSPLE.pdf"
    When I share the book entitled "SPL Development" on facebook
    Then a facebook message is posted #if ($implementçãoFuncionalidadesNosMoldesArticle)

 #if ($implementçãoNovasFuncionalidades)
  Scenario: List existing books by title in alphabetical order
    Given the system has book entitled "Livro de Teste" with file name "TCS-1401.pdf"
    And the system has book entitled "SPL Development" with file name "MACI.pdf"
    When the system orders the book list by title
    Then the system book list content is not modified

  Scenario: list existing book web
    Given I am on the book page
    And there is the book "Software Engineering" stored in the system with file name "TCS-88.pdf"
    Then my resulting books list contains the book "Software Engineering"

  Scenario: Filter existing books by author
    Given the system has some books authored by "Paulo Borba"
    When the system filter the books authored by author "Paulo Borba"
    Then the system book list content is not modified

  Scenario: Post an existing article on facebook web
    Given I am on the book page
    And the article "Software Engineering 3" is stored in the system with file name "TCS-04.pdf"
    When I select to view "Software Engineering 3" in resulting book list
    And I click on Share on Facebook for book
    Then A facebook message was posted
 #end