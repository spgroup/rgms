@i9n
Feature: technical report
As a member of a research group
I want to add, remove and modify technical report I have published
so that I can generate web pages and reports containing these technical reports

Scenario: edit existing technical report with bad parameters web
    Given 	I am at the publications menu and the report "Evaluating Natural Languages System" is stored in the system with file name "EVLS.pdf"
    And 	I select the "Technical Report" option at the publications menu
   	When 	I select to view "Evaluating Natural Languages System" in resulting technical report list
   	And 	I change the report title to ""
    Then 	I select the "Alterar" option and a error message is shown

Scenario: remove existing technical report web
   	Given 	I am at the publications menu and the report "Evaluating Natural Languages System" is stored in the system with file name "EVLS.pdf"
    And 	I select the "Technical Report" option at the publications menu
   	When 	I select to view "Evaluating Natural Languages System" in resulting technical report list
   	And 	I select the edit button
    Then 	I can select the "Remover" option in edition after change the title to "Tech"

Scenario: new invalid technical report
	Given 	The system has no technical report entitled "NFL Languages System"
	When 	I create the technical report "NFL Languages System" with file name ""
	Then 	The technical report "NFL Languages System" is not properly stored by the system
	
Scenario: edit existing technical report wrong value
	Given	The system has an technical report entitled "NFL Languages System" with file name "NFL.pdf"
	When	I edit the technical report title from "NFL Languages System" to ""
	Then	The technical report "NFL Languages System" is not updated by the system
    
    
    
    
    
    
