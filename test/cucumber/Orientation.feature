@i9n
Feature: orientations
  As a admin of the system
  I want to add, remove and modify orientations I coached
  so that I can generate web pages and reports containing these orientations

  Scenario: new orientation
    Given the system has no orientations entitled "The Book is on the table 2"
    When I create a orientation for the thesis "The Book is on the table 2"
    Then the orientation "The Book is on the table 2" is properly stored by the system

  Scenario: remove existing orientation
    Given   the system has thesis entitled "The Book is on the table" supervised for someone
    When    I delete the orientation for "The Book is on the table"
    Then    the orientation for "The Book is on the table" is properly removed by the system

  Scenario: create orientation web
    Given I am at the create orientation page
    When I fill the orientation title with "The Book is on the table"
    Then I am on the orientation show page

  Scenario: edit existing orientation web
    Given I am at the orientation page and the orientation "The Book is on the table" is stored in the system
    When I select to view orientation "The Book is on the table" in resulting list
    And I change the orientation tituloTese to "Hexa"
    And I select the "Alterar" option
    Then I am on the orientation show page

  Scenario: create duplicated orientation
    Given I am at the create orientation page
    And the system has thesis entitled "The Book is on the table" supervised by someone
    When I fill the orientation title with "The Book is on the table"
    Then I am on the orientation show page with the error message

#if ($XMLUpload)
    Scenario: upload orientation with a file
        Given the system has some orientations stored
        When I upload a new orientation "testelattes.xml"
        Then the system has more orientations now

    Scenario: upload orientations with a file
        Given I am at the publications menu
        When I select the "Orientation" option at the program menu
        And I select the upload button at the orientations page
        Then I'm still on orientations page
        And the orientations are not stored by the system
#end

