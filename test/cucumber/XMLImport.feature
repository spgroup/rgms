@i9n
Feature: XMLImport
  As a member of a research group
  I want to import a xml file
  So that the system register the corresponding publications in my profile

  Scenario: import invalid file
    Given the system has some publications stored
    When I upload the file "curriculo.pdf"
    Then no publication is stored by the system
    And the previously stored publications do not change

  Scenario: import unformatted xml file
    Given the system has some publications stored
    When I upload the unformatted file "curriculo.xml"
    Then no publication is stored by the system
    And the previously stored publications do not change

  Scenario: import publications without a file web
    Given I am at the "Import XML File" Page
    And the system has some publications stored
    When I click on "upload" without select a xml file
    Then the system outputs an error message

  Scenario: publications with same name and different type
    Given the system has a journal article entitled "An Abstract Equivalence Notion for Object Models"
    When  I upload the file "curriculo.xml" which contains a conference article entitled "An Abstract Equivalence Notion for Object Models"
    Then the conference article entitled "An Abstract Equivalence Notion for Object Models" is stored

  Scenario: duplicated publication
    Given the system has a journal article entitled "An Abstract Equivalence Notion for Object Models"
    When  I upload the file "curriculo.xml" which also contains a journal article entitled "An Abstract Equivalence Notion for Object Models"
    Then the journal article entitled "An Abstract Equivalence Notion for Object Models" is not stored twice

  Scenario: duplicated publications with conflicted details
    Given the system has a journal article entitled "An Abstract Equivalence Notion for Object Models" which authors are Rohit Gheyi, Tiago Massoni and Paulo Borba
    When  I upload the file "curriculo.xml" which contains a journal article entitled "An Abstract Equivalence Notion for Object Models" authored by R. Gheyi, T. Massoni and P. Borba
    Then the system offers the option to store both publications or to choose the author's name to be stored

  Scenario: duplicated publications with different details
    Given the system has a conference article entitled "An Abstract Equivalence Notion for Object Models" with pages "1-14"
    When  I upload the file "curriculo.xml" which contains a conference article entitled "An Abstract Equivalence Notion for Object Models" with locale "Recife"
    Then the system updates the previously stored article to include the locale "Recife"

  Scenario: import other author's publications web
    Given the system has the member "John" stored
    And I am logged in as "Maria" member
    And I am at the "Import XML File" Page
    When I upload the file "curriculo.xml" which contains John's publications
    Then no publication is stored by the system
    And the system outputs a warning message

  Scenario: import several publication types
    Given the system has some publications stored
    When  I upload the file "curriculo.xml" which contains a book, a book chapter, a conference paper, a dissertation,
          a tool, a journal article, a thesis and an orientation
    Then the system stores a book, a book chapter, a conference paper, a dissertation,
         a tool, a journal article, a thesis and an orientation

  #if ($ResearchLine)
  Scenario: import xml file that contains publications and research line
    Given the system has some publications stored
    When  I upload the file "curriculo.xml" which contains publications and a research line
    Then the system stores the publications and the research line
  #end

  #if($ResearchProject)
  Scenario: import xml file that contains publications and research project
    Given the system has some publications stored
    When  I upload the file "curriculo.xml" which contains publications and a research project
    Then the system stores the publications and the research project
  #end
