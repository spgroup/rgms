@i9n
Feature: news
  As research group member in RMGS system
  I want to publish, remove and modify news about our researches, schedule and any other thing.
  Jointly this I want too integrate an twitter account to publish news into RGMS system.
   
  Scenario: new news
    Given the system has no news with description "noticia1teste" and date "07-04-2013"
    When I create a news with description "noticia1teste" and date "07-04-2013"
    Then the news  with description  "noticia1teste" and date "07-04-2013" is properly stored by the system
    
   Scenario: delete news
    Given the system has a news with description "noticia1teste" and date "07-04-2013"
    When I delete a news with description "noticia1teste" and date "07-04-2013"
    Then the news  with "noticia1teste" and date "07-04-2013" doesnt exists
   
   Scenario: new news with existing 
    Given the system has a news with description "noticia1teste" and date "07-04-2013"
    When I create a news with description "noticia1teste" and date "07-04-2013"
    Then the news  with "noticia1teste" and date "07-04-2013" is not registered   
   Scenario: insert Twitter account
    Given the research group "taes" in the system has no Twitter account associated
    When I associate the account "olhardigital" to "taes" group
    Then "taes" research group has a twitter account "olhardigital" registred
   @ignore 
   Scenario: retrive Twitter timeline
    Given the research group "RGTST" in the system has Twitter account "olhardigital" associated
    When I request to update the news from Twitter "olhardigital"
    Then news of "RGTST" research group has been updated