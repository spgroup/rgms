#Para executar os testes, alterar o nome do usuário admin para "Paulo Henrique Monteiro Borba" em BootStrap.groovy
@i9n
Feature: XMLImport
  As a member of a research group
  I want to import a xml file
  So that the system register the corresponding publications in my profile

  Scenario: invalid file web
    Given I am at the "Import XML File" Page
    When I select the "upload" button
    And I upload the file "cv.pdf"
    Then the system outputs an error message
    And no new publication is stored by the system
    And the previously stored publications do not change

  Scenario: invalid file
    Given the system has some publications stored
    When I upload the file "cv.pdf"
    Then no new publication is stored by the system
    And the previously stored publications do not change

  @ignore
  Scenario: unformatted xml file
    Given the system has some publications stored
    When I upload the unformatted file "cvUnformatted.xml"
    Then no new publication is stored by the system
    And the previously stored publications do not change

  Scenario: no file web
    Given the system has some publications stored
    When I click on "upload" at the "Import XML File" Page without selecting a xml file
    Then the system outputs an error message
    And no new publication is stored by the system
    And the previously stored publications do not change

  Scenario: new publication
    Given the system has some publications stored
    And the system has no journal article entitled "An Abstract Equivalence Notion for Object Models" with journal "Eletronic Notes In Theoretical Computer Science" authored by me
    When  I upload the file "cv.xml" that contains a journal article entitled "An Abstract Equivalence Notion for Object Models" with journal "Eletronic Notes In Theoretical Computer Science" authored by me
    Then the system outputs a list of imported publications that contains the journal article entitled "An Abstract Equivalence Notion for Object Models" with status "stable"
    And no new publication is stored by the system
    And the previously stored publications do not change

  @ignore
  Scenario: confirm import of new publication
    Given the system has some publications stored
    And the system has no journal article entitled "An Abstract Equivalence Notion for Object Models" authored by me
    And the file "cv.xml", which contains a journal article entitled "An Abstract Equivalence Notion for Object Models" authored by me, is uploaded
    When I confirm the import of the journal article entitled "An Abstract Equivalence Notion for Object Models" with status "stable"
    Then the journal article entitled "An Abstract Equivalence Notion for Object Models" is stored by the system
    And the journal article entitled "An Abstract Equivalence Notion for Object Models" with status "stable" is removed from the list of imported publications
    And the previously stored publications do not change

  @ignore
  Scenario: cancel import of new publication
    Given the system has some publications stored
    And the system has no journal article entitled "An Abstract Equivalence Notion for Object Models" authored by me
    And the file "cv.xml", which contains a journal article entitled "An Abstract Equivalence Notion for Object Models" authored by me, is uploaded
    When I cancel the import of the journal article entitled "An Abstract Equivalence Notion for Object Models" with status "stable"
    Then the journal article entitled "An Abstract Equivalence Notion for Object Models" with status "stable" is removed from the list of imported publications
    And no new publication is stored by the system
    And the previously stored publications do not change

  Scenario: publications with same name and different type
    Given the system has a journal article entitled "An Abstract Equivalence Notion for Object Models" with journal "Eletronic Notes In Theoretical Computer Science" authored by me, among several publications
    When I upload the file "cv.xml" that contains a conference article entitled "An Abstract Equivalence Notion for Object Models" from "Seventh Brazilian Conference on Formal Methods" authored by me
    Then no new publication is stored by the system
    And the previously stored publications do not change
    And the system outputs a list of imported publications that contains the conference article entitled "An Abstract Equivalence Notion for Object Models" with status "stable"

  Scenario: duplicated publication with equal details
    Given the system has a journal article entitled "An Abstract Equivalence Notion for Object Models" with journal "Eletronic Notes In Theoretical Computer Science" authored by me, among several publications
    When I upload the file "cv.xml" that also contains a journal article entitled "An Abstract Equivalence Notion for Object Models" with the same details information
    Then the previously stored publications do not change
    And the system outputs a list of imported publications that does not contain the journal article entitled "An Abstract Equivalence Notion for Object Models"

  Scenario: duplicated publications with conflicted details
    Given the system has a journal article entitled "An Abstract Equivalence Notion for Object Models" with journal "Eletronic Notes In Theoretical Computer Science" and pages "3-21" that is authored by me, among several publications
    When  I upload the file "cv-duplicatedConflictedDetails.xml" that contains a journal article entitled "An Abstract Equivalence Notion for Object Models" with journal "Eletronic Notes In Theoretical Computer Science" and pages "3-10" authored by me
    Then no new publication is stored by the system
    And the previously stored publications do not change
    And the system outputs a list of imported publications that contains the journal article entitled "An Abstract Equivalence Notion for Object Models" with status "conflicted"

  @ignore
  Scenario: confirm import of publication with conflicted details
    Given the system has a journal article entitled "An Abstract Equivalence Notion for Object Models" with year "2003" that is authored by me, among several publications
    And the file "cv-duplicatedConflictedDetails.xml", which contains a journal article entitled "An Abstract Equivalence Notion for Object Models" authored by me with year "2004", is uploaded
    When I confirm the import of the journal article entitled "An Abstract Equivalence Notion for Object Models" with status "conflicted"
    Then the year of the previously stored journal article entitled "An Abstract Equivalence Notion for Object Models" is updated to "2004" by the system
    And the journal article entitled "An Abstract Equivalence Notion for Object Models" with status "conflicted" is removed from the list of imported publications

  @ignore
  Scenario: cancel import of publication with conflicted details
    Given the system has a journal article entitled "An Abstract Equivalence Notion for Object Models" with year "2003" that is authored by me, among several publications
    And the file "cv-duplicatedConflictedDetails.xml", which contains a journal article entitled "An Abstract Equivalence Notion for Object Models" authored by me with year "2004", is uploaded
    When I cancel the import of the journal article entitled "An Abstract Equivalence Notion for Object Models" with status "conflicted"
    Then the journal article entitled "An Abstract Equivalence Notion for Object Models" with status "conflicted" is removed from the list of imported publications
    And the previously stored publications do not change

  #Esse cenário não faz sentido hoje, porque todos os atributos de uma publicação são obrigatórios, com exceção do filename.
  #Daí que não tem como uma publicação importada ter informações extra em relação à uma publicação já existente no sistema.
  @ignore
  Scenario: duplicated publications with different details
    Given the system has a journal article entitled "An Abstract Equivalence Notion for Object Models" with file name "ArticleExample.pdf" that is authored by me, among several publications
    When  I upload the file "cv.xml" that contains a journal article entitled "An Abstract Equivalence Notion for Object Models" authored by me
    Then no new publication is stored by the system
    And the previously stored publications do not change
    And the system outputs a list of imported publications that contains the journal article entitled "An Abstract Equivalence Notion for Object Models" with status "to update"

  @ignore
  Scenario: confirm import of publication with different details
    Given the system has a conference article entitled "An Abstract Equivalence Notion for Object Models" with pages "1-14" that is authored by me, among several publications
    And the file "cv-duplicatedDifferentDetails.xml", that contains a conference article entitled "An Abstract Equivalence Notion for Object Models" authored by me with locale "Recife", is uploaded
    When I confirm the import of the conference article entitled "An Abstract Equivalence Notion for Object Models" with status "to update"
    Then the system updates the previously stored journal article entitled "An Abstract Equivalence Notion for Object Models" to include the locale "Recife"
    And the journal article entitled "An Abstract Equivalence Notion for Object Models" with status "to update" is removed from the list of imported publications

  @ignore
  Scenario: cancel import of publication with different details
    Given the system has a conference article entitled "An Abstract Equivalence Notion for Object Models" with pages "1-14" that is authored by me, among several publications
    And the file "cv-duplicatedDifferentDetails.xml", that contains a conference article entitled "An Abstract Equivalence Notion for Object Models" authored by me with locale "Recife", is uploaded
    When I cancel the import of the conference article entitled "An Abstract Equivalence Notion for Object Models" with status "to update"
    And the conference article entitled "An Abstract Equivalence Notion for Object Models" with status "conflicted" is removed from the list of imported publications
    And the previously stored publications do not change

  #if ($ResearchLine)
  Scenario: new research line
    Given the system has some research lines stored
    And the system has no research line named as "Modularidade Emergente" associated with me
    When  I upload the file "cv.xml" that contains a research line named as "Modularidade Emergente"
    Then the system outputs a list of imported research lines that contains the one named as "Modularidade Emergente" with status "stable"
    And no new research line is stored by the system
    And the previously stored research lines do not change


  Scenario: confirm import of new research line
    Given the system has some research lines stored
    And the system has no research line named as "Modularidade Emergente" associated with me
    And the file "cv.xml", which contains a research line named as "Modularidade Emergente", is uploaded
    When I confirm the import of the research line named as "Modularidade Emergente" with status "stable"
    Then the research line named as "Modularidade Emergente" is stored by the system
    And the research line named as "Modularidade Emergente" with status "stable" is removed from the list of imported research lines
    And the previously stored research lines do not change

  @ignore
  Scenario: cancel import of new research line
    Given the system has some research lines stored
    And the system has no research line named as "Modularidade Emergente" associated with me
    And the file "cv.xml", which contains a research line named as "Modularidade Emergente", is uploaded
    When I cancel the import of the research line named as "Modularidade Emergente" with status "stable"
    Then the research line named as "Modularidade Emergente"  is not stored by the system
    And the research line named as "Modularidade Emergente" with status "stable" is removed from the list of imported research lines
    And the previously stored research lines do not change

  @ignore
  Scenario: duplicated research line
    Given the system has a research line named as "Modularidade Emergente" associated with me, with description "Investigar formas alternativas de modularidade.", among others research lines
    When  I upload the file "cv-duplicatedRLE.xml" that contains a research line named as "Modularidade Emergente" with description "Investigar formas alternativas de modularidade."
    Then the system outputs a list of imported research lines that does not contain the one named as "Modularidade Emergente"
    And the previously stored research lines do not change

  @ignore
  Scenario: duplicated research line with conflicted details
    Given the system has a research line named as "Modularidade Emergente" associated with me, with description "Investigar formas alternativas de modularidade.", among others research lines
    When  I upload the file "cv-duplicatedRLC.xml" that contains a research line named as "Modularidade Emergente" with description " Investigar formas alternativas de modularidade com o intuito de promover a produtividade deste processo e a qualidade dos seus produtos."
    Then the system outputs a list of imported research lines that contains the one named as "Modularidade Emergente" with status "conflicted"
    And no new research line is stored by the system
    And the previously stored research lines do not change

  @ignore
  Scenario: confirm import of research line with conflicted details
    Given the system has a research line named as "Modularidade Emergente" associated with me, with description "Investigar formas alternativas de modularidade.", among others research lines
    And the file "cv-duplicatedRLC.xml", which contains a research line named as "Modularidade Emergente" with description "Investigar formas alternativas de modularidade com o intuito de promover a produtividade deste processo e a qualidade dos seus produtos.", is uploaded
    When I confirm the import of the research line named as "Modularidade Emergente" with status "conflicted"
    Then the description of the previously stored research line named as "Modularidade Emergente" is updated to "Investigar formas alternativas de modularidade com o intuito de promover a produtividade deste processo e a qualidade dos seus produtos." by the system
    And the research line named as "Modularidade Emergente" with status "conflicted" is removed from the list of imported research lines

  @ignore
  Scenario: cancel import of research line with conflicted details
    Given the system has a research line named as "Modularidade Emergente" associated with me, with description "Investigar formas alternativas de modularidade.", among others research lines
    And the file "cv-duplicatedRLC.xml", which contains a research line named as "Modularidade Emergente" with description "Investigar formas alternativas de modularidade com o intuito de promover a produtividade deste processo e a qualidade dos seus produtos.", is uploaded
    When I cancel the import of the research line named as "Modularidade Emergente" with status "conflicted"
    And the research line named as "Modularidade Emergente" with status "conflicted" is removed from the list of imported research lines
    And the previously stored research lines do not change
  #end

  #if($ResearchProject)
  Scenario: new research project
    Given the system has some research projects stored
    And the system has no research project named as "Desenvolvimento Formal de Componentes de Software Reutilizáveis"
    When  I upload the file "cv.xml" that contains a research project named as "Desenvolvimento Formal de Componentes de Software Reutilizáveis"
    Then no new research project is stored by the system
    And the previously stored research projects do not change
    And the system outputs a list of imported research projects that contains the one named as "Desenvolvimento Formal de Componentes de Software Reutilizáveis" with status "stable"

  @ignore
  Scenario: confirm import of new research project
    Given the system has some research projects stored
    And the system has no research project named as "Modularização Emergente para Linhas de Produtos de Software"
    And the file "cv.xml", which contains a research project named as "Modularização Emergente para Linhas de Produtos de Software", is uploaded
    When I confirm the import of the research project named as "Modularização Emergente para Linhas de Produtos de Software" with status "stable"
    Then the research project named as "Modularização Emergente para Linhas de Produtos de Software" is stored by the system
    And the research project named as "Modularização Emergente para Linhas de Produtos de Software" with status "stable" is removed from the list of imported research projects
    And the previously stored research projects do not change

  @ignore
  Scenario: cancel import of new research project
    Given the system has some research projects stored
    And the system has no research project named as "Modularização Emergente para Linhas de Produtos de Software"
    And the file "cv.xml", which contains a research project named as "Modularização Emergente para Linhas de Produtos de Software", is uploaded
    When I cancel the import of the research project named as "Modularização Emergente para Linhas de Produtos de Software" with status "stable"
    Then the research project named as "Modularização Emergente para Linhas de Produtos de Software" is not stored by the system
    And the research project named as "Modularização Emergente para Linhas de Produtos de Software" with status "stable" is removed from the list of imported research projects
    And the previously stored research projects do not change

  Scenario: duplicated research project
    Given the system has a research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas", among several research projects
    When  I upload the file "cv.xml" that also contains a research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas" with the same details information
    Then the previously stored research projects do not change
    And the system outputs a list of imported research projects that does not contain the one named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas"

  Scenario: duplicated research project with conflicted details
    Given the system has a research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas" with status "CONCLUIDO", among several research projects
    When  I upload the file "cv-duplicatedRPC.xml" that also contains a research project named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas" with status "ENCERRADO"
    Then no new research project is stored by the system
    And the previously stored research projects do not change
    And the system outputs a list of imported research projects that contains the one named as "Implementação Progressiva de Aplicações Orientadas a Objetos Complexas" with status "conflicted"

  @ignore
  Scenario: confirm import of research project with conflicted details
    Given the system has a research project named as "Modularização Emergente para Linhas de Produtos de Software" with status "EM_ANDAMENTO", among several research projects
    And the file "cv-duplicatedRPC.xml", which contains a research project named as "Modularização Emergente para Linhas de Produtos de Software" with status "ENCERRADO", is uploaded
    When I confirm the import of the research project named as "Modularização Emergente para Linhas de Produtos de Software" with status "conflicted"
    Then the status of the previously stored research project named as "Modularização Emergente para Linhas de Produtos de Software" is updated to "ENCERRADO" by the system
    And the research project named as "Modularização Emergente para Linhas de Produtos de Software" with status "conflicted" is removed from the list of imported research projects

  @ignore
  Scenario: cancel import of research project with conflicted details
    Given the system has a research project named as "Modularização Emergente para Linhas de Produtos de Software" with status "EM_ANDAMENTO", among several research projects
    And the file "cv-duplicatedRPC.xml", which contains a research project named as "Modularização Emergente para Linhas de Produtos de Software" with status "ENCERRADO", is uploaded
    When I cancel the import of the research project named as "Modularização Emergente para Linhas de Produtos de Software" with status "conflicted"
    And the research project named as "Modularização Emergente para Linhas de Produtos de Software" with status "conflicted" is removed from the list of imported research projects
    And the previously stored research projects do not change
  #end

  #if($Orientation)
  @ignore
  Scenario: new orientation
    Given the system has some orientations stored
    And the system has no master's orientation entitled "Structuring Adaptive Aplications using AspectJ"
    When  I upload the file "cv.xml" that contains a master's orientation entitled "Structuring Adaptive Aplications using AspectJ"
    Then the system outputs a list of imported orientations that contains the one entitled "Structuring Adaptive Aplications using AspectJ" with status "stable"
    And no new orientation is stored by the system
    And the previously stored orientations do not change

  @ignore
  Scenario: confirm import of new orientation
    Given the system has some orientations stored
    And the system has no master's orientation entitled "Structuring Adaptive Aplications using AspectJ"
    And the file "cv.xml", which contains a master's orientation entitled "Structuring Adaptive Aplications using AspectJ", is uploaded
    When I confirm the import of the master's orientation entitled "Structuring Adaptive Aplications using AspectJ" with status "stable"
    Then the master's orientation entitled "Structuring Adaptive Aplications using AspectJ" is stored by the system
    And the master's orientation entitled "Structuring Adaptive Aplications using AspectJ" with status "stable" is removed from the list of imported orientations
    And the previously stored orientations do not change

  @ignore
  Scenario: cancel import of new orientation
    Given the system has some orientations stored
    And the system has no master's orientation entitled "Structuring Adaptive Aplications using AspectJ"
    And the file "cv.xml", which contains a master's orientation entitled "Structuring Adaptive Aplications using AspectJ", is uploaded
    When I cancel the import of the master's orientation entitled "Structuring Adaptive Aplications using AspectJ" with status "stable"
    Then the master's orientation entitled "Structuring Adaptive Aplications using AspectJ" is not stored by the system
    And the master's orientation entitled "Structuring Adaptive Aplications using AspectJ" with status "stable" is removed from the list of imported orientations
    And the previously stored orientations do not change

  @ignore
  Scenario: orientations with same name and different type
    Given the system has a master's orientation entitled "Structuring Adaptive Aplications using AspectJ"
    When  I upload the file "cv-orientation.xml" that contains a doctorate's orientation entitled "Structuring Adaptive Aplications using AspectJ"
    Then the doctorate's orientation entitled "Structuring Adaptive Aplications using AspectJ" is stored by the system
    And the previously stored master's orientation does not change

  @ignore
  Scenario: orientations with same name and different type
    Given the system has a master's orientation entitled "Structuring Adaptive Aplications using AspectJ", among several orientations
    When I upload the file "cv-orientation.xml" that contains a doctorate's orientation entitled "Structuring Adaptive Aplications using AspectJ"
    Then the system outputs a list of imported orientations that contains the doctorate's orientation entitled "Structuring Adaptive Aplications using AspectJ" with status "stable"
    And no new orientation is stored by the system
    And the previously stored orientations do not change

  @ignore
  Scenario: duplicated orientation with equal details
    Given the system has a master's orientation entitled "Structuring Adaptive Aplications using AspectJ" with year "2004", among several orientations
    When I upload the file "cv-duplicatedOrientationE.xml" that contains a master's orientation entitled "Structuring Adaptive Aplications using AspectJ" with year "2004"
    Then the system outputs a list of imported orientations that does not contain the master's orientation entitled "Structuring Adaptive Aplications using AspectJ" with year "2004"
    And the previously stored orientations do not change

  @ignore
  Scenario: duplicated orientation with conflicted details
    Given the system has a master's orientation entitled "Structuring Adaptive Aplications using AspectJ" with year "2004", among several orientations
    When  I upload the file "cv-duplicatedOrientationC.xml" that contains a master's orientation entitled "Structuring Adaptive Aplications using AspectJ" with year "2003"
    Then the system outputs a list of imported orientations that contains the master's orientation entitled "Structuring Adaptive Aplications using AspectJ" with status "conflicted"
    And no new orientation is stored by the system
    And the previously stored orientations do not change

  @ignore
  Scenario: confirm import of orientation with conflicted details
    Given the system has a master's orientation entitled "Structuring Adaptive Aplications using AspectJ" with year "2004", among several orientations
    And the file "cv-duplicatedOrientationC.xml", which contains a master's orientation entitled "Structuring Adaptive Aplications using AspectJ" with year "2003", is uploaded
    When I confirm the import of the master's orientation entitled "Structuring Adaptive Aplications using AspectJ" with status "conflicted"
    Then the year of the previously stored master's orientation entitled "Structuring Adaptive Aplications using AspectJ" is updated to "2003" by the system
    And the master's orientation entitled "Structuring Adaptive Aplications using AspectJ" with status "conflicted" is removed from the list of imported orientations

  @ignore
  Scenario: cancel import of orientation with conflicted details
    Given the system has a master's orientation entitled "Structuring Adaptive Aplications using AspectJ" with year "2004", among several orientations
    And the file "cv-duplicatedOrientationC.xml", which contains a master's orientation entitled "Structuring Adaptive Aplications using AspectJ" with year "2003", is uploaded
    When I cancel the import of the master's orientation entitled "Structuring Adaptive Aplications using AspectJ" with status "conflicted"
    And the master's orientation entitled "Structuring Adaptive Aplications using AspectJ" with status "conflicted" is removed from the list of imported orientations
    And the previously stored orientations do not change
  #end
# o que acontece quando o arquivo tem publicações já cadastradas? e
# publicações com mesmos títulos mas outras partes diferentes? e
# se o arquivo nao estiver no formato correto?
