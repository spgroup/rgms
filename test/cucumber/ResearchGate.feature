@i9n
#if($researchGate)
Feature: Research Gate
  As a member of a research group
  I want to export my articles to Research Gate
  So that I can see my files on Research Gate website

  Scenario: connect to Research Gate
    Given I am at the publications menu
	And my Research Gate credentials were not given to the system previously
    When I select the "Export bibtex file" option at the publications menu
    And I select "Export to Research Gate" option at the export bibtex page
    Then I can fill my Research Gate credentials

  Scenario: view article on Research Gate website
    Given I am at the export to research gate page
    And the system has article entitled "A theory of software product line refinement" with file name "TCS-0.pdf"
    And the system has article entitled "Algebraic reasoning for object-oriented programming" with file name "SCP.pdf"
    And I had filled the Research Gate credentials with valid credentials
    When I click on the button Export
    Then I can see the articles "A theory of software product line refinement" and "Algebraic reasoning for object-oriented programming" on the Research Gate website
#end