@i9n
#if($researchGate)
Feature: Research Gate
  As a member of a research group
  I want to export my articles to Research Gate
  So that I can see my files on Research Gate website

  Scenario: connect to Research Gate
    Given I am at the publications menu
	And my Research Gate credentials were not given to the system previously
    When I select the "Export Bibtex File" option at the publications menu
    And I select "Generate All BibTex" option at the export bibtex page
    And I select "Export to Research Gate" option at the export bibtex page
    Then I can fill my Research Gate credentials

#end