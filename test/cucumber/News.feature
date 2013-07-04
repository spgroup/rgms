@i9n
Feature: news
  As research group member in RMGS system
  I want to publish, remove and modify news about our researches, schedule and any other thing.
  Jointly this I want too integrate an twitter account to publish news into RGMS system.
   
  Scenario: new news
    Given the system has no news with description "noticia1teste"
    When I create a news with description "noticia1teste"
    Then the news  with description  "noticia1teste" is properly stored by the system
    
  Scenario: delete news
    Given the system has a news with description "noticia1teste"
    When I delete a news with description "noticia1teste"
    Then the news  with "noticia1teste" doesnt exists
  
  Scenario: new news with existing 
    Given the system has a news with description "noticia1teste"
    When I create a news with description "noticia1teste"
    Then the news  with "noticia1teste" is not registered
  
  Scenario: integrate Twitter account
    Given the research group  in the system has no Twitter account "testtt@teste.com" associated
    When I associate the account "testtt@teste.com"
    Then the news can be retrieved by a Twitter post with "testtt@teste.com"
