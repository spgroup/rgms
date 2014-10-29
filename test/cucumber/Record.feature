@i9n
Feature: record
  As a member, I want to edit my status and create, edit and remove records

#Depends on BootStrap code. Remove that.

  Scenario: Delete record without dependency
    Given the system has only one record with status "Graduate Student"
    And the record with status "Graduate Student" is not associated to a member
    When I remove the record with status "Graduate Student"
    Then the record with status "Graduate Student" is properly removed by the system

  Scenario: Delete record with dependency
    Given the system has only one record with status "MSc Student"
    And the record with status "MSc Student" is associated to a member
    When I remove the record with status "MSc Student"
    Then the record with status "MSc Student" is not removed by the system

  Scenario: Update record
    Given the system has only one record with status "MSc Student" and this record has a null end date
    When I update the record with status "MSc Student" with an end date "04/03/2013"
    Then the record with status "MSc Student" has end date "04/03/2013"

  Scenario: Create record with same status name
    Given the system has only one record with status "MSc Student"
    When I create the record with status "MSc Student"
    Then the record with status "MSc Student" is properly stored and the system has two records with this status

  Scenario: visualize record web
    Given I am logged
    And I am at record list
    And the system has only one record with status "MSc Student"
    When I click the record with status "MSc Student" at the record list
    Then I am still at the visualize page of the record with status "MSc Student"

  Scenario: new record web
    Given I am logged
    And I am at record list
    When I click the create record option
    Then I can fill the record details

  Scenario: update record with status empty web
    Given I am logged
    And I am at record list
    And the system has only one record with status "MSc Student"
    When I click the record with status "MSc Student" at the record list
    And I click the edit button of the record
    And I set the status to "" and I click the save button
    Then I am at the edit page of the record with status "MSc Student"

  Scenario: delete record web with dependency
    Given I am logged
    And I am at the visualize page of the record with status "MSc Student"
    And the system has only one record with status "MSc Student"
    And the record with status "MSc Student" is associated to a member
    When I click to remove the record
    Then I am still at the visualize page of the record with status "MSc Student"
	
#if($successfullyEditAStatus)
  Scenario: successfully edit a status
	Given I am a member who wants to edit my status
	And I am logged
	And I am in my profile page,
	When I fill the field of status with my new status,
	Then my old status will be thrown away
	And my profile will now show my new status.
#end
	
#if($cancelTheEditOfAStatus)
  Scenario: Cancel the edit of a status
	Given a field of status filled with the new status of a member,
	When the member decides to not post that status
	And press the button 'cancel',
	Then the new status will be erased
	And nothing will be posted.
#end