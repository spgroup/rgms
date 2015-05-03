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
    And I have an article named "A new approach to large-scale software development"
    When I generate a BibTex file
    Then the BibTex file has unique citation-keys for each article

  Scenario: Generate new BibTex from a subset of publications web
    Given I am on the "Publications" menu
    When I select a subset of publications
    And I click on the "Generate BibTex" option
    Then the system generates a BibTex file containing only the publications from the selected subset

  Scenario: Publications with multiple authors must have authors' names separated by and
    Given I have an article with multiple authors
    When I generate a BibTex file
    Then the BibTex file author field must have the authors' names separated by "and"

 #if ($InvalidEntryOfBibtex)
  Scenario: Tags of entry of BibTex are not separated by commas
    Given: I am logged into the system
    And: I am at the BibTexGenerateFile page
    When: I click to "Generate BibTex manually"
    And: A BibTeX entry is "@article{mrx05
                            auTHor = "Mr. X",
                            Title = {Something Great},
                            publisher = "nob" # "ody",
                            YEAR = 2005,
                            }"
    And: I click on the other button "Generate BibTex"
    Then: I see an error message

  Scenario: Tags of entry of BibTex are incompatible with type of publication chosen
    Given: I am logged into the system
    And: I am at the BibTexGenerateFile page
    When: I click to "Generate BibTex manually"
    And: A BibTex entry is "@article{mrx05,
                            auTHor = "Mr. X",
                            Title = {Something Great},
                            publisher = "nob" # "ody",
                            YEAR = 2005,
                            chapter = 8,
                            }"
    And: I click on the other button "Generate BibTex"
    Then: I see an error message

  Scenario: Lack mandatory tags in entry of BibTex with type of publication chosen
    Given: I am logged into the system
    And: I am at the BibTexGenerateFile page
    When: I click to "Generate BibTex manually"
    And: A BibTeX entry is "@article{mrx05,
                            auTHor = "Mr. X",
                            Title = {Something Great},
                            publisher = "nob" # "ody",
                            }
    And: I click on the other button "Generate BibTex"
    Then: I see an error message
 #end

 #if ($CorrectEntryOfBibtex)
  Scenario: BibTex file is generated manually
    Given: I am logged into the system
    And: I am at the BibTexGenerateFile page
    When: I click to "Generate BibTex manually"
    And: A Bibtex entry is "@article{mrx05,
                            auTHor = "Mr. X",
                            Title = {Something Great},
                            publisher = "nob" # "ody",
                            YEAR = 2005,
                            }"
    And: I click on the other button "Generate BibTex"
    Then: a BibTex file is generated
 #end