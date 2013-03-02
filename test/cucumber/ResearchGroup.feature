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