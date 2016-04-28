@i9n
Feature: research group

 	Scenario: new researchgroup
    Given the system has no research group entitled "modcs" stored in the system
    When I create a research group named "modcs" with the description "modcs research group"
    Then the research group "modcs" is properly stored by the system
    
    Scenario: new research group with no name. 
    Given the system has no research group entitled "" stored in the system
    When I create a research group named "" with the description "modcs research group"
    Then the research group is not stored in the system because it has no name
    
    Scenario: new research group with no description. 
    Given the system has no research group entitled "modcs-20" stored in the system
    When I create a research group named "modcs-20" with the description ""
    Then the research group with name "modcs-20" is not stored in the system because it has no description
    
    Scenario: duplicate researchgroup
    Given the system has a research group entitled "modcs" with the description "modcs research group" stored in the system
    When I create a research group named "modcs" with the description "modcs research group"
    Then the research group "modcs" is not stored again in the system
    
    Scenario: invalid by name size researchgroup
    Given the system has no research group entitled "modcs 123456789" stored in the system
    When I create a research group named "modcs 123456789" with the description "modcs research group"
    Then the research group "modcs 123456789" is not stored in the system because exceeds the number of characters allowed


  Scenario: editing the researchgroup's description to none
    Given the system has a research group entitled "modcs-2" with the description "modcs-2 research group" stored in the system
    When I modify the description of research group entitled "modcs-2" to none
    Then the description of research group entitled "modcs-2" is not none

  Scenario: editing the researchgroup's name to none
    Given the system has a research group entitled "modcs-3" with the description "modcs-3 research group" stored in the system
    When I modify the name of research group entitled "modcs-3" to none
    Then the research group is not stored in the system because it has no name


    Scenario:edit research group
    Given the system has a research group entitled "modcs" with the description "modcs research group" stored in the system
    When I modify the research group entitled "modcs" to "modcs 123" and its description to "modcs research group 1234"
    Then the edited research group "modcs 123" with description "modcs research group 1234" is properly stored in the system
    
	Scenario:delete research group
    Given the system has a research group entitled "modcs" with the description "modcs research group" stored in the system
    When I delete the research group entitled "modcs"
    Then the research group "modcs" is properly deleted of the system
 
    Scenario: new research group and show via web browser
    Given I am at the publications menu
    When I select the "Research Group" option at the publications menu
    And I select the new research group option at research group list page
    Then I can fill the research group details with name "modcs" and create a new one
    
  Scenario: show research group via web browser
    Given I am at the publications menu
    And the system has a Research Group named "Software Productivity Group" stored in the system
    When I select the Research Group option
    And I select the research group named "Software Productivity Group"
    Then the system will show the details of “Software Productivity Group” research group
    
    Scenario: edit research group via web browser
    Given I am at the publications menu
    And the system has a Research Group named "PESQUISA" stored in the system
    And I am at Research Group list menu
    When I select a research group called "PESQUISA"
    And I select the edit option
    Then I can change the research group name to "rgms" and save it



  Scenario: edit childof of research group
    Given the system has a research group entitled "group" with childof none
    When I modify the childof of research group entitled "group" to itself
    Then the childof of research group "group" is none


  Scenario: edit childof of research group web
    Given I am at the publications menu
    And   I created a research group entitled "rg" with childof none
    And   I am at Research Group list menu
    When  I select a research group called "rg"
    And   I select the edit option
    And   I change the field childof to research group called "rg"
    And   I click on update button
    Then  the childof of research group "rg" is none



