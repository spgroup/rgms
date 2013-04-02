@i9n
Feature: record
 As a member, I want to edit my status and create, edit and remove records
 
	Scenario: Delete record
	Given the system has only one record with status "MSc Student"
	When I remove the record with status "MSc Student"
	Then the record with status "MSc Student" is properly removed by the system
			
	Scenario: Update record
	Given the system has only one record with status "MSc Student" and this record has a null end date
	When I update the record with status "MSc Student" with an end date "04/03/2013"
	Then the record with status "MSc Student" has end date "04/03/2013"
	
	Scenario: Create record with same status name
	Given the system has only one record with status "MSc Student"
	When I create the record with status "MSc Student"
	Then the record with status "MSc Student" is properly stored and the system has two records with this status

	Scenario: visualize record web
    Given I am at record list and the system has only one record with status "MSc Student"
    When I click the record with status "MSc Student" at the record list
    Then I can visualize the record with status "MSc Student"

	Scenario: new record web
	Given I am at record list
	When I click the create record option
	Then I can fill the record details

    Scenario: update record with status empty web
    Given I am at the edit page of the record with status "MSc Student"
    When I set the status to "" and I click the save button
    Then I am still at the edit page of the record with status "MSc Student"
