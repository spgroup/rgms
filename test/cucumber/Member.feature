@i9n
Feature: member
  As an administrator of the RGMS system
  I want to add, remove and modify users in the system.
  

  Scenario: new member
    Given the system has no member with username "usernametest"
    When I create a member with username "usernametest"
    Then the member with username "usernametest" is properly stored by the system
  
  Scenario: delete member
    Given the system has member with username "usernametest"
    When I delete a member with username "usernametest"
    Then the member with "usernametest" doesnt exist
  
  Scenario: new member with existing username
    Given the system has member with username "usernametest"
    When I create the member with username "usernametest"
    Then the member "usernametest" is not registered
  

  Scenario: login with incorrect password
    Given I am at the login page
    When I fill username and password with "admin" and "incorrectpassword"
    Then I am still on the login page with an error message
    
  Scenario: user registration
    Given I am at the register page
    When I fill the user details with "jose" "josesilva" "123456" "123456" "jose@ufpe.br" "UFPE" "Graduate Student"
    Then I am still on the register page with the message user created
    
  Scenario: create member
    Given I am at the create member page
    When I fill the user details with "jose" "josesilva" "jose@ufpe.br" "UFPE"
    Then I am on the member view page 

  Scenario: register user with invalid data
    Given I am at the create member page
    When I fill the user details with "jose" "josesilva" "jose@com" "UFPE"
    Then I am still on the create member page with the error message