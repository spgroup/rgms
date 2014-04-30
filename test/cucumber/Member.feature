@i9n
Feature: member
  As an administrator of the RGMS system
  I want to add, remove and modify users in the system.



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

  #if ($memberListAndPageImprovement)
  Scenario: user can order the member by id, name, username, enabled, email, roles, permissions
    Given I am at the member List page
    Then I can click to any attribute of member
    And the system will display the list of member ordered them with the attribute

  Scenario: new member with valid mail server
    Given the system has no member with username "usernametest"
    When I create a member with username "usernametest"
    Then the member with username "usernametest" is properly stored by the system
    And  the new member with username "usernametest" will be logged in
    Then the system will send a welcoming email to the new member

  Scenario: administrator needs to know the newest members and approve them
    Given I am at the member List page
    Then I see the newest members and the not approved ones

  Scenario: member can see his name and a link to logout
    Given I am at any page
    Then I see my name and a link to logout

  Scenario: administrator click on roll of member and access the member profile
    Given I am at the member List page
    Then I move the mouse hover on the rows to select the member
    And click to access the profile

  Scenario: member can access the facebook profile for those connect the facebook app
    Given I am at the member profile
    Then I can click to the link to member's facebook profile

  #end
