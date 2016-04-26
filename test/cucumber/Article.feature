@i9n
Feature: journal article
  As a member of a research group
  I want to add, remove and modify journal articles I have published
  so that I can generate web pages and reports containing these articles

  Scenario: new article
    Given the system has no article entitled "A theory of software product line refinement"
    When I create the article "A theory of software product line refinement" with file name "TCS.pdf"
    Then the article "A theory of software product line refinement" is properly stored by the system

  Scenario: new invalid article (number field blank)
    Given the system has no article entitled "Algebraic reasoning for object-oriented programming"
    When I create the article "Algebraic reasoning for object-oriented programming" with file name "SCP.pdf" with the "number" field blank
    Then the article "Algebraic reasoning for object-oriented programming" is not stored by the system because it is invalid

  Scenario: duplicate article
    Given the system has article entitled "A theory of software product line refinement" with file name "TCS-0.pdf"
    When I create the article "A theory of software product line refinement" with file name "TCS-1.pdf"
    Then the article "A theory of software product line refinement" is not stored twice

  Scenario: alternative duplicate article
    Given the system has no article entitled "A theory of software product line refinement"
    When I create the article "A theory of software product line refinement" with file name "TCS-2"
    And I create the article "A theory of software product line refinement" with file name "TCS-3"
    Then the article "A theory of software product line refinement" is not stored twice

  Scenario: new article web
    Given I am at the publications menu
    When I select the "Periodico" option at the publications menu
    And I select the new article option at the article page
    Then I can fill the article details

  Scenario: remove existing article
    Given the system has article entitled "A theory of software product line refinement" with file name "TCS-44.pdf"
    When I delete the article "A theory of software product line refinement"
    Then the article "A theory of software product line refinement" is properly removed by the system

  Scenario: list existing article
    Given the system has article entitled "A theory of software product line refinement" with file name "TCS-55.pdf"
    When I view the article list
    Then my article list contains "A theory of software product line refinement"

  Scenario: edit existing article
    Given the system has article entitled "A theory of software product line refinement" with file name "TCS-66.pdf"
    When I edit the article title from "A theory of software product line refinement" to "A theory of software product line refinement REVIEWED"
    Then the article "A theory of software product line refinement" is properly updated by the system

  Scenario: remove existing article web
    Given I am at the articles page and the article "A theory of software product line refinement" is stored in the system with file name "TCS-77.pdf"
    When I select to view "A theory of software product line refinement" in resulting list
    And I select the option to remove in show page
    Then the article "A theory of software product line refinement" is properly removed by the system

  Scenario: list existing article web
    Given I am at the articles page  
    And the article "A theory of software product line refinement" is stored in the system with file name "TCS-88.pdf"
    When I select to view the article list
    Then my resulting articles list contains "A theory of software product line refinement"

  Scenario: edit existing article web
    Given I am at the articles page and the article "A theory of software product line refinement" is stored in the system with file name "TCS-99.pdf"
    When I select to view "A theory of software product line refinement" in resulting list
    And I change the article title to "REVIEWED"
    And I select the "Alterar" option in Article Show Page
    Then I am at Article show page

  Scenario: Add a new article tweeting it
    Given I am logged as "admin"
    And I am at the Article Page
    When I try to create an article named as "A theory of software product line refinement 1" with filename "TCS-101.pdf"
    And I click on Share it in Twitter with "rgms_ufpe" and "rgmsadmin2013"
    Then A tweet is added to my twitter account regarding the new article "A theory of software product line refinement 1"

  Scenario: Add a new article and try to tweet the article using wrong login and password
    Given I am logged as "admin"
    And I am at the Article Page
    When I try to create an article named as "Teoria de Sw" with filename "TCS-102.pdf"
    And I click on Share it in Twitter with "fake" and "fake2013"
    Then No tweet should be post about "Teoria de Sw"

  Scenario: Add a new article with twitter, but don't tweet it
    Given I am logged as "admin"
    And I am at the Article Page
    When I try to create an article named as "Empirical Studies in Product Line 2" with filename "TCS.pdf"
    Then No tweet should be post about "Empirical Studies in Product Line 2"

  Scenario: Add a new article and post it in the facebook
    Given I am logged as "admin"
    And   I am at the Add Article Page
    When  I try to create an article named as "A theory of software product line refinement 2" with filename "TCS-01.pdf"
    And   I click on Share on Facebook
    Then  A facebook message was posted

  Scenario: Add a new article but don't post it in the facebook
    Given I am logged as "admin"
    And   I am at the Add Article Page
    When  I try to create an article named as "Empirical Studies in Product Line 4" with filename "TCS-02.pdf"
    Then  No facebook message was posted

  Scenario: Post an existing article on facebook
    Given   the system has article entitled "A theory of software product line refinement" with file name "TCS-03.pdf"
    When    I share the article entitled "A theory of software product line refinement" on facebook
    Then    A facebook message was posted

  Scenario: Post an existing article on facebook web
    Given   I am at the articles page and the article "A theory of software product line refinement 3" is stored in the system with file name "TCS-04.pdf"
    When    I select to view "A theory of software product line refinement 3" in resulting list
    And     I click on Share on Facebook
    Then    A facebook message was posted

