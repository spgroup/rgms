@i9n
Feature: technical report
As a member of a research group
I want to add, remove and modify technical report I have published
so that I can generate web pages and reports containing these technical reports

Scenario: new invalid technical report
	Given 	The system has no technical report entitled "NFL Languages System"
	When 	I create the technical report "NFL Languages System" with file name "NLS.pdf"
	Then 	The technical report "NFL Languages System" is not properly stored by the system
	
Scenario: edit existing technical report with empty title
	Given	The system has an technical report entitled "NFL Languages System" with file name "NFL.pdf"
	When	I edit the technical report title from "NFL Languages System" to ""
	Then	The technical report "NFL Languages System" is not updated by the system
	
Scenario: edit existing technical report with invalid title web
    Given  I am at the technical reports page and the technical report "Joe-E" is stored in the system with file name "Joee.pdf"
    When   I select to view "Joe-E" in resulting list and I change the technical report title to ""
    Then   I cannot select the "Alterar" option
    
Scenario: remove existing technical report web
    Given  I am at the technical reports page and the technical report "Joe-E" is stored in the system with file name "Joee.pdf"
    When   I select to view "Joe-E" in resulting list
    Then   the technical report details are showed and I can select the option to remove
    And    the technical report "Joe-E" is properly removed by the system