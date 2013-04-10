@i9n
Feature: news
  As research group member in RMGS system
  I want to publish, remove and modify news about our researches, schedule and any other thing.
  Jointly this I want too integrate an twitter account to publish news into RGMS system.
   
  Scenario: new news
    Given the system has no news with description "noticia1teste" and date "07/04/2013"
    When I create a news with description "noticia1teste" and date "07/04/2013"
    Then the news  with description  "noticia1teste" and date "07/04/2013" is properly stored by the system
    
  Scenario: delete news
    Given the system has a news with description "noticia1teste" and date "07/04/2013"
    When I delete a news with description "noticia1teste" and date "07/04/2013"
    Then the news  with "noticia1teste" and date "07/04/2013" doesnt exists
  
  Scenario: new news with existing 
    Given the system has a news with description "noticia1teste" and date "07/04/2013"
    When I create a news with description "noticia1teste" and date "07/04/2013"
    Then the news  with "noticia1teste" and date "07/04/2013" is not registered
  
  Scenario: integrate Twitter account
    Given the research group "RGTST" in the system has no Twitter account associated
    When I associate the account "testtt@teste.com" to "RGTST" group
    Then the news of "RGTST" research group can be retrieved by a Twitter post of "testtt@teste.com"
    
    
/*****
 *** especificar a forma de atualização de noticia, via job, via ação no menu...
 *****/