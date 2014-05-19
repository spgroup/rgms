@i9n
Feature: XMLImport
  As a member of a research group
  I want to import a xml file
  So that the system register the corresponding publications in my profile
  	
 <!-- checar duplicacoes -->
  Scenario: upload publications without a file
    Given I am at the publications menu
    When I select the "Import XML File" option at the publications menu
    And I select the upload button at the XML import page
    Then I'm still on XML import page
    And the publications are not stored by the system

  Scenario: update a duplicate file
    Given I am at the publications menu
    And I select the "Import XML File" option at the publications menu
    When I select the button upload
    And I upload the publications of file "SPL.xml" stored in the system
    Then the file "SPL.xml" is not stored twice

  Scenario: upload a different extension file
    Given I am at the publications menu
    And I select the "Import XML File" option at the publications menu
    When I select the button upload
    And I upload the publications of file "SPL.xmi", "SPL.pdf" or "SPL.doc"
    Then the publications are not stored by the system
<!-- duplicoes terminam aqui-->
	
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
  Scenario: import xml file that contains a research line
    Given the system has no research line named as "Modularidade Emergente"
    When  I upload the file "curriculo.xml" which contains a research line named as "Modularidade Emergente"
    Then the research line named as "Modularidade Emergente" is stored
  #end

  #if($ResearchProject)
  Scenario: import xml file that contains a research project
    Given the system has no research project named as "Modularização Emergente para Linhas de Produtos de Software"
    When  I upload the file "curriculo.xml" which contains a research project named as "Modularização Emergente para Linhas de Produtos de Software"
    Then the research project named as "Modularização Emergente para Linhas de Produtos de Software" is stored
  #end

  #if($Orientation)
  Scenario: import xml file that contains publications and orientation
    Given the system has no master's orientation entitled "Structuring Adaptive Aplications using AspectJ"
    When  I upload the file "curriculo.xml" which contains a master's orientation entitled "Structuring Adaptive Aplications using AspectJ"
    Then the master's orientation entitled "Structuring Adaptive Apllications using AspectJ" is stored
  #end
>>>>>>> 659bdf33f850f1118fbbe43477bd44c4d2a86f0c
