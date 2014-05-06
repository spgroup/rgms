@i9n
#if ($researchGate)
Feature: Research Gate
  As a member of a research group
  I want to export a bibtex file to research gate
  So that I can see my files on Research Gate website

  Scenario: connect to Research Gate
    Given I am at the publications
    When I select the "Export bibtex file" option at the publications menu
    And I select "Export to Research Gate" option at the export bibtex page
    Then I can fill my Research Gate credentials

  Scenario: view article on Research Gate website
    Given I am at the export to research gate page
    And I have a bibtex file with the article "A theory of software product line refinement"
    When I fill the Research Gate credentials with valid credentials
    Then I can see the article "A theory of software product line refinement" on the Research Gate website

  Scenario: export file to Research Gate
    Given I have a bibtex file with the article "A theory of software product line refinement"
    When I export the bibtex file to Research Gate
    Then The article "A theory of software product line refinement" is stored on Research Gate database
#end