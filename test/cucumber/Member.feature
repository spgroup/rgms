@i9n
Feature: member
  As an administrator of the RGMS system
  I want to add, remove and modify users in the system.

  Scenario: new member with valid mail server
    Given the system has no member with username "usernametest"
    When I create a member with username "usernametest"
    Then the member with username "usernametest" is properly stored by the system

  Scenario: list existing member
    Given   the system has member with username "usernametest"
    When    I view the member list
    Then    my list members contains member "usernametest"

  Scenario: delete a existing member
    Given the system has a member with username "usertestname"
    When I delete a member with username "usertestname"
    Then the member with username "usertestname" will be deleted

  Scenario: new member with existing username
    Given the system has a member with username "usertestname"
    When I create the member with a username "usertestname"
    Then the member with username "usertestname" is not registered

  Scenario: new member with existing email
    Given the system has member with email "memberEmail@ufpe.br"
    When I try to create the member "Rebeca Souza" with email "memberEmail@ufpe.br"
    Then the member named "Rebeca Souza" is not registered

  Scenario: login with incorrect password
    Given I am at the login page
    When I fill username with "admin"
    And I fill password with "incorrectpassword"
    Then I am still on the login page with an error message

  Scenario: user registration
    Given I am at the register page
    When I fill the user details with a name, username, passoword1, password2, email, university, status "jose" "josesilva" "123456" "123456" "jose@ufpe.br" "UFPE" "Graduate Student"
    Then I am redirected to the Login Page
    And A message indicating the user was successfully registered is displayed


#  Scenario: create member web
#    Given I am at the create member page
#    When I fill the user details with "jose" "josesilva" "jose@ufpe.br" "UFPE"
#    Then I am on the member show page
#   Then  the member with username "josesilva" is created

  Scenario: create member web with partial information in chrome
    Given I am at the create member page
    When I fill some user details with "jose" "josesilva" "jose@ufpe.br" "UFPE"
    Then I am still on the create member page with the error message

  Scenario: register user with invalid data
    Given I am at the create member page
    When I fill the user details with "jose" "josesilva" "jose@com" "UFPE"
    Then I am still on the create member page with the error message

  Scenario: new member with invalid phone
   Given I am at the create member page
   When I create a member with invalid phone
   Then I am still on the create member page with a "Invalid Phone" error message

#if ($contextualInformation)
  Scenario: new member filled with default data
    Given I am at the create member page
    Then I see default data filled on create form

  Scenario: user registration with default data
    Given I am at the register page
    Then I see default data filled on register form
#end