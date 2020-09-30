# DXC Relatorios - Instructions

1. Instalar o NodeJS
	Link: https://nodejs.org/en/
	Baixar a versão mais recente e instalar.
	OBS: nas novas versões, é necessário checar a opção de instalar o Chocolatey durante a instalação do NodeJS
	
2. Instalar AngularCLI globalmente
	Após instalar o Node, no CMD rodar o comando: "npm install -g @angular/cli"
	
3. Setar o Proxy no NPM. No CMD, rodar os seguintes comandos:
	"npm config set proxy http://proxy.houston.hpecorp.net:8080 -g"
	"npm config set https-proxy http://proxy.houston.hpecorp.net:8080 -g"

4. Baixar projeto do git
	Estrutura do projeto:
	angular-code 	==> front-end
	webapp 			==> front-end(angular) compilado
	src				==> backend Java
	
5. Na pasta "angular-code", rodar o comando "npm install". Isso vai baixar as dependencias do frontend
	Pode demorar vários minutos.


*** Subir FrontEnd ***
	1. Na pasta "angular-code", rodar o comando "ng serve --proxy-config proxy.config.js". Pode demorar um pouco(Proxy por causa do CORS)
	2. No browse, acessar o frontend em "localhost:4200"
	
*** Subir BackEnd ***
	1. Add o projeto DXC_Relatorios no JBOSS;
	2. Startar o JBOSS;
	3. Testar:
		No postman, criar uma nova request POST
		URL: http://localhost:8080/CMS-DXC-RELATORIOS/servico/logoff

*** Gerar o WAR ***
	1. Na pasta "angular-code", rodar o comando "ng build" ou "ng build --prod"
		ng build --prod ==> build é mais demorado, porem a aplicação fica mais rapida depois
	
	2. mvn clean package


*** EM TEMPO DE DESENVOLVIMENTO ***
Frontend: Node na porta 4200
http://localhost:4200

Backend: Spring JBOSS porta 8080
http://localhost:8080/CMS-ISSUER-DXC-RELATORIOS