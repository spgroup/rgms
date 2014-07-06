@i9n
Feature: Visit
  As a member of a research group
  I want to add, remove and modify visits of external members to the group
  so that I can store the visits that external member perform to the group, so do as the visitor

#if ( $visit )
  Scenario: visit of a day for a non stored visitor
    Given the system has no visitor named "Person"
    When I create the visit for the visitor "Person" with initial date "11/11/2000"
    Then the visitor named "Person" is properly stored by the system
    And the visit for the visitor "Person" with initial and final date equal to "11/11/2000" is properly stored by the system

  Scenario: visit of a period for a non stored visitor
    Given the system has no visitor named "Person"
    When I create the visit for the visitor "Person" with initial date "11/11/2000" and final date "12/11/2000"
    Then the visitor named "Person" is properly stored by the system
    And the visit for the visitor "Person" with initial date "11/11/2000" and final date "12/11/2000" is properly stored by the system

  Scenario: visit of a day for a stored visitor
    Given the system has visitor named "Person"
    When I create the visit for the visitor "Person" with initial date "11/11/2000"
    Then the visit for the visitor "Person" with initial and final date equal to "11/11/2000" is properly stored by the system

  Scenario: visit of a period for a stored visitor
    Given the system has visitor named "Person"
    When I create the visit for the visitor "Person" with initial date "11/11/2000" and final date "12/11/2000"
    Then the visit for the visitor "Person" with initial date "11/11/2000" and final date "12/11/2000" is properly stored by the system

  Scenario: list existing visit
    Given the system has visitor named "Person"
    And a visit for the visitor "Person" with initial date "11/11/2000" and final date "12/11/2000"
    When I view the list of visits
    Then the list is returned with the visit of the visitor named "Person" with initial date "11/11/2000" and final date "12/11/2000"

  Scenario: list existing visit web
    Given I am logged as "admin" and at the visits page
    And the visit of the visitor named "Person" with initial date "11/11/2000" and final date "12/11/2000" is stored in the system
    Then my resulting visits list contains the visit of the visitor named "Person" with initial date "11/11/2000" and final date "12/11/2000"

  Scenario: remove existing visit
    Given the system has visitor named "Person"
    And a visit for the visitor "Person" with initial date "11/11/2000" and final date "12/11/2000"
    When I delete the visit of the visitor named "Person" with initial date "11/11/2000" and final date "12/11/2000"
    Then the visit of the visitor named "Person" with initial date "11/11/2000" and final date "12/11/2000" is properly removed by the system

  Scenario: remove existing visit web
    Given I am logged as "admin" and at the visits page
    And the visit of the visitor named "Person" with initial date "11/11/2000" and final date "12/11/2000" is stored in the system
    When I select to view the visit of the visitor named "Person" with initial date "11/11/2000" and final date "12/11/2000" in resulting list
    Then the visit details are showed and I can select the option to remove
    And the visit of the visitor named "Person" with initial date "11/11/2000" and final date "12/11/2000" is properly removed by the system

  Scenario: edit existing visit
    Given the system has visitor named "Person"
    And a visit for the visitor "Person" with initial date "11/11/2000" and final date "12/11/2000"
    When I edit the visit of the visitor named "Person" with initial date "11/11/2000" and final date "12/11/2000" to the visitor named "Person Updated"
    Then the visit of the visitor named "Person" with initial date "11/11/2000" and final date "12/11/2000" is properly updated by the system

  Scenario: edit existing visit web
    Given I am logged as "admin" and at the visits page
    And the visit of the visitor named "Person" with initial date "11/11/2000" and final date "12/11/2000" is stored in the system
    When I select to view the visit of the visitor named "Person" with initial date "11/11/2000" and final date "12/11/2000" in resulting list
    And I change the visitor name
    Then I can select the "Alterar" option visit
    And the visit of the visitor named "Person" with initial date "11/11/2000" and final date "12/11/2000" is properly updated by the system

  Scenario: new invalid visit
    Given the system has visitor named "Person"
    When I create the visit for the visitor "Person" with initial date "11/11/2000" and final date "10/11/2000"
    Then the visit for the visitor "Person" with initial date "11/11/2000" and final date "10/11/2000" is not stored by the system because it is invalid

  Scenario: invalid edit existing visit
    Given the system has visitor named "Person"
    And a visit for the visitor "Person" with initial date "11/11/2000" and final date "12/11/2000"
    When I try to edit the visit of the visitor named "Person" with initial date "11/11/2000" and final date "12/11/2000" changing the final date to "10/11/2000"
    Then the visit of the visitor named "Person" with initial date "11/11/2000" and final date "12/11/2000" is not properly updated by the system because it is invalid
    
  Scenario: list existing visits and periods
    Given the system has visits with initial or final date greater than or equal "01/01/2011"
    When I ask for the list of visits for the period from "01/01/2011" to today
    Then a visit with initial or final date greater than or equal "01/01/2011" is not lost

  Scenario: asking identification for a visitor that already exists
    Given I am logged as "admin"
    And I am at the Add Visit Page
    And I have created a visitor named "Person"
    When I try to create the visit for the visitor "Person"
    Then the Confirm Identification Page is open
 
  Scenario: confirming identification for a visitor that already exists
    Given I am logged as "admin"
    And I have tried to create a visit for the visitor "Person" that already exists
    And I am at the Confirm Identification Page
    When I press the No button
    Then the visit for the visitor "Person" is properly stored by the system

  Scenario: changing the name of a visitor that already exists
    Given I am logged as "admin"
    And I have tried to create a visit for the visitor "Person" that already exists
    And I am at the Confirm Identification Page
    When I press the Yes button
    Then the visitor named "Person2" is properly stored by the system
    And the visit for the visitor "Person2" is properly stored by the system
    
#if( $Twitter )
  Scenario: Add a new visit twitting it
    Given I am logged as "admin" and at the Add Visit Page
    When I try to create an visit
    And I share it in Twitter with "rgms_ufpe" and "rgmsadmin2013"
    Then A tweet is added to my twitter account regarding the new visit "Primeira Visita"
    
  Scenario: Add a new visit with twitter, but don't tweet it
   Given I am logged as "admin" and at the Add Visit Page
   When I try to create an visit
   Then The visit "Primeira Visita" is created but no tweet should be post

#end

#end
