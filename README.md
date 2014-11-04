### Setup do projeto em ambientes Unix com o GVM

O GVM (http://gvmtool.net/) -- Grails Version Manager -- é uma ferramenta para gerenciamento de versões de Groovy e de Grails (parecida com o mais famoso [RVM](http://rvm.io)). Com ele, pra rodar o RGMS a partir do 0, você só precisa:

* Estar em um ambiente (S.O.) Linux;
* Abrir um terminal de linha de comando;
* Instalar o GVM `curl -s get.gvmtool.net | bash`
* Fechar o terminal e abrir novamente (pra que a instalação do GVM faça efeito);
* Instalar o groovy (o comando abaixo irá instalar a versão estável mais recente)
`gvm install groovy`
* Instalar a versão do Grails que o projeto especifica
`gvm install grails 2.1.0`
* Fazer o clone do repositório
`git clone git@github.com:spgroup/rgms.git`
* Mudar para o diretório do projeto e executar a aplicação:
`cd rgms && grails run-app`

Obs.:

* A aplicação estará disponível através do endereço http://localhost:8080/rgms
* Escolha sim (Y) depois da instalação do Groovy e do Grails para que eles sejam setados como versões padrão em uso no sistema
Qualquer dúvida, manda aí.
