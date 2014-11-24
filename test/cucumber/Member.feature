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

  Scenario: new member with existing email
    Given the system has member with email "memberEmail@ufpe.br"
    When I try to create the member "Rebeca Souza" with email "memberEmail@ufpe.br"
    Then the member named "Rebeca Souza" is not registered

  Scenario: login with incorrect password
    Given I am at the login page
    When I fill username and password with "admin" and "incorrectpassword"
    Then I am still on the login page with an error message
	
#if($nonexisting)
	@cscbb
  Scenario: login with nonexisting username
    Given I am at the login page
	When I fill username and password with "nonexistingusername" and "password"
	Then I am still on the login page with an error message
#end
	
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

#if($longUsername)
  Scenario: register user with long username
    Given I am at the create member page
    When I fill the username with "josedmskejfjsdifejfje"
    Then I am still on the create member page with the error message
#end

#if($invalidEmail)
  Scenario: register member with invalid email
    Given I am at the create member page
	When I fill the email with "lalala.la"
	Then I am still on the create member page
	And a invalid email error message is displayed
#end

#if($invalid info)
Scenario: register member invalid info
  Given I am at the create member page
  When I fill city with "321"
  And I fill country with "123"
  Then I am still on the create member page 
  And a "no numbers in city and country allowed" error message is displayed
#end
  
#if($invalidPhone)
Scenario: new member with invalid phone
  Given I am at the create member page
  When I fill the phone with "camilasouto"
  Then I am still on the create member page 
  And a invalid phone message is displayed
#end
  
#if ($contextualInformation)
  Scenario: new member filled with default data
    Given I am at the create member page
    Then I see default data filled on create form

  Scenario: user registration with default data
    Given I am at the register page
    Then I see default data filled on register form
#end

#if($memberInfo)
@vddm
Scenario: editing member information
	Given the system has member with "Victor Monteiro","rgmsTest", "rgmsTest@gmail.com", "UFPE", "12345", "www.g.com.br", "Brazil", "Graduate Student"
	When I edit the "rgmsTest@gmail.com"'s "email" for "rgms@gmail.com"
	Then "rgms@gmail.com"'s information is updated and saved in the system

@vddm
Scenario: editing member
	Given I am at the member page
	When I click the first member id
    And I Click the option "edit" on Member Edition Page
    And I change the member's "name" by "Victor Monteiro"
	Then I can see the member's name is now "Victor Monteiro"
#end

#if($loginfacebook)
  Scenario: new member with facebook account
    Given I am at the create member page
	And I am logged on "Camila Souto" facebook
	When I click on "register with facebook"
	Then the member "Camila Souto" is properly stored by the system
#end

