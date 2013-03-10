@i9n
Feature: research line
  As a member of a research group
  I want to add, remove and modify research lines I have done
 
	Scenario: Delete research line
	Given the system has a research line named "Novo Padrao Arquitetural MVCE"
	When I remove the research line "Novo Padrao Arquitetural MVCE"
	Then the research line "Novo Padrao Arquitetural MVCE" is properly removed by the system
			
	Scenario: Update research line
	Given the system has a research line named "Teoria da informacao - Complexidade no espaco" with a description "P=NP"
	When I update the research line "Teoria da informacao - Complexidade no espaco" with a description "P != NP"
	Then the research line "Teoria da informacao - Complexidade no espaco" has description "P != NP"
	
	Scenario: Create invalid research line
	Given the system has no research line named "IA Avancada"
	When I create the research line named "IA Avancada" with empty description
	Then the research line "IA Avancada" is not stored, because is invalid
	
	Scenario: new research line web
    Given I am at the publications menu
    When I select the "Linha de pesquisa" option at the publications menu
    And I select the new research line option at the research line page
    Then I can fill the research line details
    
    Scenario: visualize research line web
    Given the system has a research line named as "Teoria da informacao - Complexidade no espaco"
    And I am at the publications menu    
    When I select the "Linha de pesquisa" option at the publications menu
    And I click the research line "Teoria da informacao - Complexidade no espaco" at the research line list
    Then I can visualize the research line "Teoria da informacao - Complexidade no espaco" details
    
    Scenario: edit research line web
    Given the system has a research line named as "Teoria da informacao - Complexidade no espaco"
    And I am at the visualize page of the research line "Teoria da informacao - Complexidade no espaco"
    When I click the edit button
    Then I can change the research line "Teoria da informacao - Complexidade no espaco" details
	