#if ($contextualInformation)
  Scenario: Add a new article with user data already filled by default
    Given I am at the publications menu
    When I select the "Periodico" option at the publications menu
    And I select the new article option at the article page
    Then I see my user listed as an author member of article by default
#end
  Scenario: upload article with a file
    Given the system has some articles stored
    When I upload the articles of "curriculo.xml"
    Then the system has all the articles of the xml file

  Scenario: upload articles without a file
    Given I am at the Article Page
    And I select the upload button at the article page
    Then I'm still on article page
    And the articles are not stored by the system

#if($Report)  
  Scenario:	report existing article
	Given the system has article entitled "A theory of software product line refinement" with file name "TCS-1401.pdf"
	When the system reports the existing articles
	Then the system report contains "A theory of software product line refinement" article
#end

  Scenario:	list existing articles in alphabetical order of title
	Given the system has article entitled "A theory of software product line refinement" with file name "TCS-1401.pdf"
	And the system has article entitled "Modularity analysis of use case implementations" with file name "MACI.pdf"
	When the system orders the article list by title
	Then the system article list content is not modified

  Scenario:	list existing articles ordered by publication date
	Given the system has article entitled "A theory of software product line refinement" with file name "TCS-1401.pdf" dated on "22 October 2011"
	And the system has article entitled "Modularity analysis of use case implementations" with file name "MACI.pdf" dated on "05 August 2010"
	When the system orders the article list by publication date
	Then the system article list content is not modified

  Scenario: new invalid article (title field blank)
	Given the system has no article without title and with filename "TCS-01.pdf"
	When I create the article with filename "TCS-01.pdf" and with the title field blank
	Then the article with blank title and with filename "TCS-01.pdf" field is not stored by the system

  Scenario: filter existing articles by author
	Given the system has some articles authored by "Paulo Borba"
	When the system filter the articles authored by author "Paulo Borba"
	Then the system article list content is not modified

  Scenario: remove multiple articles
	Given the system has 3 articles entitled "A theory of software product line refinement" with file name "TCS-01.pdf", "Algebraic reasoning for object-oriented programming" with file name "AROOP-02.pdf" and "Modularity analysis of use case implementations" with file name "MACI-03.pdf"
	When I remove the articles "A theory of software product line refinement" and "Modularity analysis of use case implementations"
	Then the system removes the articles "A theory of software product line refinement" and "Modularity analysis of use case implementations"
	And the system contains the "Algebraic reasoning for object-oriented programming" article

#if($Report)  
  Scenario:	report existing article web
	Given I am at the articles page
	And the article "A theory of software product line refinement" is stored in the system with file name "TCS-88.pdf"
	When I select to view the report of articles
	Then my resulting report of articles contains "A theory of software product line refinement"
#end

Scenario: list existing articles in alphabetical order of title web
	Given I am at the articles page 
	And the system has some articles created
	When I select to view the list of articles
	And I select to order the list of articles by "title"
	Then my article list shows the articles ordered by "title"

  Scenario: list existing articles ordered by publication date web
	Given I am at the articles page 
	And the system has some articles created
	When I select to view the list of articles
	And I select to order the list of articles by "publication date"
	Then my article list shows the articles ordered by "publication date"

  Scenario: new invalid article web (title field blank)
	Given I am at the new article page
	When I fill all article information except the title field
	And I select to create the article
	Then an error message is showed for the title field

  Scenario: filter existing articles by author web
	Given I am at the articles page 
	And I create some articles authored by "Paulo Borba"
	When I select to view the list of articles
	And I select to filter the list of articles by author "Paulo Borba"
	Then my article list shows only the articles authored by "Paulo Borba"

  Scenario: remove multiple articles web
	Given I am at the articles page 
	And I create 3 articles entitled "A theory of software product line refinement" with file name "TCS-01.pdf", "Modularity analysis of use case implementations" with file name "MACI-03.pdf" and "Algebraic reasoning for object-oriented programming" with file name "AROOP-02.pdf"
	When I select to view the list of articles	
	And I mark multiple articles to be removed
	And I select to remove the selected articles
	Then my resulting articles list contains "Modularity analysis of use case implementations"
	
# Paulo Borba: voces podem criar cenários para ordenar a lista, filtrar a lista
# Paulo Borba: verificar se alguns campos podem ser opcionais, etc.
