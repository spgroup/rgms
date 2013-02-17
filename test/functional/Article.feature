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
