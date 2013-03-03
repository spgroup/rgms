@i9n
Feature: research group

	Scenario: new researchgroup
	Given the system has no research group entitled "modcs" stored in the system
	When I create a research group named "modcs" with the description "modcs research group"
	Then the reserach group "modcs" is properly stored by the system
	    
	Scenario: duplicate researchgroup
	Given the system has a research group entitled "modcs" with the description "modcs research group" stored in the system
	When I create a research group named "modcs" with the description "modcs research group"
	Then the research group "modcs" is not stored again in the system
	
	Scenario: invalid researchgroup
	Given the system has no research group entitled "modcs 123456789" stored in the system
	When I create a research group named "modcs 123456789" with the description "modcs research group"
	Then the research group "modcs 123456789" is not stored in the system because exceeds the number of characters allowed
	
	Scenario: getting Publications
	Given the system has a research group entitled "modcs" with the description "modcs research group" stored in the system
	And the research group "modcs" has a membership with a member with username "admin" stored in the system
	And the member with username "admin" is associated with a publication titled "AdminPublication", with date "20-02-2013" stored in the system
	When I get publications from a research group
	Then the members publications of the research group "modcs" are returned