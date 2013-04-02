@i9n
Feature: conferencia
  As a member of a research group
  I want to add, remove and modify conferencias I have published
  so that I can generate web pages and reports containing these conferencias

 Scenario: new conferencia
    Given the system has no conferencia entitled "IV Conference on Software Product Lines"
    When I create the conferencia "IV Conference on Software Product Lines" with file name "IICSE.pdf"
    Then the conferencia "IV Conference on Software Product Lines" is properly stored by the system
	
 Scenario: duplicate conferencia
    Given the conferencia "I International Conference on Software Engineering" is stored in the system with file name "IICSE-0.pdf"
    When I create the conferencia "I International Conference on Software Engineering" with file name "IICSE-1.pdf"
    Then the conferencia "I International Conference on Software Engineering" is not stored twice
	
 Scenario: remove conferencia
    Given the conferencia "IV Conference on Software Product Lines" is stored in the system with file name "IICSE.pdf"
    When I remove the conferencia "IV Conference on Software Product Lines"
    Then the conferencia "IV Conference on Software Product Lines" is properly removed by the system
	
 Scenario: new conferencia web
    Given I am at the publications and conferencias menu
    When I select the conferencia option at the publications menu
    And I select the new conferencia option at the conferencia page
    Then I can fill the conferencia details
	
 Scenario: list conferencia web
    Given I am at the publications menu
    When I select the conferencia option at the publications menu
    Then a list of conferencias stored by the system is displayed at the conferencia page