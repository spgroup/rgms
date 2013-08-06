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
    Then the book chapter "Next Generation Software Product Line Engineering" is properly removed by the system

  Scenario: register book chapter with invalid data
    Given I am at the publication menu
    When I select the "Book Chapter" option at the publication menu
    And I select the Novo BookChapter option at the book chapter page
    And I fill only the title field with the value "Next Generation Software Product Line Engineering"
    Then A failure message is displayed
    And I still on the book chapter create page

  Scenario: new book chapter web
    Given I am at the book chapter page
    And the system has no book chapter entitled "Next Generation Software Product Line Engineering"
    When I go to NewBookChapter page
    And I use the webpage to create the book chapter "Next Generation Software Product Line Engineering" with file name "NGSPL-0.pdf"
    Then the book chapter "Next Generation Software Product Line Engineering" was stored by the system
    And it is shown in the book chapter list with title "Next Generation Software Product Line Engineering"

#if ($Autofill)

  Scenario: new book chapter filled with user data by default
    Given I am at the publication menu
    When I select the "Book Chapter" option at the publication menu
    And I select the Novo BookChapter option at the book chapter page
    Then I see my user listed as a member of book chapter by default
#end

  Scenario: list existing book chapter
    Given the system has book chapter entitled "Artificial Neural Networks" with file name "ANN.pdf"
    When I view the book chapter list
    Then my book chapter list contains "Artificial Neural Networks"

  Scenario: list existing book chapter web
    Given I am at the book chapter page
    And the book chapter "Next Generation Software Product Line Engineering" with file name "Ngs.pdf" was created before
    Then My resulting book chapter list contains "Next Generation Software Product Line Engineering"