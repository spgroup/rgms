@i9n
Feature: all bibtex
  As a member
  I want to view all publications i have published in format of bibtex

  Scenario: show all bibtex
    Given I am at the publications
    When I select the export bibtex file option at the publications menu
    And I select Generate All BibTex option at the export bibtex page
    Then I can see the bibtex details

  Scenario: Duplicate citation-key generation
    Given I have an article named "A theory of software product line refinement"
    And I have another article named "Modularity analysis of use case implementations"
    When I generate a BibTex file from articles named "A theory of software product line refinement" and "Modularity analysis of use case implementations"
    Then the BibTex file has unique citation-keys for the articles "A theory of software product line refinement" and "Modularity analysis of use case implementations"

  Scenario: Generate new BibTex from a subset of publications web
    Given I am on the "Publications" menu
    When I select the publications "A theory of software product line refinement" and "Modularity analysis of use case implementations"
    And I click on the "Generate BibTex" option
    Then the BibTex details are showed
    And It only contains the articles "A theory of software product line refinement" and "Modularity analysis of use case implementations"

  Scenario: Publications with multiple authors must have authors' names separated by and
    Given I have an article named "A theory of software product line refinement" with multiple authors
    When I generate a BibTex file from the article named "A theory of software product line refinement"
    Then the BibTex file author field must have the authors' names separated by "and"

 #if ($InvalidEntryOfBibtex)
  Scenario: Tags of entry of BibTex are not separated by commas
    Given: I am logged into the system
    And: I am at the "Main menu"
    And: A BibTeX entry is "@article{mrx05
                            auTHor = "Mr. X",
                            Title = {Something Great},
                            publisher = "nob" # "ody",
                            YEAR = 2005,
                            }"
    When: I click to "Generate BibTex"
    Then: I see an error message

  Scenario: Tags of entry of BibTex are incompatible with type of publication chosen
    Given: I am logged int the system
    And: I am at the "Main menu"
    And: A BibTex entry is "@article{mrx05,
                            auTHor = "Mr. X",
                            Title = {Something Great},
                            publisher = "nob" # "ody",
                            YEAR = 2005,
                            chapter = 8,
                            }"
    When: I click to "Generate BibTex"
    Then: I see an error message

  Scenario: Lack mandatory tags in entry of BibTex with type of publication chosen
    Given: I am logged into the system
    And: I am at the "Main menu"
    And: A BibTeX entry is "@article{mrx05,
                            auTHor = "Mr. X",
                            Title = {Something Great},
                            publisher = "nob" # "ody",
                            }"
    When: I click to "Generate BibTex"
    Then: I see an error message
 #end

 #if ($CorrectEntryOfBibtex)
  Scenario: BibTex file is generated
    Given: I am logged into the system
    And: I am at the "Main menu"
    And: A Bibtex entry is "@article{mrx05,
                            auTHor = "Mr. X",
                            Title = {Something Great},
                            publisher = "nob" # "ody",
                            YEAR = 2005,
                            }"
    When: I click to "Generate BibTex"
    Then: a BibTex file is generated
 #end