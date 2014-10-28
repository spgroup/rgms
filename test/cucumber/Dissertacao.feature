@i9n
Feature: Dissertation Tests
  As a member of a research group
  I want to add, remove and modify dissertations I have added

  Scenario:  first dissertation and delete
    Given the system has no dissertation stored
    When I create the dissertation "New dissertation" with file name "dissertation.txt" and school "federal"
    And I am at the publications menu
    And I select the "Dissertacao" option at the program menu
    And I select "Delete dissertation" at the dissertation page
    And I delete "New dissertation"
    Then the system has no dissertation stored

  Scenario: new dissertation without school
    Given the system has no dissertation entitled "Dissertation without school"
    When I create the dissertation "Dissertation without school" with file name "Dissertationwithoutschool.txt" without school
    Then the system has no dissertation entitled "Dissertation without school"

  Scenario: new dissertation without address
    Given the system has no dissertation entitled "Dissertation without address"
    When I create the dissertation "Dissertation without address" with file name "Dissertationwithoutaddress.txt" without address
    Then the system has no dissertation entitled "Dissertation without address"

  Scenario: new dissertation
    Given the system has no dissertation entitled "New dissertation"
    When I create the dissertation "New dissertation" with file name "Newdissertation.txt" and school "UFPE"
    Then the dissertation "New dissertation" is properly stored by the system

  Scenario: new dissertation duplicated
    Given the dissertation "Dissertation duplicated" is stored in the system with file name "Dissertationduplicated.txt"
    When I create the dissertation "Dissertation duplicated" with file name "Dissertationduplicated2.txt" and school "UFPE"
    Then the dissertation "Dissertation duplicated" is not stored twice

  Scenario: new dissertation without file
    Given I am at the publications menu
    When I select the "Dissertacao" option at the program menu
    And I select the new dissertation option at the dissertation page
    And I cant add the dissertation without a file
    Then the system has no dissertation entitled "Dissertacao sem arquivo"

  Scenario: upload a dissertation and system has no dissertation stored
    Given the system has no dissertation entitled "New dissertation"
    When I upload a new dissertation "curriculo4.xml" with title "New dissertation"
    Then the system has more dissertations now

  Scenario: new dissertation with file
    Given I am at the publications menu
    When I select the "Dissertacao" option at the program menu
    And I select the new dissertation option at the dissertation page
    And I can add the dissertation with a file "Modularity.pdf"
    Then the system has a dissertation entitled "Dissertacao Teste 1"

  Scenario: edit dissertation
    Given the dissertation "Edit dissertation" is stored in the system with file name "Editdissertation.txt"
    And the system has no dissertation entitled "Edit dissertation reviewed"
    When    I edit the dissertation title from "Edit dissertation" to "Edit dissertation reviewed"
    Then    the dissertation "Edit dissertation" is properly updated by the system

  Scenario: delete dissertation
    Given the dissertation "Delete dissertation" is stored in the system with file name "Deletedissertation.txt"
    Given I am at the publications menu
    When I select the "Dissertacao" option at the program menu
    And I select "Delete dissertation" at the dissertation page
    And I delete "Delete dissertation"
    Then the system has no dissertation entitled "Delete dissertation"

  Scenario: upload dissertation without a file
    Given I am at the publications menu
    When I select the "Dissertacao" option at the program menu
    And I select the upload button at the dissertation page
    Then I'm still on dissertation page

  Scenario: upload dissertation with a file
    Given the system has some dissertation stored
    Given the system has no dissertation entitled "New dissertation"
    When I upload a new dissertation "curriculo3.xml" with title "New dissertation"
    Then the system has more dissertations now
    
  Scenario: upload a dissertation and system has no dissertation stored
    Given the system has no dissertation entitled "New dissertation"
    When I upload a new dissertation "curriculo2.xml" with title "New dissertation"
    Then the system has more dissertations now
    
#if ($contextualInformation)

  Scenario: create a new dissertation with user data already filled by default
    Given I am at the publications menu
    When I select the "Dissertacao" button at the program menu
    And I select button the new dissertation at the dissertation page
    Then I see my user listed as an author member of dissertation by default
    And I see my school name as school of dissertation by default
#end

  Scenario: download a dissertation
    Given I am at the publications menu
    And the system has a Dissertacao with file named "dissertation.txt"
    When I select the download button
    Then I can download the file named "dissertation.txt"
