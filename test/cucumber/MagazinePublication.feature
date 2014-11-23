@i9n
Feature: "MagazinePublication"
  As a member of a research group
  I want to add, remove and modify where my publications were published

Scenario: new magazine publication
   Given The article has no magazine with title "Theoretical Computer Science"
   When I try to create a magazine publication as "Theoretical Computer Science"
   And publication number "21"
   And publication place "São Paulo"
   And publication year "2013"
   Then the "Theoretical Computer Science" is properly stored by the system

Scenario: new magazine publication web
   Given my article was published in the magazine
   And I am at Article Page
   And its name is "Theoretical Computer Science"
   And publication number "21"
   And publication page "43-59"
   And publication place "São Paulo"
   And publication month "Julho"
   And publication year "2013"
   When I click on New Publication Spot
   Then the magazine publication is properly stored by the system
  #Format content to "Theoretical Computer Science, São Paulo, n. 2, p. 43-59, jul/2000.’
  #dado que a publicação na revista tem um formato, foi especificado nessa última linha

 Scenario: edit magazine publication page
   Given I have a magazine publication entitled "Theoretical Computer Science"
   When I want to add or correct the page field
   Then I can edit and save the information

Scenario: edit magazine publication page web
   Given I am at Article Page 
   And I have a magazine publication entitled "Theoretical Computer Science"
   And I want to correct the page field
   When I click on 'edit' on the section ‘This article was published in:’
   Then I have all the fields filled in 
   And I can edit the page or any of the fields
   And I store it 

####vml#####

#if($uploadPublicacao)
Scenario: make the upload of the PDF presentation in "Publicacao"
   Given I am at Article Page
   And I want to upload the "Publicacao.pdf" file 
   When I choose the file by browsing it	 
   Then The file is properly stored
#end

#if($uploadPublicacao)
Scenario: make the upload of the PDF presentation in "Publicacao" web
   Given I want to upload the PDF presentation "Publicacao.pdf"
   When I	choose "Publicacao.pdf" file
   Then I see it on the list
#end
