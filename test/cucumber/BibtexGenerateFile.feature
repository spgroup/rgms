@i9n
#if ($bibtexGenerateFile)
Feature: all bibtex
  As a member of a research group
  I want to view all publications i have published in format of bibtex
  So that I can export this file

  Scenario: show all bibtex Web
    Given I am at the publications menu
    When I select the "Export bibtex file" option
    And I select "Generate All BibTex" option at the export bibtex page
    Then I can see the bibtex details

  Scenario: generate bibtex file
    Given I created one article named "A theory of software product line refinement"
    And the article "A theory of software product line refinement" is stored with the file name "TCS.pdf"
    When I export all my articles to a bibtex file
    Then The article "A theory of software product line refinement" is inside the bibtex file
#end