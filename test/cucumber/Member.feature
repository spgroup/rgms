@i9n
Feature: member
  As an administrator of the RGMS system
  I want to add, remove and modify users in the system.

  Scenario: new member with valid mail server
    Given the system has no member with username "usernametest"
    When I create a member with username "usernametest"
    Then the member with username "usernametest" is properly stored by the system

  Scenario: list existing member
    Given   the system has a member with username "usernametest"
    When    the list members is displayed
    Then    the list members contains member "usernametest"

  Scenario: delete member
    Given the system has a member stored with username "usernametest"
    When I delete a member with username "usernametest"
    Then the member with "usernametest" is removed from the system storage

  Scenario: new member with existing username
    Given the system has a member with username "usernametest"
    When I create the member with username "usernametest"
    Then the member "usernametest" is not registered

  Scenario: new member with existing email
    Given the system has member with email "memberEmail@ufpe.br"
    When I try to create the member "Rebeca Souza" with email "memberEmail@ufpe.br"
    Then the member named "Rebeca Souza" is not registered

  Scenario: login with incorrect password
    Given I am at the login page
    When I fill username and password with "admin" and "incorrectpassword"
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


#Scenario: register member invalid aditional info
#   Given  I am at the create member page
#   When   I fill many user details with "berg" "bergU" "jus@cin.ufpe.br" "UFPE" "ajsdaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaajsdaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaajsdaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaajsdaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaajsdaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaajsdaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
#   Then   I am still on the create member page with the error message

Scenario: new member with invalid phone
   Given the system has no member with username "userwithinvalidphone"
   When I create a member with username, phone "userwithinvalidphone" "telefone Invalido"
   Then the member "userwithinvalidphone" is not stored by the system


  Scenario: new member with invalid University
    Given the system has no member with username "userwithinvaliduniversity"
    When I create a member with username, university "userwithinvalidphone" "123456"
    Then the member "userwithinvaliduniversity" is not stored by the system


#if ($contextualInformation)
  Scenario: new member filled with default data
    Given I am at the create member page
    Then I see default data filled on create form

  Scenario: user registration with default data
    Given I am at the register page
    Then I see default data filled on register form
#end

  Scenario: Phone param in blank should not impede the user creation
    Given I'm creating a new user
    When I create a user with Name, Username, Email, University, Status, Country and Website equals to "userTest", "user1", "user1@ufpe.br", "Federal University of Pernambuco", "Graduate Student", "Brazil" and "http://www.google.com"
    Then The User with username "user1" should be stored by the system

  Scenario: Website param in blank should not impede the user creation
    Given I'm creating a new user
    When I create a user with Name, Username, Email, University, Status, Country and Phone equals to "userTest", "user2", "user2@ufpe.br", "Federal University of Pernambuco", "Graduate Student", "Brazil" and "99887766"
    Then The User with username "user2" should be stored by the system
