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

  #if($listFunders)
  Scenario: list existing funder
    Given the system has a funder with code "12345"
    When I view the funder list
    Then my list funders contains funder "12345"
  #end


#if($duplicateFunderWeb)
  Scenario: duplicate funder web
    Given I am at the create funder page
    When I fill the funder code with "12345"
    Then the funder with code "12345" is not stored
    And I am still at the create funder page
#end