Feature: "Publicacao"
  As a member of a research group
  I want to add, remove and modify where my publications were published

Scenario: new publication spot in magazine
   Given I want to add informations of an article that had beed published in a magazine
   When I try to name a publication in a magazine as ‘Psicologia: Teoria e Prática’ 
   And publication number ‘21’ 
   And publication page ‘43-59’
   And publication place ‘São Paulo’ 
   And publication date ‘Jul/2013’
   Then this publication is stored

Scenario: new publication spot in magazine
   Given my article was published in the magazine 
   And its name is ‘Psicologia: Teoria e Prática’ 
   And publication number ‘21’ 
   And publication page ‘43-59’
   And publication place ‘São Paulo’ 
   And publication date ‘Jul/2013’
   When I click on New Publication Spot
   Then I am on Article Page with the new information added as at the section as ‘Psicologia: Teoria e Prática, São Paulo, n. 2, p. 43-59, jul/2000.’
#dado que a publicação na revista tem um formato, foi especificado nessa última linha

#Nesses dois próximos cenários é garantindo que existe um JavaScript que ajuda
Scenario: edit publication spot in magazine
   Given I have all the information of an article published in a magazine 
   And I want to correct any of the fields
   When I am at Article Page
   Then I can edit any information
   And I store it
   
Scenario: edit publication spot in magazine
   Given I am at Article Page 
   I have all the information of an article published in a magazine 
   And I want to correct any of the fields 
   When I click on edit on the section ‘This article was published in:’	 
   Then I have all the fields filled in 
   And I can edit anything
   And I store it 

####vml#####

#if($uploadPublicacao)
Scenario: make the upload of the PDF presentation in "Publicacao"
   Given I am at Article Page
   And I want to upload the "Publicacao.pdf" file 
   When I choose the file by browsing it	 
   Then I click on the "upload" button
#end

#if($uploadPublicacao)
Scenario: make the upload of the PDF presentation in "Publicacao"
   Given I want to upload the PDF presentation "Publicacao.pdf"
   When I	choose "Publicacao.pdf" file
   Then I see it on the list
#end
