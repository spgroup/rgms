Feature: Authentication Process
  As a registered member in the system
  I want to have access to all of its internal features, which are only accessible after a successful login procedure

Scenario: Login with user that doesn't exist fail message
  Given I am at the Login Page
  When I try to login with an user that does not exist
  Then I am redirected to the Login Page
  And A login failure message is displayed

Scenario: Login with user's wrong password fail message
  Given I am at the Login Page
  When I try to login with an existent user, though with wrong password
  Then I am redirected to the Login Page
  And A login failure message is displayed

Scenario:New user register success message
  Given I am at the User Register Page
  When I register a user with success
  Then I am redirected to the Login Page
  And A message indicating the user was successfully registered is displayed

Scenario: New user register with invalid email fail message
  Given I am at the Login Page
  When I try to create a "newuser" username with the "invalid email abcde" email
  Then A message indicating the email is invalid is displayed

Scenario: Try to access Member List Page without being logged in
  Given I am not logged
  When I directly access the Member List Page
  Then I am redirected to the Login Page

Scenario: Try to access root page without being logged in
  Given I am not logged
  When I access the Root Page
  Then I am redirected to the Login Page

#if ($contextualInformation)
Scenario: Field University filled with "Federal University of Pernambuco"
  Given I am at the Login Page
  When I click the "Create an account" link
  Then I am redirected to the User Register Page
  And The University field is filled with "Federal University of Pernambuco"
#end

Scenario: Unpermitted go to "Principal" Menu from Member Listagem
  Given I am at the Member Listagem page
  When I select the "Principal" menu option
  Then I am redirected to the Publications Menu page

 Scenario: Invalid password validation registration data remaining
   Given I am at Register Page registering myself
   And I mistype my confirmation password at Register Page
   When I submit the form
   Then I am redirected to the User Register Page
   And The password fields are empty
   And My remaining user data is still at their corresponding fields

Scenario:  User unabilited receive not permitted message
  Given I am at the Login Page
  And The user of "naoHabilitado" username is not yet enabled
  When I try loggin with "naoHabilitado"
  Then Inform the user that don`t have permission to loggin yet


 #O campo 'Remember me' da tela de login não surte efeito, ao reinicializar o browser.
 #Essa issue FOI resolvida!! No entanto, não é possível gerar um scenario convincente, pois atualmente o grails
 #limpa todos os cookies toda vez em que o browser é inicializado no escopo de testes. Tentamos utilizar a propriedade
 #de configuração autoClearCookies de várias formas, como chegamos a comentar na thread dessa atividade no email, porém
 #nenhuma surtiu o efeito esperado, e os cookies continuaram sendo apagados.
 #NOS FOI PEDIDO, no entanto, que este scenario fosse mantido, com o devido comentário explicando a situação.
@ignore
Scenario:
  Given I am not logged
  And I access the Root Page
  And I am redirected to the Login Page
  When I fill my login data
  And Press the Remember me checkbox
  And The login procedure is successful
  And I am redirected to the Publications Menu page
  And I close and reopen the browser
  And I access the Root Page
  Then I am redirected to the Publications Menu page

 #Essa issue só existiria se o UrlMappings fosse configurado para visualizar views. Esse scenario não foi implementado.
 #Esse scenario busca representar que, atualmente, o plugin shiro não autentica/protege acessos diretos a views, apenas
 #à páginas gerenciadas por uma class de controle. Ou seja, ao acessar diretamente - sem ter passado com sucesso
 #por um procedimento de login - uma página gerenciada por um controle, o shiro redireciona o navegador para a página
 #de login. No entanto, o mesmo não acontece se o acesso direto é feito a uma view (.gsp), resultando em acesso
 #irrestrito a qualquer view da aplicação, contanto que essa esteja acessível por algum caminho na UrlMappings.
 #NOS FOI PEDIDO, no entanto, que este scenario fosse mantido, com o devido comentário explicando a situação.
@ignore
Scenario:
  Given I am not logged
  When I directly access the Publications Menu Page
  Then I am redirected to the Login Page
