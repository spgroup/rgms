@i9n
Feature: XMLImport
  As a member of a research group
  I want to import a XML file
  So that the system register the corresponding publications

#if($XMLImport)

  #Controller tests
  Scenario: upload publications with a XML file
    Given the system has no publication entitled "A theory of software product line refinement"
    When I upload the XML file "curriculo_publications.xml" containing a publication entitled "A theory of software product line refinement"
    Then the publication "A theory of software product line refinement" is properly stored by the system

  Scenario: upload publications without a XML file
    Given the system has no publication entitled "A theory of software product line refinement"
    When I do not upload any file
    Then no publications are stored by the system

  Scenario: upload publications with a invalid file
    Given the system has no publication entitled "A theory of software product line refinement"
    When I upload an invalid XML file
    Then no publications are stored by the system

  Scenario: upload duplicate publications with a XML file
    Given the system has a publication entitled "A theory of software product line refinement"
    When I upload the XML file "curriculo_publications.xml" containing a publication entitled "A theory of software product line refinement"
    Then the publication "A theory of software product line refinement" is not stored twice

  #GUI tests
  Scenario: upload publications with a XML file
    Given I am at the "Import XML File" page
    And the system has no publication entitled "A theory of software product line refinement"
    When I select the XML file "curriculo_publications.xml" containing a publication entitled "A theory of software product line refinement"  through the button "Escolher arquivo"
    And I click the button "Enviar"
    Then I am still at the "Import XML File" page
    And the publication "A theory of software product line refinement" is properly stored by the system

  Scenario: upload publications without a XML file
    Given I am at the "Import XML File" page
    And the system has no publication entitled "A theory of software product line refinement"
    When I do not select any file through the button "Escolher arquivo"
    And I click the button "Enviar"
    Then no publications are stored by the system
    And the system shows the message "No valid XML file was uploaded."

  Scenario: upload publications with a invalid file
    Given I am at the "Import XML File" page
    And the system has no publication entitled "A theory of software product line refinement"
    When I select an invalid XML file through the button "Escolher arquivo"
    And I click the button "Enviar"
    Then no publications are stored by the system
    And the system shows the message "No valid XML file was uploaded."

  Scenario: upload duplicate publications with a XML file
    Given I am at the "Import XML File" page
    And the system has a publication entitled "A theory of software product line refinement"
    When I select the XML file "curriculo_publications.xml" containing a publication entitled "A theory of software product line refinement" through the button "Escolher arquivo"
    And I click the button "Enviar"
    Then the publication "A theory of software product line refinement" is not stored twice

#end