@i9n
Feature: Visitante Externo
  As um membro do grupo de pesquisa
  I want adicionar visitas de membros externos ao grupo
  so that I can armazenar  as visitas que membros externos realizem ao grupo

  Scenario: visita de um dia para visitante nao cadastrado
    Given que o visitante "Pessoa" nao esteja cadastrado no  sistema
    When eu agendar uma visita para o visitante "Pessoa" And inserir apenas a data "11/11/2000"
    Then o cadastro do vistante "Pessoa" e armazenado no sistema e tambem uma visita com de incio igual e fim igual  a "11/11/2000"

  Scenario: visita de um periodo para visitante nao cadastrado
    Given que o visitante "Pessoa" nao esteja cadastrado no  sistema
    When eu agendar uma visita para o visitante "Pessoa" e inserir data inicio igual a "11/11/2000" e inserir a data fim igual a "12/11/2000"
    Then o cadastro do vistante "Pessoa" e armazenado no sistema e tambem uma visita com data de incio igual a "11/11/2000" e data fim igual a "12/11/2000"

  Scenario: visita de um dia para visitante cadastrado
    Given que o visitante "Pessoa" esteja cadastrado no  sistema
    When eu agendar uma visita para o visitante "Pessoa" And inserir apenas a data "11/11/2000"
    Then uma visita para o visitante "Pessoa" com de incio igual e fim igual  a "11/11/2000" e armazenada no sistemas

  Scenario: visita de um periodo para visitante cadastrado
    Given que o visitante "Pessoa" esteja cadastrado no  sistema
    When eu agendar uma visita para o visitante "Pessoa" e inserir data inicio igual a "11/11/2000" e inserir a data fim igual a "12/11/2000"
    Then uma visita para o visitante "Pessoa" com de incio igual a "11/11/2000" e data fim igual a "12/11/2000" e armazenada no sistemas

  Scenario: list existing visit
    Given the system has a visit of the visitor named "Pessoa" with initial date "11/11/2000" and final date "12/11/2000" stored
    When I view the list of visits
    Then the list is returned with the visit of the visitor named "Pessoa" with initial date "11/11/2000" and final date "12/11/2000"

  Scenario: list existing visit web
    Given I am logged as "admin" and at the visits page and the visit of the visitor named "Pessoa" with initial date "11/11/2000" and final date "12/11/2000" is stored in the system
    Then my resulting visits list contains the visit of the visitor named "Pessoa" with initial date "11/11/2000" and final date "12/11/2000"

  Scenario: remove existing visit
    Given the system has a visit of the visitor named "Pessoa" with initial date "11/11/2000" and final date "12/11/2000" stored
    When I delete the visit of the visitor named "Pessoa" with initial date "11/11/2000" and final date "12/11/2000"
    Then the visit of the visitor named "Pessoa" with initial date "11/11/2000" and final date "12/11/2000" is properly removed by the system

  Scenario: remove existing visit web
    Given I am logged as "admin" and at the visits page and the visit of the visitor named "Pessoa" with initial date "11/11/2000" and final date "12/11/2000" is stored in the system
    When I select to view the visit of the visitor named "Pessoa" with initial date "11/11/2000" and final date "12/11/2000" in resulting list
    Then the visit details are showed and I can select the option to remove
    And the visit of the visitor named "Pessoa" with initial date "11/11/2000" and final date "12/11/2000" is properly removed by the system

  Scenario: edit existing visit
    Given the system has a visit of the visitor named "Pessoa" with initial date "11/11/2000" and final date "12/11/2000" stored
    When I edit the visit of the visitor named "Pessoa" with initial date "11/11/2000" and final date "12/11/2000" to the visitor named "Pessoa Updated"
    Then the visit of the visitor named "Pessoa" with initial date "11/11/2000" and final date "12/11/2000" is properly updated by the system

  Scenario: edit existing visit web
    Given I am logged as "admin" and at the visits page and the visit of the visitor named "Pessoa" with initial date "11/11/2000" and final date "12/11/2000" is stored in the system
    When I select to view the visit of the visitor named "Pessoa" with initial date "11/11/2000" and final date "12/11/2000" in resulting list
    And I change the visitor name
    Then I can select the "Alterar" option visit
    And the visit of the visitor named "Pessoa" with initial date "11/11/2000" and final date "12/11/2000" is properly updated by the system

#if( $Twitter )
  Scenario: Add a new visit twitting it
    Given I am logged as "admin" and at the Add Visit Page
    When I try to create an visit  and I click on Share it in Twitter with "rgms_ufpe" and "rgmsadmin2013"
    Then A twitter is added to my twitter account regarding the new visit "Primeira Visita"

  Scenario: Add a new visit with twitter, but don't twitte it
    Given I am logged as "admin" and at the Add Visit Page
    When I try to create an visit
    Then No twitter should be post
#end
