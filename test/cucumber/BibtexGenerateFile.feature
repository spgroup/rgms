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

  Scenario: Generate new BibTex from a subset of publications
    Given I am on the "Publications" menu
    When I select the "Generate BibTex" option
    Then I can select which files to include in the BibTex generation

  Scenario: Publications with multiple authors must have authors' names separated by an and
    Given I have an article with multiple authors
    When I generate a BibTex file
    Then the BibTex file author field must have the authors' names separated by an and