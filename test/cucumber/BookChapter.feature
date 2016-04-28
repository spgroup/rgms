@i9n
Feature: BookChapter
  As a member of a research group
  I want to add, remove and modify book chapters I have published
  so that I can generate web pages and reports containing these book chapters

  Scenario: new book chapter
    Given the system has no book chapter entitled "SPL Development"
    When  I create the book chapter "SPL Development" with file name "HSPLE.pdf"
    Then  the book chapter "SPL Development" is properly stored by the system

  Scenario: duplicate book chapter
    Given the book chapter "Next Generation Software Product Line Engineering" is stored in the system with file name "NGSPL-0.pdf"
    When I create the book chapter "Next Generation Software Product Line Engineering" with file name "NGSPL-0.pdf"
    Then the book chapter "Next Generation Software Product Line Engineering" is not stored twice

  Scenario: remove book chapter
    Given the book chapter "Next Generation Software Product Line Engineering" is stored in the system with file name "NGSPL-2.pdf"
    When I remove the book chapter "Next Generation Software Product Line Engineering"
    * the book chapter "Next Generation Software Product Line Engineering" is properly removed by the system

  Scenario: register book chapter with invalid data
    Given I am at the book chapter page
    And I select the new book chapter option at the book chapter page
    And I fill only the title field with the value "Next Generation Software Product Line Engineering"
    Then A failure message is displayed
    And I still on the book chapter create page

  Scenario: new book chapter web
    Given I am at the book chapter page
    And the system has no book chapter entitled "Next Generation Software Product Line Engineering"
    When I go to new book chapter page
    And I use the webpage to create the book chapter "Next Generation Software Product Line Engineering" with file name "Ngs.pdf"
    Then the book chapter "Next Generation Software Product Line Engineering" was stored by the system
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
    When I select the new book chapter option at the book chapter page
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

  Scenario: upload book chapters without a file
    Given I am at the publications menu
    When I select the Book Chapter option at the program menu
    And I select the upload button at the book chapter page
    Then I'm still on book chapter page
    And the book chapters are not stored by the system
