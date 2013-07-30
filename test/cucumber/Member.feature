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

  Scenario: create member web
    Given I am at the create member page
    When I fill the user details with "jose" "josesilva" "jose@ufpe.br" "UFPE"
    Then I am on the member show page

  Scenario: create member web with partial information in chrome
    Given I am at the create member page
    When I fill some user details with "jose" "josesilva" "jose@ufpe.br" "UFPE"
    Then I am still on the create member page with the error message

  Scenario: register user with invalid data
    Given I am at the create member page
    When I fill the user details with "jose" "josesilva" "jose@com" "UFPE"
    Then I am still on the create member page with the error message

Scenario: register member long aditional info
   Given  I am at the create member page
   When   I fill many user details with "berg" "bergU" "jus@cin.ufpe.br" "UFPE" "ajsdaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaajsdaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaajsdaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaajsdaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaajsdaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaajsdaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
   Then   I am still on the create member page with the error message

Scenario: register member without phone city country
   Given I am at the create member page
   When  I fill user detail with "berg" "bergU" "jus@cin.ufpe.br" "UFPE"
   Then  I am on the member show page

Scenario: new member with invalid phone
   Given the system has no member with username "usernametest"
   When I create a member with "usernametest" "telefone"
   Then the system has no member with "usernametest"