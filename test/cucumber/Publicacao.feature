Feature: "Publicacao"
  As a member of a research group
  I want to add, remove and modify where my publications were published
  
-- (gml)

Scenario: new publication spot in magazine
	Given I have an article published in a magazine
When I am at Article Page
Then I can add this information in it

Scenario: new publication spot in magazine
Given my article was published in the magazine ‘Psicologia: Teoria e Prática’ 
And publication number ‘21’ 
And publication page ‘43-59’
And publication place ‘São Paulo’ 
And publication date ‘Jul/2013’
When I click on New Publication Spot
Then I am on Article Page with the new information added as at the section ‘This article was published in:’ ‘Psicologia: Teoria e Prática, São Paulo, n. 2, p. 43-59, jul/2000.’

Scenario: edit publication spot in magazine
	Given I have an information of an article published in a magazine 
And I want to correct any of the fields 
When I am at Article Page
Then I can edit this information
And I return to the Article Page

Scenario: edit publication spot in magazine
	Given I have an information of an article published in a magazine 
And I want to correct any of the fields 
When I am at Article Page
And I click on edit on the section ‘This article was published in:’	
Then I have all the fields filled in and I can edit anything
