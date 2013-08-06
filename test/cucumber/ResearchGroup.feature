@i9n
Feature: research group

 	Scenario: new researchgroup
    Given the system has no research group entitled "modcs" stored in the system
    When I create a research group named "modcs" with the description "modcs research group"
    Then the research group "modcs" is properly stored by the system
    
    Scenario: new research group with no name. 
    Given the system has no research group entitled "modcs" stored in the system
    When I create a research group with no name and with the description "modcs research group"
    Then the research group is not stored in the system because is invalid
    
    Scenario: new research group with no description. 
    Given the system has no research group entitled "modcs" stored in the system
    When I create a research group with name "modcs" and with no description
    Then the research group is not stored in the system because is invalid
    
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
    Then there is no research group entitled none


    Scenario:edit research group
    Given the system has a research group entitled "modcs" with the description "modcs research group" stored in the system
    When i modify the research group entitled "modcs" to "modcs 123" and its description to "modcs research group 1234"
    Then the edited research group "modcs 123" with description "modcs research group 1234" is properly stored in the system
    
	Scenario:delete research group
    Given the system has a research group entitled "modcs" with the description "modcs research group" stored in the system
    When i delete the research group entitled "modcs"
    Then the research group "modcs" is properly deleted of the system
 
    Scenario: new research group and show via web browser
    Given i am at publication menu
    When i select the "Research Group" option at publications menu
    And i select the new research group option at research group list page
    Then i can fill the research group details with name "modcs" and create a new one
    
    Scenario: show research group via web browser
    Given the system has a Research Group named "grupo" stored in the system
    And i am at Research Group list menu
    And i select a research group called "grupo"
    Then the system will show the details of this research group
    
    Scenario: edit research group via web browser
    Given the system has a Research Group named "PESQUISA" stored in the system
    And i am at Research Group list menu
    When i select a research group called "PESQUISA"
    And i select the edit option
    Then i can change the research group name to "rgms" and save it



  Scenario: edit childof of research group
    Given the system has a research group entitled "group" with childof none
    When I modify the childof of research group entitled "group" to itself
    Then the childof of research group "group" is none


  Scenario: edit childof of research group web
    Given i am logged as "admin"
    And   i created a research group entitled "rg" with childof none
    And   i am at Research Group list menu
    When  i select a research group called "rg"
    And   i select the edit option
    And   i change the field childof to research group called "rg"
    And   i click on update button
    Then  the childof of research group "rg" is none



