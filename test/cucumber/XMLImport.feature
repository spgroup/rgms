@i9n
Feature: XMLImport
  As a member of a research group
  I want to import a xml file
  So that the system register the corresponding publications in my profile

  Scenario: import invalid file web
    Given I am at the "Import XML File" Page
    When I select the "upload" button
    And I upload the file "cv.pdf"
    Then the system outputs an error message
    And no publications is stored by the system
    And the previously stored publications do not change

  Scenario: import invalid file
    Given the system has some publications stored
    When I upload the file "cv.pdf"
    Then no publication is stored by the system
    And the previously stored publications do not change

  Scenario: import unformatted xml file
    Given the system has some publications stored
    When I upload the unformatted file "cv1.xml"
    Then no publication is stored by the system
    And the previously stored publications do not change

  Scenario: import publications without a file web
    Given I am at the "Import XML File" Page
    And the system has some publications stored
    When I click on "upload" without select a xml file
    Then the system outputs an error message
    And the previously stored publications do not change

  Scenario: publications with same name and different type
    Given the system has a journal article entitled "An Abstract Equivalence Notion for Object Models" authored by me
    When  I upload the file "cv2.xml" which contains a conference article entitled "An Abstract Equivalence Notion for Object Models" authored by me
    Then the conference article entitled "An Abstract Equivalence Notion for Object Models" is stored by the system
    And the previously stored journal article does not change

  Scenario: duplicated publication with equal details
    Given the system has a journal article entitled "An Abstract Equivalence Notion for Object Models" with some extra information
    When  I upload the file "cv3.xml" which also contains a journal article entitled "An Abstract Equivalence Notion for Object Models" with the same extra information
    Then the journal article entitled "An Abstract Equivalence Notion for Object Models" is not stored twice

  Scenario: duplicated publications with conflicted details
    Given the system has a journal article entitled "An Abstract Equivalence Notion for Object Models" with year "2003" that is authored by me
    When  I upload the file "cv4.xml" which contains a journal article entitled "An Abstract Equivalence Notion for Object Models" authored by me with year "2004"
    Then the system offers the option to store both publications or to choose the year to be stored
  <!-- a ultima clausula nao esta coerente com a logica de ser um cenario de controller. poderia ser um cenario de gui ou então é preciso reescrever
  pensando na implementação. lembro que foi discutido que iria redirecionar para outra view, para perguntar se o usuario quer manter ambas as publicações ou escolher o ano a ser armazenado, mas não
  lembro mais, no fim das contas, como fazer isso -->

  Scenario: duplicated publications with different details
    Given the system has a conference article entitled "An Abstract Equivalence Notion for Object Models" with pages "1-14" that is authored by me
    When  I upload the file "cv5.xml" which contains a conference article entitled "An Abstract Equivalence Notion for Object Models" with locale "Recife" that is also authored by me
    Then the system updates the previously stored article to include the locale "Recife"

  #if ($ResearchLine)
  Scenario: import xml file that contains a research line
    Given the system has no research line named as "Modularidade Emergente"
    When  I upload the file "cv6.xml" which contains a research line named as "Modularidade Emergente"
    Then the research line named as "Modularidade Emergente" is stored by the system
  #end

  #if($ResearchProject)
  Scenario: import xml file that contains a research project
    Given the system has no research project named as "Modularização Emergente para Linhas de Produtos de Software"
    When  I upload the file "cv7.xml" which contains a research project named as "Modularização Emergente para Linhas de Produtos de Software"
    Then the research project named as "Modularização Emergente para Linhas de Produtos de Software" is stored by the system
  #end

  #if($Orientation)
  Scenario: import xml file that contains publications and orientation
    Given the system has no master's orientation entitled "Structuring Adaptive Aplications using AspectJ"
    When  I upload the file "cv8.xml" which contains a master's orientation entitled "Structuring Adaptive Aplications using AspectJ"
    Then the master's orientation entitled "Structuring Adaptive Aplications using AspectJ" is stored by the system
  #end
