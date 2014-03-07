@i9n
Feature: research project
  As a member of a research group
  I want to add, remove and modify research projects I have created

  Scenario: new research project
    Given the system has no research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
    When I create a research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
    Then the research project "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas" is properly stored by the system

  Scenario: duplicated research project
    Given  the system has a research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
    When I create a research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
    Then the research project "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas" has not store twice

  Scenario: remove research project
    Given  the system has a research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
    When I remove the research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
    Then the research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas" is properly removed by the system

  Scenario: new research project without funders
    Given the system has no research project named as "Implementação Progressiva de Aplicações Orientadas a Aspectos"
    When I create a research project named as "Implementação Progressiva de Aplicações Orientadas a Aspectos" without funders
    Then the research project "Implementação Progressiva de Aplicações Orientadas a Aspectos" is properly stored by the system

  #if ($XMLUpload)
  Scenario: upload research project with a file
    Given the system has some research project stored
    When I upload a research project "testelattes2.xml"
    Then the system has more research project now

  Scenario: upload research project without a file
    Given I am at the publications menu
    When I select the "Projeto de Pesquisa" option at the program menu
    And I select the upload button at the research project page
    Then I'm still on research project page
    And the system shows an error message on research project page
  #end