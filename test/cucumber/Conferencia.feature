@i9n
Feature: conferencia
  As a member of a research group
  So that I can add, remove and modify conferencias I had published
  I want to generate web pages and reports containing these conferencias

  Scenario: new conferencia
    Given the system has no conferencia entitled "IV Conference on Software Product Lines"
    When I create the conferencia "IV Conference on Software Product Lines" with file name "SPLC.pdf"
    Then the conferencia "IV Conference on Software Product Lines" is properly stored by the system

  Scenario: duplicate conferencia
    Given the conferencia "I International Conference on Software Engineering" is stored in the system with file name "IICSE-0.pdf"
    When I create the conferencia "I International Conference on Software Engineering" with file name "IICSE-0.pdf"
    Then the conferencia "I International Conference on Software Engineering" is not stored twice


  Scenario: remove conferencia
    Given the conferencia "IV Conference on Software Product Lines" is stored in the system with file name "IICSE-1.pdf"
    When I remove the conferencia "IV Conference on Software Product Lines"
    Then the conferencia "IV Conference on Software Product Lines" is properly removed by the system


  Scenario: new conferencia web
    Given I am at the publications
    When I select the conferencia option at the publications menu
    And I select the new conferencia option at the conferencia page
    Then I can fill the conferencia details

  @ignore
  Scenario: list conferencia web
    Given I am at the publications menu
    When I select the conferencia option at the publications menu
    Then a list of conferencias stored by the system is displayed at the conferencia page by alphabetic order

#if ($contextualInformation)
  Scenario: new conferencia web has user data filled by default
    Given I am at the publications
    When I select the conferencia option at the publications menu
    And I select the new conferencia option at the conferencia page
    Then I see my user listed as an author member of conferencia by default

#end

  Scenario: back to main menu web
    Given I am at the publications
    When I select the conferencia option at the publications menu
    And I select the home option at the conferencia page
    Then I am back at the publications and conferencias menu

  Scenario: remove conferencia that does not exist
    Given the system has no conferencia entitled "IV Conference on Software Product Lines"
    When I try to remove the conferencia "IV Conference on Software Product Lines"
    Then nothing happens

  Scenario: remove and create the same conferencia
    Given the conferencia "V Conference on Software Product Lines" is stored in the system with file name "IICSE-10.pdf"
    When I remove the conferencia "V Conference on Software Product Lines"
    And I create the conferencia "V Conference on Software Product Lines" with file name "IICSE-12.pdf"
    Then the conferencia "V Conference on Software Product Lines" is properly stored by the system

  Scenario: remove conferencia web
    Given I am at the publications menu
    When I select the conferencia option at the publications menu
    And a list of conferencias stored by the system is displayed at the conferencia page
    Then I can remove one conferencia

  Scenario: upload conferencia with a file
    Given the system has some conferencias stored
    When I upload the conferencias of "curriculo_conferencias.xml"
    Then the system has all the conferencias of the xml file

  Scenario: upload conferencias without a file
    Given I am at the publications menu
    When I select the "Conferencia" option at the program menu
    And I select the upload button at the conferencia page
    Then I'm still on conferencia page
    And the conferencias are not stored by the system

  @ignore
  Scenario: edit existing conference
    Given the system has conference entitled "IV Conference on Software Product Lines" with file name "SPLC.pdf"
    When I change the conference file from "SPLC.pdf" to "SPLC2.pdf"
    Then the conference "IV Conference on Software Product Lines" is properly updated by the system

  @ignore
  Scenario: edit existing conference web
    Given I am at the conference page 
    And the conference "IV Conference on Software Product Lines" is stored in the system with file name "SPLC.pdf"
    When I select to view "IV Conference on Software Product Lines" in resulting list
    And I change the conference file to "SPLC2.pdf"
    And I select the "Alterar" option in Conference Registration Page
    And A success message is displayed
    Then I am at Conference page

  @ignore
  Scenario: new invalid conference web (fields blank)
	Given I am at the conference registration page
	When I create the conference with some field blank
	Then the conference is not stored by the system because it is invalid
        And an error menssage is displayed

  @ignore
  Scenario: list existing conference
    Given the system has conference entitled "IV Conference on Software Product Lines" with file name "SPLC.pdf"
    When I view the conference list
    Then my conference list contains "IV Conference on Software Product Lines"

  @ignore
  Scenario: Order conference web by title
    Given I am at the conference page
    When I click on the column "title" at the conference list table
    Then a list of conferences stored by the system is displayed at the conference page by ascending alphabetic order

  @ignore
  Scenario: Order conference web by conference data
    Given I am at the conference page
    When I click on the column "Date" at the conference list table
    Then a list of conferences stored by the system is displayed at the conference page by publication ascending date order

  @ignore
  Scenario: Order  conference web by research line
    Given I am at the conference page
    When I click on the column "Research Line" at the conference list table
    Then a list of conferences stored by the system is displayed at the conference page by ascending alphabetic order

  @ignore
  Scenario: Go to search page
    Given I am at the conference page
    When I select the Search Conference option
    Then I am at the search conference page

  @ignore
  Scenario: Search for conference
    Given the system has conference entitled "IV Conference on Software Product Lines"
    When I search for the conferencia entitled "IV Conference on Software Product Lines"
    Then theres no change in the data stored by the system.

  @ignore
  Scenario: Search for conference web by date
    Given I am at the Seach Conference page
    And  the system has conference dated "2007"
    When I write "2007" at the date field
    And I select the option Serach for Conference at the conference page
    Then a list of all conferences containing that date will be presented in the conference screen

  Scenario: Publish a new article  
    Given I am at the article registration page  
    When I am filling in the author field
    And As I type the name, they come up suggestions of names containing the string entered as "And" may appear names like " Anderson " or " Candido "
    Then I choose the right name if it appears , otherwise we fill the whole field

  Scenario: new article
    Given I am at the publications
    When I select the "conferencia" option at the publications menu
    And I select the new article
    Then I can fill the article details

  Scenario: remove article
    Given I am at the publications menu
    When I select the "conferencia" option at the publications menu
    And a list of articles stored by the system is displayed at the conferencia page
    Then I select the desired article
    Then I can remove the article

  Scenario: new article from an existing conference
    Given the conference "I International Conference on software Engineering" is stored in the system
    When I type the letter "I" in the conference field to publish a new article
    Then the system suggests  "I International Conference on software Engineering"

  Scenario:  author suggestion for a new article (existing author)
    Given I am adding a new article
    When I type the first letter in the Author field
    Then a list is displayed suggesting names from Authors who already published an article
    And I select the name I want

  Scenario: Search conference web by existing Author
    Given I am at the Search Conference page
    When I write a name from an Author who already published an article at the Search field
    And I click on the Search button
    Then a list of all conferences with articles from that Author are displayed

  Scenario: System can suggest one author for new conferencia being created (good path)
    Given I am at Add new conferencia page
    And I had previously published only with "Júnior"
    When I try to fill "J" in Authors
    Then the system should suggest "Júnior" as an possible author
    When I select "Júnior"
    Then "Júnior" should be added in "Authors"

  Background: Start from the Add new conferencia page with conferencias yet published
    Given I am at Add new conferencia page
    And I had previously published with "Jorge", "Junior Lima" and "Fábio Jr"  
	
  Scenario: System can suggest some authors for new conferencia being created (good path)
    When I try to fill "J" in Authors
    Then the system should suggest "Jorge", "Junior Lima" and "Fábio Jr" as possible authors in lexicographical order
    When I select "Jorge" and other suggested authors
    Then the selected authors should be added in "Authors"

  Scenario: System can try to suggest some authors for new conferencia being created (bad path)
    When I try to fill "K" in Authors
    Then the system should suggest the latest 5 authors I had published as possible authors
    When I select any suggested author
    Then the selected author should be added in "Authors"

