<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<%@ page import="rgms.Conferencia" %>
<%@ page import="rgms.Periodico" %>
<%@ page import="rgms.Ferramenta" %>
=======
>>>>>>> 4dc8467add37a91b09033139da8c2e6bbaa43086
=======
>>>>>>> 4dc8467add37a91b09033139da8c2e6bbaa43086
=======
>>>>>>> 4dc8467add37a91b09033139da8c2e6bbaa43086
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Research Group Management System</title>
	</head>
	<body>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
				<li><a href='/rgms/periodico/list'> Periodico </a></li>
				<li><a href='/rgms/conferencia/list'> Conferencia </a></li>
				<li><a href='/rgms/ferramenta/list'> Ferramenta </a></li>
			</ul>
		</div>	
		<div class="content">
			<h1>Listagem, busca, edição e remoção de publicações</h1>
		</div>
		
		<g:form>
			<div>
				<h1 margin="0.8em 1em 0.3em">Search: </h1>
				<g:textField name="busca" type="text" size='55' id="search"/>
			</div>
		</g:form>
		</br>
		<table>
				<thead>
					<tr>
						<g:sortableColumn property="title" title="Title" />
					
						<g:sortableColumn property="author" title="Author" />
					
						<g:sortableColumn property="year" title="Year" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${item}" status="i" var="oneitem">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						
						
						<% Conferencia conf = new Conferencia() %>
						<% Periodico per = new Periodico() %>
						<% Ferramenta fer = new Ferramenta() %>
					
						<g:if test = "${oneitem.class == conf.class}">
							<td><g:link action="show" controller="conferencia" id="${oneitem.id}">${oneitem.title}</g:link></td>
						</g:if>
						<g:if test = "${oneitem.class == per.class}">
							<td><g:link action="show" controller="periodico" id="${oneitem.id}">${oneitem.title}</g:link></td>
						</g:if>
						<g:if test = "${oneitem.class == fer.class}">
							<td><g:link action="show" controller="ferramenta" id="${oneitem.id}">${oneitem.title}</g:link></td>
						</g:if>
					
						<td>${oneitem.author}</td>
					
						<td>${oneitem.year}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
=======
=======
>>>>>>> 4dc8467add37a91b09033139da8c2e6bbaa43086
=======
>>>>>>> 4dc8467add37a91b09033139da8c2e6bbaa43086
			</ul>
		</div>	
		<div class="content">
			<h1>RGMS</h1>
			<br>
			<p>Que tipo de publicação deseja cadastrar?</p> <br>
			<a href='/rgms/periodico/list'> Periodico </a> <br> 
			<a href='/rgms/conferencia/list'> Conferencia </a> <br> 
			<a href='/rgms/ferramenta/list'> Ferramenta </a>
		</div>
		<div class="content">
			<h1>Listagem, busca, edição e remoção de publicações</h1>
		</div>
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> 4dc8467add37a91b09033139da8c2e6bbaa43086
=======
>>>>>>> 4dc8467add37a91b09033139da8c2e6bbaa43086
=======
>>>>>>> 4dc8467add37a91b09033139da8c2e6bbaa43086
	</body>
</html>
