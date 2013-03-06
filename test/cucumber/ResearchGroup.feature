@i9n
Feature: research group

 Scenario: new researchgroup
    Given the system has no research group entitled "modcs"
    When I create a research group named "modcs" with the description "modcs research group"
    Then the reserach group "modcs" is properly stored by the system
    
     Scenario: duplicate researchgroup
     Given the system has a research group entitled "modcs" stored in the system
    When I create a research group named "modcs" with the description "modcs research group"
    Then the research group is not stored again in the system
    
      Scenario: new research group with no name. inavlid
     Given the system has no research group entitled "modcs" stored in the system
    When I create a research group with no name and with the description "modcs research group"
    Then the research group is not stored in the system because is invalid
	
      Scenario: new research group with no description. inavlid
     Given the system has no research group entitled "modcs" stored in the system
    When I create a research group with name "modcs" and with no description
    Then the research group is not stored in the system because is invalid

      Scenario: getting Publications
     Given the system has research groups stored in the system
     And the system has memberships stored in the system
     And the system has publications stored in the system
    When I get publications from a research group
    Then the publications members of the research group are returned
    
     Scenario:edit research group
      Given the system has a research group entitled "modcs" stored in the system
      When i edit the research group entitled "modcs"
      Then the edited research group is properly stored in the system
      
       Scenario:edit research group inexistent
      Given the system has no research group entitled "modcs" stored in the system
      When i edit a research group entitled "modcs"
      Then the system will not edit anything
      
       Scenario:delete research group
      Given the system has a research group entitled "modcs" stored in the system
      When i delete the research group entitled "modcs"
      Then the research group is properly deleted of the system
      
       Scenario:delete inexistent research group
       Given the system has a research group entitled "modcs" stored in the system
       When i delete the research group entitled "modcs"
       Then the system will not delete anything
      
       Scenario: new research group via web browser
       Given i am at publication menu
       When i select the "Research Group" option at publications menu
       And i select the new research group option at research group list page
       Then i can fill the research group details
       
       Scenario: show research group via web browser
       Given the system has a research group stored in the system
       And i am at publications menu
       When i select "Research Group" option at publications menu
       And i select a research group called "MODCs"
       Then the system will show the details of this research group
       
       