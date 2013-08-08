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
    
#if( $Twitter )
  Scenario: Add a new visit twitting it
    Given I am logged as "admin" and at the Add Visit Page
    When I try to create an visit  and I click on Share it in Twitter with "rgms_ufpe" and "rgmsadmin2013"
    Then A twitter is added to my twitter account regarding the new visit "Primeira Visita"
    
  Scenario: Add a new visit with twitter, but don't twitte it
   Given I am logged as "admin" and at the Add Visit Page
   When I try to create an visit
   Then The visit "Primeira Visita" is created but no twitter should be post

#end
