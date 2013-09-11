@i9n
Feature: XMLImport
  As a member of a research group
  I want to import a xml file
  So that the system register the corresponding publications

  @ignore
  Scenario: upload publications with a file
    Given the system has some publications stored
    When I upload the publications of "curriculo_publications.xml"
    Then the system has all the publications of the xml file

  Scenario: upload publications without a file
    Given I am at the publications menu
    When I select the "Import XML File" option at the publications menu
    And I select the upload button at the XML import page
    Then I'm still on XML import page
    And the publications are not stored by the system
