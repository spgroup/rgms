@i9n
Feature: membership
  As an administrator of the RGMS system
  I want to add, remove and modify memberships in the system.


  Scenario: new membership
    Given the system has member "usernametest" and research group "taes"
    And the system has no membership with member "usernametest", research group "taes" between "01-03-2012" and "01-06-2012"
    When I create a membership with member "usernametest", research group "taes" between "01-03-2012" and "01-06-2012"
    Then the membership with member "usernametest", research group "taes" between "01-03-2012" and "01-06-2012" is created
  
  Scenario: delete membership
  	Given the system has membership with member "usernametest", research group "taes" between "01-03-2012" and "01-06-2012"
    When I delete a membership with member "usernametest", research group "taes" between "01-03-2012" and "01-06-2012"
    Then the membership with member "usernametest", research group "taes" between "01-03-2012" and "01-06-2012" is deleted

  	
  Scenario: modify membership
  	//TODO
  	

  Scenario: access membership creation page from membership main page
    Given I am at the membership page
    When I click the new membership option
    Then I am on the create page
    
  Scenario: create membership with invalid data
  //TODO
  
  Scenario: create membership with incomplete form
  //TODO  	