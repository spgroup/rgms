@i9n
Feature: news
  As research group member in RMGS system
  I want to publish, remove and modify news about our researches, schedule and any other thing.
  Jointly this I want too integrate an twitter account to publish news into RGMS system.

  Scenario: new news
    Given the system has no news with description "noticia1teste" and date "07-04-2012" for "SPG" research group
    When I create a news with description "noticia1teste" and date "07-04-2012" for "SPG" research group
    Then the news  with description  "noticia1teste", date "07-04-2012" and "SPG" research group is properly stored by the system


  Scenario: delete news
    Given the system has a news with description "noticiaTeste" and date "07-04-2012" for "SPG" research group
    When I delete a news with description "noticiaTeste" and date "07-04-2012" for "SPG" research group
    Then the news with "noticiaTeste" and date "07-04-2012" doesnt exists to "SPG" research group


  Scenario: new news with existing
    Given the system has a news with description "noticiaTeste" and date "07-04-2012" for "SPG" research group
    When I create a news with description "noticiaTeste" and date "07-04-2012" for "SPG" research group
    Then the news  with "noticiaTeste" and date "07-04-2012" is not registered to "SPG" research group


  Scenario: integrate Twitter account
    Given the research group "SPG" in the system has no Twitter account associated
    When I associate the account "@HumanBrainProj" to "SPG" group
    Then "SPG" research group has a twitter account "@HumanBrainProj" registred
    #Then the news can be retrieved by a Twitter post with "@testetwitteracount"

  Scenario: update news from twitter account
    Given the research group "SPG" in the system has a Twitter account "@HumanBrainProj" associated
     When I request to update the news from Twitter to research group "SPG"
     Then news of "SPG" research group has been updated

  Scenario: consecutive update without duplicate news
    Given the research group "SPG" in the system has a Twitter account "@HumanBrainProj" associated
    And   twitter account associated with "SPG" research group has been updated once
    When  I request to update the news from Twitter to research group "SPG"
    Then  there is no duplicated news in Twitter account associated with research group "SPG"