Scenario: Fill in the field "Author Name"
   Given I'm registering a new Article
   And I'm filling the field " Author Name"
   When I type "and" if there author names as " Anderson " or " Candido " registered in the system
   And the names " Anderson " and " Candido " will be suggested by the system
   Then I choose between " Anderso " and " Candido " or if it is not neither I fill with the desired name

Scenario: Remove Article Web
   Given I want to remove the article "A theory of software" with the file name "ATOS.pdf"
   When I click on "A theory of software" that is on the list of articles published in the conference page
   And I click with the mouse in the article "A theory of software"
   And appear the options to edit or remove the article
   Then I click the button to remove and the "A theory of software" is removed from the list of articles
   And the aquirvo "ATOS.pdf" is removed from the system
	
# voces podem criar cenários para ordenar a lista de conferencia, filtrar a lista, verificar se alguns campos podem ser opcionais, etc.

  Scenario: Search conference articles by Author web
    Given I am at the Conference Articles page
    And the system has some conference articles authored  by "Jose", among several publications
    When I write "J" at the Search field
    And I click on the Search button
    Then a list of all conference articles by "Jose" is displayed

  Scenario: Search for conferences which an Author have published web
    Given I am at the Conference page
    And an Author named "Junior" had published the articles "An Analysis and Survey of the Development of Mutation Testing", "A Systematic Survey of Program Comprehension through Dynamic Analysis", and "Engineering Privacy", for the conferences "International Conference on Software Engineering", "Information and Software Technology" and "International Symposium on Software Testing and Analysis"
    When I write "Junior" at the search field
    And I click on the search button
    Then a list of all conferences, composed by "International Conference on Software Engineering", "Information and Software Technology" and "International Symposium on Software Testing and Analysis", that "Junior" published an article is displayed




  Scenario: Search for conferences which an Author have published web
    Given I am at the Conference page
    And an Author named "Junior" had published the articles "An Analysis and Survey of the Development of Mutation Testing", "A Systematic Survey of Program Comprehension through Dynamic Analysis" and "Engineering Privacy" for the conferences "International Conference on Software Engineering", "Information and Software Technology" and "International Symposium on Software Testing and Analysis"
    When I write "Junior" at the search field
    And I click on the Search button
    Then a list of all conferences, composed by "International Conference on Software Engineering", "Information and Software Technology" and "International Symposium on Software Testing and Analysis", that "Junior" published an article is displayed

  Scenario: Remove conference article that does not exist
    Given the system has no conference article entitled "An Analysis and Survey of the Development of Mutation Testing"
    When I try to remove the conference article "An Analysis and Survey of the Development of Mutation Testing"
    Then nothing happens
