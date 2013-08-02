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
    When I create the article "Algebraic reasoning for object-oriented programming" without file
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
    Given   the system has article entitled "A theory of software product line refinement" with file name "TCS-44.pdf"
    When    I delete the article "A theory of software product line refinement"
    Then    the article "A theory of software product line refinement" is properly removed by the system

  Scenario: list existing article
    Given   the system has article entitled "A theory of software product line refinement" with file name "TCS-55.pdf"
    When    I view the article list
    Then    my article list contains "A theory of software product line refinement"

  Scenario: edit existing article
    Given    the system has article entitled "A theory of software product line refinement" with file name "TCS-66.pdf"
    When    I edit the article title from "A theory of software product line refinement" to "A theory of software product line refinement REVIEWED" as the article journal from "Theoretical Computer Science" to "The Big Bang Theory"
    Then    the article "A theory of software product line refinement" is properly updated by the system

  Scenario: remove existing article web
    Given   I am at the articles page and the article "A theory of software product line refinement" is stored in the system with file name "TCS-77.pdf"
    When    I select to view "A theory of software product line refinement" in resulting list
    Then    the article details are showed and I can select the option to remove
    And     the article "A theory of software product line refinement" is properly removed by the system

 Scenario: list existing article web
    Given    I am at the articles page and the article "A theory of software product line refinement" is stored in the system with file name "TCS-88.pdf" and journal "Theoretical Computer Science"
    Then    my resulting articles list contains "A theory of software product line refinement"

  Scenario: edit existing article web
    Given    I am at the articles page and the article "A theory of software product line refinement" is stored in the system with file name "TCS-99.pdf"
    When    I select to view "A theory of software product line refinement" in resulting list and I change the article title to "REVIEWED" and the journal article from "Theoretical Computer Science" to "The Big Bang Theory"
    Then    I can select the "Alterar" option

  Scenario: Add a new article twitting it
    Given I am logged as "admin" and at the Add Article Page
    When I try to create an article named as "A theory of software product line refinement 1" with filename "TCS-101.pdf"
    And I click on Share it in Twitter with "rgms_ufpe" and "rgmsadmin2013"
    Then A twitter is added to my twitter account regarding the new article "A theory of software product line refinement 1"

  Scenario: Add a new article with twitter, but don't twitte it
    Given I am logged as "admin" and at the Add Article Page
    When I try to create an article named as "Empirical Studies in Product Line 2" with filename "TCS.pdf"
    Then No twitte should be post about "Empirical Studies in Product Line 2"

  Scenario: Add a new article and post it in the facebook
    Given I am logged as "admin" and at the Add Article Page
    When I try to create an article named as "A theory of software product line refinement 2" with filename "TCS-100.pdf"
    And I click on share it on Facebook, with login "rgms.ufpe@gmail.com", password "rgmsadmin2013", and message "New Article"
    Then A facebook message is added for "A theory of software product line refinement 2"

  Scenario: Add a new article but don't post it in the facebook
    Given I am logged as "admin" and at the Add Article Page
    When I try to create an article named as "Empirical Studies in Product Line 4" with filename "TCS.pdf"
    Then No facebook message is added for "Empirical Studies in Product Line 4"
