@i9n
Feature: research project
  As a member of a research group
  I want to add, remove and modify research projects I have created

  Scenario: new research project
    Given the system has no research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
    When I create a research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas" with all required data
    Then the research project "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas" is properly stored by the system

  Scenario: duplicated research project
    Given  the system has a research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
    When I try to create a research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
    Then the research project "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas" is not stored twice
    And no research project stored is affected

  Scenario: remove research project
    Given the system has a research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
    And I am logged into the system as administrator of the research group named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
    When I remove the research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
    Then the research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas" is properly removed by the system

  Scenario: new research project without funders
    Given the system has no research project named as "Implementação Progressiva de Aplicações Orientadas a Aspectos"
    When I create a research project named as "Implementação Progressiva de Aplicações Orientadas a Aspectos" without funders
    Then the research project "Implementação Progressiva de Aplicações Orientadas a Aspectos" is properly stored by the system

#if($XMLUpload)
  Scenario: upload research project with a file
    Given I am logged into the system as administrator
    And the system has some research project stored
    When I upload new research projects from the file "testelattes2.xml"
    Then the system has more research projects now

  Scenario: upload research project without a file
    Given I am at the publications menu
    When I select the "Projeto de Pesquisa" option at the program menu
    And I select the upload button at the research project page
    Then I'm still on the research project page
    And the system shows an error message at the research project page
#end

  Scenario: new invalid research project with blank name
    Given the system has a research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
    When I try to create a research project named as ""
    Then the research project "" is not stored by the system because it is invalid
    And no research project stored is affected

  Scenario: new invalid research project with blank description
    Given the system has no research project named as "Implementação Progressiva de Aplicações Orientadas a Aspectos Complexas"
    When I try to create a research project named as "Implementação Progressiva de Aplicações Orientadas a Aspectos Complexas" with description field blank
    Then the research project "Implementação Progressiva de Aplicações Orientadas a Aspectos Complexas" is not stored by the system because it is invalid
    And no research project stored is affected

  Scenario: new research project with duplicated members
    Given the system has no research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas members duplicated"
    When I create a research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas members duplicated" with member field duplicated
    Then the research project "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas members duplicated" is properly stored by the system
    And the research project "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas members duplicated" does not have duplicated members

  Scenario: new research project web
    Given I am at new research project page
    And the system has no research project named as "Implementação Progressiva de Aplicações Orientadas a Aspectos Complexas web"
    When I can create a research project named as "Implementação Progressiva de Aplicações Orientadas a Aspectos Complexas web" with all required data
    Then it is shown in the research project list with name "Implementação Progressiva de Aplicações Orientadas a Aspectos Complexas web"

  Scenario: new invalid research project with blank name web
    Given I am at new research project page
    And the system has a research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
    When I try to create a research project named as ""
    Then the research project "" is not stored by the system because it is invalid
    And  I'm still on the new research project page
    And no research project stored is affected

  Scenario: new invalid research project with blank description web
    Given I am at new research project page
    And the system has no research project named as "Implementação Progressiva de Aplicações Orientadas a Aspectos Complexas web"
    When I try to create a research project named as "Implementação Progressiva de Aplicações Orientadas a Aspectos Complexas web" with description field blank on the web site
    Then the research project "Implementação Progressiva de Aplicações Orientadas a Aspectos Complexas web" is not stored by the system because it is invalid
    And  I'm still on the new research project page
    And no research project stored is affected

  Scenario: duplicated research project web
    Given I am at new research project page
    And the system has a research project named as "Implementação Progressiva de Aplicações Orientadas a Aspectos Complexas web"
    When I try to create a research project named as "Implementação Progressiva de Aplicações Orientadas a Aspectos Complexas web" on the web site
    Then  the system shows an warning message at the new research project page
    And the research project "Implementação Progressiva de Aplicações Orientadas a Aspectos Complexas web" is not shown duplicated in the research project list

  Scenario: list research projects where I am a member
    Given I am at the research project list page
    When I select the option to show my research projects
    Then the system shows a list with the research projects where I am a member

  Scenario: filter research projects by name
    Given  I am at the research projects list page
    When I fill the project name filter field with "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
    And select the option to filter the research projects
    Then the system shows the research projects with the name "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"

  Scenario: remove research project that does not exist
    Given the system has no research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
    And I am logged into the system as administrator
    When I remove the research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
    Then no research project stored is affected

  Scenario: edit existing research project
    Given the system has a research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
    And I am logged into the system as administrator of the research project named "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
    When I edit the research project "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas" in the system
    Then the data of the research project named "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas" is updated in the system
