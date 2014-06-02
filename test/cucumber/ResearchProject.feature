@i9n
Feature: research project
  As a member of a research group
  I want to add, remove and modify research projects I have created

  @ignore
  Scenario: new research project
    Given the system has no research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
    When I create a research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas" with all required data
    Then the research project "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas" is properly stored by the system

  @ignore
  Scenario: duplicated research project
    Given  the system has a research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
    When I try to create a research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
    Then the research project "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas" is not stored twice
    And no research project stored is affected

  @ignore
  Scenario: remove research project
    Given the system has a research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
    And I am logged into the system as administrator of the research group named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
    When I remove the research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
    Then the research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas" is properly removed by the system

  Scenario: new research project without funders
    Given the system has no research project named as "Implementação Progressiva de Aplicações Orientadas a Aspectos"
    When I create a research project named as "Implementação Progressiva de Aplicações Orientadas a Aspectos" without funders
    Then the research project "Implementação Progressiva de Aplicações Orientadas a Aspectos" is properly stored by the system

  #if ($XMLImport)
  @ignore
  Scenario: upload research project with a file
    Given the system has some research project stored
    And I am logged in the system
    When I upload new research projects from the file "testelattes2.xml"
    Then the system has more research projects now

  Scenario: upload research project without a file
    Given I am at the publications menu
    When I select the "Projeto de Pesquisa" option at the program menu
    And I select the upload button at the research project page
    Then I'm still on the research project page
    And the system shows an error message at the research project page
#end

  @ignore
  Scenario: list research projects where I am a member
    Given I am at the research project list page
    When I select the "Meus Projetos de Pesquisa" option at research project menu
    Then the system shows a list with the research projects where I am a member

  @ignore
  Scenario: filter research projects by name
    Given  I am at the research projects list page
    When I fill the project name field
    And select the option "Filtrar Projetos de Pesquisa"
    Then the system shows the research projects listed by the research projects name

  @ignore
  Scenario: remove research project that does not exist
    Given the system has no research projects named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
    And I am logged into the system as administrator
    When I try to remove a research project named "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
    Then nothing happens to the research projects stored

  @ignore
  Scenario: edit existing research project
    Given the research project named "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas" is stored in the system
    And I am logged into the system as administrator of the research project named "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
    When I try to edit the research project "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas" in the system
    And I changed the data of the research project
    Then the data of the research project named "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas" is updated in the system

  @ignore
  Scenario: new invalid research project with blank name
    Given the research project named "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas" is stored in the system
    When I try to create a research project named as ""
    Then the research project "" is not stored by the system because it is invalid
    And no research project stored is affected

  @ignore
  Scenario: new invalid research project with blank description
    Given the system has no research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
    When I try to create a research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas" with description field blank
    Then the research project "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas" is not stored by the system because it is invalid
    And no research project stored is affected

  @ignore
  Scenario: new research project with duplicated members
    Given the system has no research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
    When I create a research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas" with member field duplicated
    Then the research project "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas" is properly stored by the system
    And the stored member list does not have duplicated members

  @ignore
  Scenario: new research project web
    Given I am at new research project page
    And the system has no research project named "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
    When I create the research project named "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
    Then the research project named "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas" is stored by the system
    And it is shown in the research project list with name "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"

  @ignore
  Scenario: new invalid research project with blank name web
    Given I am at new research project page
    And the research project named "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas" is stored in the system
    When I try to create the research project named ""
    Then the research project named "" is not stored by the system because it is invalid
    And  the system shows an error message at the research project page
    And no research project stored is affected

  @ignore
  Scenario: new invalid research project with blank description web
    Given I am at new research project page
    And the system has no research project named "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
    When I try to create the research project named "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas" with description field blank
    Then the research project named "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas" is not stored by the system because it is invalid
    And  the system shows an error message at the research project page
    And no research project stored is affected

  @ignore
  Scenario: duplicated research project web
    Given I am at new research project page
    And the system has a research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
    When I try to create the research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
    Then the research project "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas" is not stored twice
    And it is not shown duplicated in the research project list
    And  the system shows an warning message at the research project page
