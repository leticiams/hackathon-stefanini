# hackaton-stefanini-estatico
############## Stefanini Solutions - HACKATON STEFANINI ESTATICO ############## 

1. Baixar o projeto no repositório: https://github.com/ticoleite80/hackaton-stefanini-estatico.git 
2. Entrar na pasta raiz do projeto
3. realizar o NPM INSTALL
4. executar o comando GRUNT
5. Entrar no endereço http://localhost:8300

CONFIG CORS BACKEND
1. entrar no arquivo wildfly-18.0.1.Final/standalone/configuration/standalone.xml
2. Procurar por <host name="default-host" alias="localhost"> no arquivo
3. Alterar a tag encontrada pelo conteudo abaixo
<host name="default-host" alias="localhost">
	<location name="/" handler="welcome-content"/>

	<filter-ref name="server-header"/>
	<filter-ref name="x-powered-by-header"/>
	<filter-ref name="Access-Control-Allow-Origin"/>
	<filter-ref name="Access-Control-Allow-Methods"/>
	<filter-ref name="Access-Control-Allow-Headers"/>
	<filter-ref name="Access-Control-Allow-Credentials"/>
	<filter-ref name="Access-Control-Max-Age"/>

	<http-invoker security-realm="ApplicationRealm"/>
</host>
	Habilitar o método delete
	Alterar a classe EnderecoServico.java na linha 47 para retornar uma lista, está retornando uma lista vazia 
	public Optional<List<Endereco>> getList() {
		return dao.getList();
	}
4. Incluir logo abaixo a tag </handlers> o conteúdo abaixo
<filters>
	<response-header name="server-header" header-name="Server" header-value="WildFly/18"/>
	<response-header name="x-powered-by-header" header-name="X-Powered-By" header-value="Undertow/1"/>
	<response-header name="Access-Control-Allow-Origin" header-name="Access-Control-Allow-Origin" header-value="*"/>
	<response-header name="Access-Control-Allow-Methods" header-name="Access-Control-Allow-Methods" header-value="GET, POST, OPTIONS, PUT, DELETE"/>
	<response-header name="Access-Control-Allow-Headers" header-name="Access-Control-Allow-Headers" header-value="accept, authorization, content-type, x-requested-with"/>
	<response-header name="Access-Control-Allow-Credentials" header-name="Access-Control-Allow-Credentials" header-value="true"/>
	<response-header name="Access-Control-Max-Age" header-name="Access-Control-Max-Age" header-value="1"/>
</filters>


-------------------------------------------- Vídeo Aulas para Implementar as Classes de teste -----------------------------------------------------------------
Parte 1
https://www.youtube.com/watch?v=usCm0XZ0rAs

Parte 2
https://www.youtube.com/watch?v=ZpbzXFuLlUc

Parte 3
https://www.youtube.com/watch?v=hUpyhV1mYnI