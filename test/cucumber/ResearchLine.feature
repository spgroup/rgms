@i9n
Feature: research line
  As a member of a research group
  I want to add, remove and modify research lines I have create

  Scenario: Delete research line
    Given the system has a research line named "Novo Padrao Arquitetural MVCE" with a description "Nova arquitetura que promete revolucionar a web"
    When I remove the research line "Novo Padrao Arquitetural MVCE"
    Then the research line "Novo Padrao Arquitetural MVCE" is properly removed by the system

  Scenario: Update research line
    Given the system has a research line named "Teoria da informacao - Complexidade no espaco" with a description "P = NP"
    When I update the research line "Teoria da informacao - Complexidade no espaco" with a description "P != NP"
    Then the research line "Teoria da informacao - Complexidade no espaco" has the description updated to "P != NP"

  Scenario: Create invalid research line
    Given the system has no research line named "IA Avancada"
    When I create the research line named "IA Avancada" with empty description
    Then the research line "IA Avancada" is not stored, because is invalid

  Scenario: Create research line without member assigned
    Given: the system has no research line named "Redes Avancadas"
    When I create the research line "Redes Avancadas" with description "Redes de Computadores Avancadas" with no member assigned
    Then the research line "Redes Avancadas" is properly saved with no error

  Scenario: new research line web
    Given I am at the publications menu
    When I select the "Linha de pesquisa" option at the publications menu
    And I select the new research line option at the research line page
    Then I can fill the research line details

  Scenario: show research line web
    Given I am at the publications menu
    And the system has a research line named as "Teoria da informacao - Complexidade no espaco"
    When I select the "Linha de pesquisa" option at the publications menu
    And I click the research line "Teoria da informacao - Complexidade no espaco" at the research line list
    Then I can visualize the research line "Teoria da informacao - Complexidade no espaco" details


  Scenario: edit research line web
    Given I am logged as admin
    And the system has a research line named as "Teoria da informacao - Complexidade no espaco"
    And I am at the publications menu
    When I select the "Linha de pesquisa" option at the publications menu
    And I click the research line "Teoria da informacao - Complexidade no espaco" at the research line list
    When I click the edit button
    Then I can change the research line "Teoria da informacao - Complexidade no espaco" details

  #if ($XMLImport)
  @ignore
  Scenario: upload research lines with a file
    Given the system has some research line stored
    When I upload new research lines from the file "testelattes2.xml"
    Then the system has more research lines now

  Scenario: upload research lines without a file
    Given I am at the publications menu
    When I select the "Linha de pesquisa" option at the program menu
    And I select the upload button at the research line page
    Then I'm still on the research line page
    And an error message is showed at the research line page
  #end