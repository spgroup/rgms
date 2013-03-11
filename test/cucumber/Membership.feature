@i9n
Feature: membership
  As an administrator of the RGMS system
  I want to add, remove and modify memberships in the system.

//Teste "new membership" falhando com a seguinte mensagem de erro:
//could not insert: [rgms.member.Membership]; SQL [insert into membership (id, version, date_joined, date_left, member_id, research_group_id) values (null, ?, ?, ?, ?, ?)]; constraint [null]; nested exception is org.hibernate.exception.ConstraintViolationException: could not insert: [rgms.member.Membership]
//https://github.com/spgroup/rgms/issues/95 
  Scenario: new membership
    Given the system has member "usernametest" and research group "taes"
    And the system has no membership with member "usernametest", research group "taes" between "01-03-2012" and "01-06-2012"
    When I create a membership with member "usernametest", research group "taes" between "01-03-2012" and "01-06-2012"
    Then the membership with member "usernametest", research group "taes" between "01-03-2012" and "01-06-2012" is created
  
//Teste "delete membership" nao pode ser implementado enquanto nao se conseguir criar um membership
  Scenario: delete membership
  	Given the system has membership with member "usernametest", research group "taes" between "01-03-2012" and "01-06-2012"
    When I delete a membership with member "usernametest", research group "taes" between "01-03-2012" and "01-06-2012"
    Then the membership with member "usernametest", research group "taes" between "01-03-2012" and "01-06-2012" is deleted

  	
  Scenario: modify membership
  	//TODO
  	
//Teste "access membership creation page from membership main page" falhando
  Scenario: access membership creation page from membership main page
    Given I am at the membership page
    When I click the new membership option
    Then I am on the create page
    
  Scenario: create membership with invalid data
  //TODO
  
  Scenario: create membership with incomplete form
  //TODO  	