@i9n
Feature: journal article
  As a member of a research group
  I want to add, remove and modify journal articles I have published
  so that I can generate web pages and reports containing these articles

  Scenario: new article
    Given the system has no article entitled "A theory of software product line refinement"
    When I create the article "A theory of software product line refinement" with file name "TCS.pdf"
    Then the article "A theory of software product line refinement" is properly stored by the system

  Scenario: new invalid article
    Given the system has no article entitled "Algebraic reasoning for object-oriented programming"
    When I create the article "Algebraic reasoning for object-oriented programming" with file name "SCP.pdf"
    Then the article "Algebraic reasoning for object-oriented programming" is not stored by the system because it is invalid

  Scenario: duplicate article
    Given the article "A theory of software product line refinement" is stored in the system with file name "TCS-0.pdf"
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
    Given 	the system has article entitled "A theory of software product line refinement" with file name "TCS.pdf"
    When 	I delete the article "A theory of software product line refinement" 
    Then 	the article "A theory of software product line refinement" is properly removed by the system 
 
Scenario: list existing article
   Given 	the system has article entitled "A theory of software product line refinement" with file name "TCS.pdf"
   When 	I view the article list
   Then 	my article list contains "A theory of software product line refinement"
   
Scenario: edit existing article
   Given 	the system has article entitled "A theory of software product line refinement" with file name "TCS.pdf"
   When 	I edit the article title from "A theory of software product line refinement" to "A theory of software product line refinement REVIEWED"
   Then 	the article "A theory of software product line refinement" is properly updated by the system

Scenario: remove existing article web
    Given 	I am at the publications menu and the article "A theory of software product line refinement" is stored in the system with file name "TCS.pdf"
    And 	I select the "Periodico" option at the publications menu
    When 	I select to view "A theory of software product line refinement" in resulting list
    Then 	the details are showed and I can select the option to remove 

Scenario: list existing article web
    Given 	I am at the publications menu and the article "A theory of software product line refinement" is stored in the system with file name "TCS.pdf"
    When 	I select the "Periodico" option at the publications menu
    Then 	my resulting articles list contains "A theory of software product line refinement"

Scenario: edit existing article web
    Given 	I am at the publications menu and the article "A theory of software product line refinement" is stored in the system with file name "TCS.pdf"
    And 	I select the "Periodico" option at the publications menu
   	When 	I select to view "A theory of software product line refinement" in resulting list and I change the article title to "REVIEWED"
    Then 	I can select the "Alterar" option 

