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
    
      Scenario: invalid researchgroup
     Given the system has no research group entitled "modcs" stored in the system
    When I create a research group named "modcs" with the description "modcs research group"
    Then the research group is not stored in the system because is invalid

      Scenario: getting Publications
     Given the system has research groups stored in the system
     And the system has memberships stored in the system
     And the system has publications stored in the system
    When I get publications from a research group
    Then the publications members research group are returned