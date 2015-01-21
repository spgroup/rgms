@i9n
Feature: Funder
  As an administrator of the RGMS system
  I want to add, remove and modify funders in the system

  Scenario: new funder
    Given the system has no funder with code "12345"
    When I create a funder with code "12345"
    Then the funder with code "12345" is properly stored by the system

  Scenario: remove funder
    Given the system has funder with code "12345"
    When I remove a funder with code "12345"
    Then the system has no funder with code "12345"

  Scenario: duplicated funder
    Given the system has funder with code "12345"
    When I create a funder with code "12345"
    Then there is only one funder with code "12345" in the system

  Scenario: new funder web
    Given I am at the create funder page
    When I fill the funder code with "12345"
    Then the funder with code "12345" is properly stored by the system

#if(remove funder web)
  Scenario: remove funder web
    Given I am the funder page
    And has one or more funder in the list
    When I select the funder with code "12345"
    Then I click the 'remove' button
    And the funder with code "12345" is properly removed by the system
#end