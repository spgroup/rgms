@i9n
Feature: all bibtex
  As a member
  I want to view all publications i have published in format of bibtex

  Scenario: show all bibtex
    Given I am at the publications
    When I select the export bibtex file option at the publications menu
    And I select Generate All BibTex option at the export bibtex page
    Then I can see the bibtex details
    
  #if($all bibtex)
  Scenario: search all publications of a specific topic
    Given I am at the publications
    When I select the export bibtex file option at the publications menu
    And I type "Topic" at the search toolbar in export bibtex page
    And I select "Generate BibTex" option at the export bibtex page
    Then I can see all the publications related with "Topic"
  #end
