@i9n
#if(bibtexGenerateFile)
Feature: all bibtex
  As a member of a research group
  I want to view all publications I have published in bibtex format
  So that I can export this file

  Scenario: show all bibtex Web
    Given I am logged as admin
    And I created one article named "A theory of software product line refinement"
    And I created one conferencia named "IV Conference on Software Product Lines"
    And I am at the publications menu
    When I select the "Export Bibtex File" option at the publications menu
    And I select "Generate All BibTex" option at the export bibtex page
    Then I can see the publication "A theory of software product line refinement" inside the bibtex
    And I can see the publication "IV Conference on Software Product Lines" inside the bibtex
    And I can export this bibtex to a file

#end