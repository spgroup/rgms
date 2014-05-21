@i9n
Feature: research project
  As a member of a research group
  I want to add, remove and modify research projects I have created

  Scenario: new research project
    Given the system has no research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
	And I am logged in the system
    When I create a research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
	And I entered all the required data of research project
    Then the research project "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas" is properly stored by the system

  Scenario: duplicated research project
    Given  the system has a research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
    When I create a research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
    Then the research project "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas" is not store twice

  Scenario: remove research project
    Given the system has a research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
	And I am logged into the system as administrator of the research group named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
    When I remove the research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
    Then the research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas" is properly removed by the system

  SScenario: new research project without funders
    Given the system has no research project named as "Implementação Progressiva de Aplicações Orientadas a Aspectos"
    When I create a research project named as "Implementação Progressiva de Aplicações Orientadas a Aspectos"
	And the funders list of the research project is empty
    Then the research project "Implementação Progressiva de Aplicações Orientadas a Aspectos" is properly stored by the system

  #if ($XMLUpload)
  Scenario: upload research project with a file
    Given the system has some research project stored
    When I upload new research projects from the file "testelattes2.xml"
    Then the system has more research projects now

  Scenario: upload research project without a file
    Given I am at the publications menu
    When I select the "Projeto de Pesquisa" option at the program menu
    And I select the upload button at the research project page
    Then I'm still on the research project page
    And the system shows an error message at the research project page
  #end
  
  Scenario: list member research projects
	Given I am logged in the system
	And I am at the research projects menu
	When I select the "Listar" option at research projects menu
	Then the system shows the research projects where I am member

  Scenario: filter research projects
	Given I am logged in the system
	And I am at the research projects list page
	When I select the option "Filtrar"
	And fill the filter fields
	Then the system shows the research projects list filtered

  Scenario: remove research project that does not exist
	Given the system has no research projects named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
	And I am logged into the system as administrator
	When I try to remove a research project named "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
	Then nothing happens to the research projects stored

  Scenario: edit existing research project
	Given the research project named "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas" is stored in the system
	And I am logged into the system as administrator of the research project named "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"
	When I try to edit the research project "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas" in the system
	And I changed the data of the research project
	Then the data of the research project named "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas" is updated in the system