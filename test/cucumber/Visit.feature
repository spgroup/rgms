@i9n
Feature: Visit
  As a member of a research group
  I want to add, remove and modify visits of external members to the group
  so that I can store the visits that external member perform to the group, so do as the visitor

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

  @ignore
  Scenario: list existing visits and periods
    Given the system has visits with initial or final date greater than or equal "01/01/2011"
    When I list the visits for the period from "01/01/2011" to today
    Then no data is stored by the system

  @ignore
  Scenario: visit for a stored visitor
    Given the system has a visitor named "Person"
    When I try to create a visit for the visitor "Person"
    Then the visit for the visitor "Person" is not properly stored by the system

  @ignore
  Scenario: asking identification for a visitor that already exists
    Given I am logged as "admin"
    And I am at the Add Visit Page
    And the system has a visitor named "Person"
    When I try to create a visitor named "Person"
    Then the Confirm Identification Page is open

  @ignore
  Scenario: confirming identification for a visitor that already exists
    Given I am logged as "admin"
    And I have tried to create a visit for a visitor that already exists
    And I am at the Confirm Identification Page
    When I press the "Yes" button
    Then the visit for the visitor is properly stored by the system

  @ignore
  Scenario: changing the name of a visitor that already exists
    Given I am logged as "admin"
    And I have tried to create a visit for a visitor that already exists
    And I am at the Confirm Identification Page
    When I press the "No" button
    Then a new visitor is created with a different name
    And the new visitor is properly stored by the system
    And the visit for the new visitor is properly stored by the system

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


# [5/12/14, 3:38:04 PM] Paulo Borba: voce poderia fazer cenários para devolver uma lista com os nomes dos visitantes e o período da visita, para um dado período
#  [5/12/14, 3:38:07 PM] Paulo Borba: algo como
#  [5/12/14, 3:39:25 PM] Paulo Borba: Naman Goel (BHU Indian Institute of Technology, India, 5--7/2012),
#  Ralf Lämmel (University of Koblenz-Landau, Aelamanha, 8/2012),
#  Andrzej Wąsowski (IT University of Copenhagen, Dinamarca, 12/2013).
#  [5/12/14, 3:39:33 PM] Paulo Borba: se o período fosse de 2011 pra ca
#  [5/12/14, 3:40:02 PM] Paulo Borba: isso mostra alguns dados que precisamos ter sobre a visita
#  [5/12/14, 3:40:13 PM] Paulo Borba: e que não estão presentes na versão atual
#  [5/12/14, 3:40:46 PM] Paulo Borba: isso pode entao ser usado para revisar e ajustar os outros cenarios
#  [5/12/14, 3:41:17 PM] Marcello Valença: certo
#  [5/12/14, 3:41:39 PM] Paulo Borba: um outro novo cenário poderia ter a ver com o cadastro de pessoas diferente com o mesmo nome; o sistema deveria perguntar se trata-se da pessoa ja cadastrada
