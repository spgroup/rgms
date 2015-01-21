@i9n
Feature: member
  As an administrator of the RGMS system
  I want to add, remove and modify users in the system.
#if($newMemberWithValidMailServer)
  Scenario: new member with valid mail server
    Given the system has no member with username "usernametest"
    When I create a member with username "usernametest" with valid mail server
    Then the member with username "usernametest" is properly stored by the system
#end

#if($newMemberWithoutValidMailServer)
  Scenario: new member without valid mail server
    Given the system has no member with username "usernametest"
    When I create a member with username "usernametest" with a invalid mail server "notReal"
    Then the member with username "usernametest" is not properly stored by the system
#end

#if($newMemberWithABlankUsername)
  Scenario: new member with a blank username
	Given the system without a member ""
	When I create a member with username ""
	Then the new member won't be inserted.
#end

#if($deleteInexistentMember)
  Scenario: delete inexistent member
	Given the system without a member "username"
	When I delete a member with username "username"
	Then the system will throw a message with an error "cannot find 'username'".
#end

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

  Scenario: new member with existing email
    Given the system has member with email "memberEmail@ufpe.br"
    When I try to create the member "Rebeca Souza" with email "memberEmail@ufpe.br"
    Then the member named "Rebeca Souza" is not registered

    # User

#if($loginWithCorrectPassword)
  Scenario: login with correct password
    Given I am at the login page
    When I fill username and password with "admin" and "adminadmin"
    Then I am at the main page logged in
#end

  Scenario: login with incorrect password
    Given I am at the login page
    When I fill username and password with "admin" and "incorrectpassword"

#if($validUserRegistration)
  Scenario: valid user registration
    Given I am at the register page
    When I fill the user details with a name "jose", a username "josesilva", a password1 "123456", a password2 "123456", a valid email "jose@ufpe.br", a university "UFPE" and a status "Graduate Student"
    Then I am redirected to the Login Page
    And A message indicating the user was successfully registered is displayed
#end

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

#Scenario: new member with invalid phone
#   Given the system has no member with username "userwithinvalidphone"
#   When I create a member with username "userwithinvalidphone"
#   Then I am still on the create member page with the error message

#if ($contextualInformation)
  Scenario: new member filled with default data
    Given I am at the create member page
    Then I see default data filled on create form

  Scenario: user registration with default data
    Given I am at the register page
    Then I see default data filled on register form
#end