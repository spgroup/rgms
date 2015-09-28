# RGMS

Grails 2.1.0

JDK 1.7 SDK 7 (1.8 Não funciona)

Faça o download do chromedriver compatível com sua máquina e coloque ele na pasta chromedrivers.

Mark as Test Source todas as subpastas imediatas de test (não as subpastas das subpastas)

Run configurations:

Grails:RGMS 

run-app

Cucumber:GA

(IntelliJ) Para rodar os testes, crie uma configuração do grails com a seguinte linha de comando:

-Dgeb.env=chrome -Dwebdriver.chrome.driver=/PATHPARAOSEUCHROMEDRIVER test-app functional:cucumber --stacktrace

Exemplo:

-Dgeb.env=chrome -Dwebdriver.chrome.driver=/Users/Hugo/IdeaProjects/rgms/chromedrivers/chromedrivermac test-app functional:cucumber Member.feature --stacktrace

Produção: http://stormy-brushlands-1894.herokuapp.com/

