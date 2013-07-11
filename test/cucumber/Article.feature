@i9n
Feature: journal article
  As a member of a research group
  I want to add, remove and modify journal articles I have published
  so that I can generate web pages and reports containing these articles




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
    When    I edit the article title from "A theory of software product line refinement" to "A theory of software product line refinement REVIEWED"
    Then    the article "A theory of software product line refinement" is properly updated by the system

  Scenario: remove existing article web
    Given   I am at the articles page and the article "A theory of software product line refinement" is stored in the system with file name "TCS-77.pdf"
    When    I select to view "A theory of software product line refinement" in resulting list
    Then    the article details are showed and I can select the option to remove
    And     the article "A theory of software product line refinement" is properly removed by the system

  Scenario: list existing article web
    Given    I am at the articles page and the article "A theory of software product line refinement" is stored in the system with file name "TCS-88.pdf"
    Then    my resulting articles list contains "A theory of software product line refinement"

  Scenario: edit existing article web
    Given    I am at the articles page and the article "A theory of software product line refinement" is stored in the system with file name "TCS-99.pdf"
    When    I select to view "A theory of software product line refinement" in resulting list and I change the article title to "REVIEWED"
    Then    I can select the "Alterar" option

if( $Twitter )
  Scenario: Add a new article twitting it
    Given I am logged as "admin" and at the Add Article Page
    When I try to create an article named as "Empirical Studies in Product Line"
    And I click on Share it in Twitter with "rgms_ufpe" and "rgmsadmin2013"
    Then A twitter is added to my twitter account regarding the new article "Empirical Studies in Product Line"

  Scenario: Add a new article with twitter, but don't twitte it
    Given I am logged as "admin" and at the Add Article Page
    When I try to create an article named as "Empirical Studies in Product Line 2"
    Then No twitte should be post about "Empirical Studies in Product Line 2"
end

if( $Facebook )
  Scenario: Add a new article and post it in the facebook
    Given I am logged as "admin" and at the Add Article Page
    When I try to create an article named as "Empirical Studies in Product Line 3"
    And I click on share it on Facebook, with login "rgms.ufpe@gmail.com", password "rgmsadmin2013", and message "New Article"
    Then A facebook message is added for "Empirical Studies in Product Line 3"

  Scenario: Add a new article and post it in the facebook
    Given I am logged as "admin" and at the Add Article Page
    When I try to create an article named as "Empirical Studies in Product Line 4"
    Then No facebook message is added for "Empirical Studies in Product Line 4"
end

