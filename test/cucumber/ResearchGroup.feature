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
    
    Scenario: invalid by not having a name researchgroup
    Given the system has no research group with no name stored in the system
    When I create a research group with no name and with the description "modcs research group"
    Then the research group is not stored in the system because it has no name
    
    Scenario: invalid by not having a description researchgroup
    Given the system has no research group with name "modcs"
    When I create a research group with  with name "modcs" and with no description
    Then the research group with name "modcs" is not stored in the system because it has no description
    
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
    Given the system has a "Research Group" named "grupo" stored in the system
    And i am at Research Group list menu
    And i select a research group called "grupo"
    Then the system will show the details of this research group
    
    Scenario: edit research group via web browser
    Given the system has a "Research Group" named "PESQUISA" stored in the system
    And i am at Research Group list menu
    When i select a research group called "PESQUISA"
    And i select the edit option
    Then i can change the research group name to "rgms" and save it
          	